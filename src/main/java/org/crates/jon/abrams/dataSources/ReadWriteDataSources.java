package org.crates.jon.abrams.dataSources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

/**
 * @author jon
 * @date 2023/9/12
 */

@Configuration
public class ReadWriteDataSources {

    private static final Logger log = LoggerFactory.getLogger(ReadWriteDataSources.class);


    private final DataSourcesProperties dataSourcesProperties;


    public ReadWriteDataSources(DataSourcesProperties dataSourcesProperties) {
        this.dataSourcesProperties = dataSourcesProperties;
    }

}
