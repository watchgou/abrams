package org.crates.jon.abrams.dataSources.sharding;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author jon
 * @date 2023/9/16
 */

@Configuration
@ConfigurationProperties(prefix = "sharding.sphere")
public class ShardingProperties {


    private String databaseName;

    private List<ShardingDataSources> shardingDataSources;

    public List<ShardingDataSources> getShardingDataSources() {
        return shardingDataSources;
    }

    public void setShardingDataSources(List<ShardingDataSources> shardingDataSources) {
        this.shardingDataSources = shardingDataSources;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public static class ShardingDataSources {
        private String name;

        private String driverClassName;

        private String url;

        private String username;

        private String password;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDriverClassName() {
            return driverClassName;
        }

        public void setDriverClassName(String driverClassName) {
            this.driverClassName = driverClassName;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

    }

}
