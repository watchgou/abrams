package org.crates.jon.abrams.dataSources;

import com.zaxxer.hikari.util.DriverDataSource;
import org.apache.shardingsphere.driver.api.ShardingSphereDataSourceFactory;
import org.apache.shardingsphere.infra.config.mode.ModeConfiguration;
import org.apache.shardingsphere.infra.config.rule.RuleConfiguration;
import org.apache.shardingsphere.readwritesplitting.api.ReadwriteSplittingRuleConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Properties;

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


    @Bean
    public DataSource dataSource() throws SQLException {
        ModeConfiguration modeConfiguration = new ModeConfiguration("", null);
        Collection<RuleConfiguration> configs = new LinkedList<>();
        ReadwriteSplittingRuleConfiguration ruleConfiguration = new ReadwriteSplittingRuleConfiguration();
        configs.add(ruleConfiguration);
        return ShardingSphereDataSourceFactory.createDataSource(dataSourcesProperties.getDatabaseName(),
                dataSourceMap(), configs, new Properties());
    }


    private Map<String, DataSource> dataSourceMap() {
        Map<String, DataSource> dataSourceMap = new HashMap<>();
        dataSourcesProperties.getShardingDataSources().forEach(ob -> {
            DataSource dataSource = new DriverDataSource(ob.getUrl(), ob.getDriverClassName(), new Properties(), ob.getUsername(), ob.getPassword());
            dataSourceMap.put(ob.getName(), dataSource);
        });
        return dataSourceMap;
    }
}
