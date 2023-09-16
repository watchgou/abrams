package org.crates.jon.abrams.dataSources.sharding;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shardingsphere.driver.api.ShardingSphereDataSourceFactory;
import org.apache.shardingsphere.infra.config.mode.ModeConfiguration;
import org.apache.shardingsphere.infra.config.rule.RuleConfiguration;
import org.apache.shardingsphere.mode.repository.standalone.StandalonePersistRepositoryConfiguration;
import org.apache.shardingsphere.sharding.api.config.ShardingRuleConfiguration;
import org.apache.shardingsphere.transaction.config.TransactionRuleConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * @date 2023/9/16
 */

@Configuration
public class ShardingDataSources {

    private static final Logger log = LoggerFactory.getLogger(ShardingDataSources.class);

    final ShardingProperties shardingProperties;


    public ShardingDataSources(ShardingProperties shardingProperties) {
        this.shardingProperties = shardingProperties;
    }


    //@Bean
    public DataSource dataSource() throws SQLException {
        return ShardingSphereDataSourceFactory.createDataSource(shardingProperties.getDatabaseName(), StandaloneMode(), dataSourceMap(), rule(), new Properties());
    }


    private Collection<RuleConfiguration> rule() {
        Collection<RuleConfiguration> rule = new LinkedList<>();
        ShardingRuleConfiguration shardingRuleConfiguration = new ShardingRuleConfiguration();
        TransactionRuleConfiguration transactionRuleConfiguration = new TransactionRuleConfiguration("BASE", "Seata", new Properties());
        rule.add(shardingRuleConfiguration);
        rule.add(transactionRuleConfiguration);
        return rule;
    }


    private ModeConfiguration StandaloneMode() {
        StandalonePersistRepositoryConfiguration standalonePersistRepositoryConfiguration = new StandalonePersistRepositoryConfiguration("JDBC", new Properties());
        return new ModeConfiguration("Standalone", standalonePersistRepositoryConfiguration);
    }

    private Map<String, DataSource> dataSourceMap() {
        Map<String, DataSource> dataSourceMap = new HashMap<>();
        shardingProperties.getShardingDataSources().forEach(ob -> {
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
