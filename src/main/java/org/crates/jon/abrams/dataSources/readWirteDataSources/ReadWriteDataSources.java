package org.crates.jon.abrams.dataSources.readWirteDataSources;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shardingsphere.driver.api.ShardingSphereDataSourceFactory;
import org.apache.shardingsphere.infra.config.algorithm.AlgorithmConfiguration;
import org.apache.shardingsphere.infra.config.mode.ModeConfiguration;
import org.apache.shardingsphere.infra.config.rule.RuleConfiguration;
import org.apache.shardingsphere.mode.repository.standalone.StandalonePersistRepositoryConfiguration;
import org.apache.shardingsphere.readwritesplitting.api.ReadwriteSplittingRuleConfiguration;
import org.apache.shardingsphere.readwritesplitting.api.rule.ReadwriteSplittingDataSourceRuleConfiguration;
import org.apache.shardingsphere.readwritesplitting.api.transaction.TransactionalReadQueryStrategy;
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


    final ReadWriteProperties readWriteProperties;


    public ReadWriteDataSources(ReadWriteProperties readWriteProperties) {
        this.readWriteProperties = readWriteProperties;
    }


    @Bean
    public DataSource dataSource() throws SQLException {


        return ShardingSphereDataSourceFactory.createDataSource(readWriteProperties.getDatabaseName(), StandaloneMode(), dataSourceMap(), rule(), new Properties());
    }


    private ModeConfiguration StandaloneMode() {
        StandalonePersistRepositoryConfiguration standalonePersistRepositoryConfiguration = new StandalonePersistRepositoryConfiguration("JDBC", new Properties());
        return new ModeConfiguration("Standalone", standalonePersistRepositoryConfiguration);
    }


    private Collection<RuleConfiguration> rule() {
        Collection<RuleConfiguration> rule = new LinkedList<>();
        ReadwriteSplittingRuleConfiguration shardingRuleConfiguration = new ReadwriteSplittingRuleConfiguration();
        Collection<ReadwriteSplittingDataSourceRuleConfiguration> dataSources = new LinkedList<>();
        ReadwriteSplittingDataSourceRuleConfiguration readwriteSplittingDataSourceRuleConfiguration = new ReadwriteSplittingDataSourceRuleConfiguration(readWriteProperties.getDatabaseName(), readWriteProperties.getReadWriteProp().getWriteDataSourceName(), readWriteProperties.getReadWriteProp().getReadDataSourceNames(), TransactionalReadQueryStrategy.PRIMARY, "random");
        shardingRuleConfiguration.setDataSources(dataSources);
        Map<String, AlgorithmConfiguration> loadBalancers = new HashMap<>();
        AlgorithmConfiguration algorithmConfiguration = new AlgorithmConfiguration("RANDOM", new Properties());
        loadBalancers.put("random", algorithmConfiguration);
        shardingRuleConfiguration.setLoadBalancers(loadBalancers);
        rule.add(shardingRuleConfiguration);
        return rule;
    }


    private Map<String, DataSource> dataSourceMap() {
        Map<String, DataSource> dataSourceMap = new HashMap<>();
        readWriteProperties.getShardingDataSources().forEach(ob -> {
            DruidDataSource druidDataSource = new DruidDataSource();
            druidDataSource.setUrl(ob.getUrl());
            druidDataSource.setUsername(ob.getUsername());
            druidDataSource.setPassword(ob.getPassword());
            druidDataSource.setDriverClassName(ob.getDriverClassName());
            dataSourceMap.put(ob.getName(), druidDataSource);
        });
        return dataSourceMap;
    }
}
