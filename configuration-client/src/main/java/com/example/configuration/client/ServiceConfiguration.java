package com.example.configuration.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.hc.client5.http.ClientProtocolException;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class ServiceConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceConfiguration.class);

    private final ServiceConfigurationClient serviceConfigurationClient;
    private final String tenant;

    public ServiceConfiguration(final String tenant, final ServiceConfigurationClient serviceConfigurationClient) {
        this.serviceConfigurationClient = serviceConfigurationClient;
        this.tenant = tenant;
    }

    private Map<String, String> load() {
        return serviceConfigurationClient.getCacheConfiguration().get(tenant, this::requestConfiguration);
    }

    private Map<String, String> requestConfiguration(String tenant) {

        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(100);
        connectionManager.setDefaultMaxPerRoute(20);

        try (final CloseableHttpClient httpclient = HttpClients.custom().setConnectionManager(connectionManager).build()) {
            String url = String.format("%s/configurations/%s/%s", serviceConfigurationClient.getHost(), serviceConfigurationClient.getDomain(), serviceConfigurationClient.getService());
            final HttpPost httPost = new HttpPost(url);

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            String json = objectMapper.writeValueAsString(new ConfigurationProperty(serviceConfigurationClient.getProperties()));
            final StringEntity entity = new StringEntity(json);

            httPost.setHeader("Accept", "application/json");
            httPost.setHeader("Content-type", "application/json");
            httPost.setHeader("x-tenant", tenant);
            httPost.setEntity(entity);

            LOGGER.info("Executing request configuration {} {}", httPost.getMethod(), httPost.getUri().toString());

            final HttpClientResponseHandler<List<Property>> responseHandler = new HttpClientResponseHandler<>() {
                @Override
                public List<Property> handleResponse(final ClassicHttpResponse response) throws IOException {
                    final int status = response.getCode();
                    if (status >= HttpStatus.SC_SUCCESS && status < HttpStatus.SC_REDIRECTION) {
                        final HttpEntity entity = response.getEntity();
                        try {
                            return entity != null ? objectMapper.readValue(EntityUtils.toString(entity), new TypeReference<>() {
                            }) : new ArrayList<>();
                        } catch (final ParseException ex) {
                            throw new ClientProtocolException(ex);
                        }
                    } else {
                        throw new ClientProtocolException("Unexpected response status: " + status);
                    }
                }
            };

            final List<Property> responseBody = httpclient.execute(httPost, responseHandler);
            return responseBody.stream().collect(Collectors.toMap(Property::getKey, Property::getData));

        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public int asInt(String key) {
        return Integer.parseInt(load().get(key));
    }

    public int asInt(String key, int defaultValue) {
        String value = load().get(key);
        if (Objects.nonNull(value)) {
            return Integer.parseInt(value);
        }
        return defaultValue;
    }

    public boolean asBoolean(String key) {
        String value = load().get(key);
        return BooleanUtils.toBoolean(value);
    }

    public boolean asBoolean(String key, boolean defaultValue) {
        return BooleanUtils.toBooleanDefaultIfNull(BooleanUtils.toBoolean(load().get(key)), defaultValue);
    }

    public String asString(String key) {
        return load().get(key);
    }

    public String asString(String key, String defaultValue) {
        return StringUtils.defaultString(load().get(key), defaultValue);
    }
}
