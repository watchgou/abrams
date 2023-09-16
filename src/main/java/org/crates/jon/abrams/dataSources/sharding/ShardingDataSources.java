package org.crates.jon.abrams.dataSources.sharding;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jon
 * @date 2023/9/16
 */

//@Configuration
public class ShardingDataSources {

    private static final Logger log = LoggerFactory.getLogger(ShardingDataSources.class);

    final ShardingProperties shardingProperties;


    public ShardingDataSources(ShardingProperties shardingProperties) {
        this.shardingProperties = shardingProperties;
    }
}
