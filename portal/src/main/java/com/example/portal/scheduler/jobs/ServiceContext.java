package com.example.portal.scheduler.jobs;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

public class ServiceContext {

    static class ServiceContextData {
        Message message;
        String userId;
        String tenant;

        public ServiceContextData copy() {
            ServiceContextData copy = new ServiceContextData();
            copy.message = message;
            copy.userId = userId;
            copy.tenant = tenant;
            return copy;
        }
    }

    private ThreadLocal<ServiceContextData> currentContextData = new ThreadLocal<>();
    private static AtomicReference<ServiceContext> instance = new AtomicReference<>();

    public static void install(ServiceContext context) {
        ServiceContext.instance.set(context);
    }

    public static ServiceContext get() {
        return ServiceContext.instance.get();
    }

    public Message getCurrentMessage() {
        ServiceContextData serviceContextData = currentContextData.get();
        if (serviceContextData == null) {
            return null;
        }
        return serviceContextData.message;
    }

    ServiceContextData getCurrentContextDataCopy() {
        ServiceContextData serviceContextData = currentContextData.get();
        if (serviceContextData == null) {
            return null;
        }
        return serviceContextData.copy();
    }

    private ServiceContextData getOrCreateCurrentContextData() {
        ServiceContextData serviceContextData = currentContextData.get();
        if (serviceContextData == null) {
            serviceContextData = new ServiceContextData();
            currentContextData.set(serviceContextData);
        }
        return serviceContextData;
    }

    public CompletableFuture<Void> runWhenAllComplete(Runnable toRun, CompletableFuture<?>... futures) {
        ServiceContextData contextData = ServiceContext.get().getCurrentContextDataCopy();
        CompletableFuture<Void> future = CompletableFuture.allOf(futures);
        future.whenComplete((o, t) -> {
            if (contextData != null) {
                ServiceContext.get().runWithContext(contextData, toRun);
                return;
            }
            toRun.run();
        });
        return future;
    }

    public void setCurrentMessage(Message received) {
        if (getCurrentMessage() != null) {
            throw new IllegalStateException("Current message already set: " + getCurrentMessage().toString());
        }

        ServiceContextData serviceContextData = getOrCreateCurrentContextData();
        serviceContextData.message = received;
    }

    public void setCurrentTenant(String tenant) {
        if (getCurrentTenant() != null) {
            throw new IllegalStateException("Current tenant already set: " + getCurrentTenant());
        }

        ServiceContextData serviceContextData = getOrCreateCurrentContextData();
        serviceContextData.tenant = tenant;
    }

    public void setCurrentUserId(String userId) {
        if (getCurrentUsername() != null) {
            throw new IllegalStateException("Current userId already set: " + getCurrentUsername());
        }

        ServiceContextData serviceContextData = getOrCreateCurrentContextData();
        serviceContextData.userId = userId;
    }

    public String getCurrentUsername() {
        ServiceContextData serviceContextData = currentContextData.get();
        if (serviceContextData == null) {
            return null;
        }
        return serviceContextData.userId;
    }

    public String getCurrentTenant() {
        ServiceContextData serviceContextData = currentContextData.get();
        if (serviceContextData == null) {
            return null;
        }
        return serviceContextData.tenant;
    }

    public <T> T runWithUserWithReturn(String user, Supplier<T> supplier) {
        String current = getCurrentTenant();
        try {
            overrideCurrentUsername(user);
            return supplier.get();
        } finally {
            overrideCurrentUsername(current);
        }
    }

    public <T> T runWithTenantWithReturn(String tenant, Supplier<T> supplier) {
        String current = getCurrentTenant();
        try {
            overrideCurrentTenant(tenant);
            return supplier.get();
        } finally {
            overrideCurrentTenant(current);
        }
    }

    public void clearCurrentMessage() {
        clearCurrentContextData();
    }

    private void clearCurrentContextData() {
        currentContextData.remove();
    }

    private void overrideCurrentUsername(String userId) {
        getOrCreateCurrentContextData().userId = userId;
    }

    private void overrideCurrentTenant(String tenant) {
        getOrCreateCurrentContextData().tenant = tenant;
    }

    public void runWithUser(String user, Runnable runnable) {
        runWithUserWithReturn(user, () -> {
            runnable.run();
            return null;
        });
    }

    public void runWithTenant(String tenant, Runnable runnable) {
        runWithTenantWithReturn(tenant, () -> {
            runnable.run();
            return null;
        });
    }

    public Runnable wrapWithContext(Runnable toWrap) {
        ServiceContextData contextData = currentContextData.get();
        return () -> runWithContext(contextData, () -> {
            toWrap.run();
            return null;
        });
    }

    public <C> Supplier<C> wrapWithContext(Supplier<C> toWrap) {
        ServiceContextData contextData = currentContextData.get();
        return () -> runWithContext(contextData, toWrap);
    }

    void runWithContext(ServiceContextData contextData, Runnable toRun) {
        runWithContext(contextData, () -> {
            toRun.run();
            return null;
        });
    }

    <C> C runWithContext(ServiceContextData contextData, Supplier<C> toRun) {
        ServiceContextData current = currentContextData.get();
        ServiceContextData contextCopy = contextData == null ? null : contextData.copy();
        currentContextData.set(contextCopy);
        try {
            return toRun.get();
        } finally {
            currentContextData.set(current);
        }
    }
}