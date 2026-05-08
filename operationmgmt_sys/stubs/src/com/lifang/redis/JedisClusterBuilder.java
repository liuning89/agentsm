package com.lifang.redis;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.JedisCluster;

public class JedisClusterBuilder {
    private String addressConfigs;
    private int timeout;
    private int maxRedirections;
    private GenericObjectPoolConfig genericObjectPoolConfig;

    public void setAddressConfigs(String addressConfigs) { this.addressConfigs = addressConfigs; }
    public void setTimeout(int timeout) { this.timeout = timeout; }
    public void setMaxRedirections(int maxRedirections) { this.maxRedirections = maxRedirections; }
    public void setGenericObjectPoolConfig(GenericObjectPoolConfig genericObjectPoolConfig) { this.genericObjectPoolConfig = genericObjectPoolConfig; }

    public JedisCluster build() { return null; }
    public void shutdown() {}
}
