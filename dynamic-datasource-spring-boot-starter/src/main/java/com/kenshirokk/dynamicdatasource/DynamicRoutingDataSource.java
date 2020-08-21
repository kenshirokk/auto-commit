package com.kenshirokk.dynamicdatasource;

import lombok.Data;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@Data
public class DynamicRoutingDataSource extends AbstractRoutingDataSource {

    private Object writeDataSource;
    private List<Object> readDataSources;
    private int readDataSourceSize;

    @Override
    protected Object determineCurrentLookupKey() {
        DynamicDataSourceEnum key = DynamicDataSourceHolder.getDataSourceLookupKey();
        if (key == null || DynamicDataSourceEnum.WRITE.equals(key) || readDataSourceSize == 0) {
            return DynamicDataSourceEnum.WRITE.name();
        }
        int index = ThreadLocalRandom.current().nextInt(0, readDataSourceSize);

        return DynamicDataSourceEnum.READ.name() + index;
    }

    @Override
    public void afterPropertiesSet() {
        setDefaultTargetDataSource(writeDataSource);

        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DynamicDataSourceEnum.WRITE.name(), writeDataSource);
        if (readDataSources != null && readDataSources.size() > 0) {
            for (int i = 0; i < readDataSources.size(); i++) {
                targetDataSources.put(DynamicDataSourceEnum.READ.name() + i, readDataSources.get(i));
            }
            readDataSourceSize = readDataSources.size();
        } else {
            readDataSourceSize = 0;
        }
        setTargetDataSources(targetDataSources);
        super.afterPropertiesSet();
    }
}
