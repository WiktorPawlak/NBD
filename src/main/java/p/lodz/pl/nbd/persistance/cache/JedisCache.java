package p.lodz.pl.nbd.persistance.cache;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import redis.clients.jedis.DefaultJedisClientConfig;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisClientConfig;
import redis.clients.jedis.JedisPooled;


public class JedisCache {

    private static final String redisConfigPath = Thread.currentThread().getContextClassLoader().getResource("").getPath() + "redis.properties";
    public final JedisPooled jedis;
    public final Jsonb jsonb = JsonbBuilder.create();

    public JedisCache() {
        Properties appProps = new Properties();
        try {
            appProps.load(new FileInputStream(redisConfigPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String redisHost = appProps.getProperty("host");
        int redisPort = Integer.parseInt(appProps.getProperty("port"));

        JedisClientConfig clientConfig = DefaultJedisClientConfig.builder().build();
        jedis = new JedisPooled(new HostAndPort(redisHost, redisPort), clientConfig);
    }
}
