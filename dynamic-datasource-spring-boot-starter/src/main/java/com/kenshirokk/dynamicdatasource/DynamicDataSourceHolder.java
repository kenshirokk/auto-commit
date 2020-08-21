package com.kenshirokk.dynamicdatasource;

public class DynamicDataSourceHolder {

    private static ThreadLocal<DynamicDataSourceEnum> HOLDER = new ThreadLocal<>();

    public static void setDataSourceLookupKey(DynamicDataSourceEnum dataSourceLookupKey) {
        HOLDER.set(dataSourceLookupKey);
    }

    public static DynamicDataSourceEnum getDataSourceLookupKey() {
        return HOLDER.get();
    }

    public static void clearDataSourceLookupKey() {
        HOLDER.remove();
    }
}
