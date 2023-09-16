package org.crates.jon.abrams.dataSources.shardingDataSources;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author jon
 * @date 2023/9/16
 */

@Configuration
@ConfigurationProperties(prefix = "sharding.sphere")
public class ShardingProperties {


}
