package basic.bloomfilter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

/**
 * 如大规模查询用户的总消费金额的时候，由于并发量大，且数据库需要进行聚合运算，
 *并且有缓存击穿攻击行为i额，需要布隆过滤器和分布式锁lock.locl(),其实就是等待重试的行为
 * 
 * 
 * 使用布隆过滤器可以解决缓存击穿的问题，即把验证通过的放在布隆数组中，
 * 每次请求的时间查看是否是合法的用户（即是否在布隆数组中）
 * @author wang123
 *
 */
public class BloomFilterTest {
  public static void main(String[] args) {
    int sum=1000000;
    int testSum = 1000000;
    int right = 0;
    int wrong = 0;
    int a = 0;
    
    BloomFilter<String> bf = BloomFilter.create(Funnels.stringFunnel(Charsets.UTF_8), sum,0.001);
    List<String> list = new ArrayList<String>();
    Set<String> set = new HashSet<>();
    
    for (int i = 0; i < sum; i++) {
      String uuid = UUID.randomUUID().toString();
      bf.put(uuid);
      list.add(uuid);
      set.add(uuid);
    }
    
    
    for (int i = 0; i < testSum; i++) {
//      if(bf.mightContain(list.get(i))){
//        if(set.contains(list.get(i))){
//          right++;
//        }else{
//          wrong++;
//        }
//      }
      try {
        Thread.sleep(10);
      } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      String test = i%100==0?list.get(i):UUID.randomUUID().toString();
      if(bf.mightContain(test)){
        if(set.contains(test)){
          right++;
        }else{
          wrong++;//测试一万次，随机生成的字符串在里面的概率为上面指出的概率
        }
      }else{
        a++;
      }
    }
    
    
    System.out.println("---------------------right------------------------"+right);
    System.out.println("---------------------wrong------------------------"+wrong);
    System.out.println("---------------------a------------------------"+a);
  }
}
