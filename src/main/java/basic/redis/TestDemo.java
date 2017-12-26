package basic.redis;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

public class TestDemo {
  
  
  @Test
  public void sentinelTest(){
    Set<String> sentinel = new HashSet<String>();
    sentinel.add("127.0.0.1:26379");
    JedisSentinelPool jedisSentinelPool = null;//new JedisSentinelPool("", 1);
    Jedis jedis = jedisSentinelPool.getResource();
    jedis.set("", "");
  }
}
