package com.example.portal;

import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusMessage;
import com.azure.messaging.servicebus.ServiceBusReceiverClient;
import com.azure.messaging.servicebus.ServiceBusSenderClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.support.MessageBuilder;

@Configuration
//@EnableConfigurationProperties(ServiceBusProperties.class)
public class ServiceBusConfig {


//    @Bean
//    public ServiceBusSenderClient tenantQueueSenderClient() {
//
//
//        return new ServiceBusClientBuilder()
//                .connectionString(properties.getConnectionString())
//                .sender()
//                .queueName(properties.getTenantQueueName())
//                .buildClient();
//    }
//
//    @Bean
//    public ServiceBusSenderClient tenantTopicSenderClient() {
//        return new ServiceBusClientBuilder()
//                .connectionString(properties.getConnectionString())
//                .sender()
//                .topicName(properties.getTenantTopicName())
//                .buildClient().sendMessage(new ServiceBusMessage().set);
//    }
//
//    @Bean
//    public ServiceBusReceiverClient tenantQueueReceiverClient() {
//        return new ServiceBusClientBuilder()
//                .connectionString(properties.getConnectionString())
//                .receiver()
//                .queueName(properties.getTenantQueueName())
//                .buildClient();
//    }
//
//    @Bean
//    public ServiceBusReceiverClient tenantTopicReceiverClient() {
//        return new ServiceBusClientBuilder()
//                .connectionString(properties.getConnectionString())
//                .receiver()
//                .topicName(properties.getTenantTopicName())
//                .subscriptionName(properties.getTenantSubscriptionName())
//                .buildClient();
//    }
}
