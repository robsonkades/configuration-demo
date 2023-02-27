package com.example.tenant;

public class TenantContextHolder {

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

    public static String getTenant() {
        return contextHolder.get();
    }

    public static String setTenant(String tenant) {
        contextHolder.set(tenant);
        return tenant;
    }
}
