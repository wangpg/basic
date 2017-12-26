package basic.anno.aop;

import java.math.BigDecimal;
import java.util.concurrent.locks.Lock;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import basic.anno.CacheResult;

import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

//<aop:aspect-autoproxy proxy-target-class="true"/>
/**
 * 自定义AOP的开发
 * @author wang123
 *
 */
@Aspect
@Component
public class CacheAspect {
  
  
  BloomFilter<String> bf = BloomFilter.create(Funnels.stringFunnel(Charsets.UTF_8), 10000,0.03);
  CacheManager cs;
  Lock lock;
  
  /**
   * 自定义注解的AOP开发*********************
   * @param pjp
   * @throws Throwable
   */
  //@Around("@annotation(src.main.java.basic.anno.CacheResult)")
  @Around("@annotation(cr)")
  public Object doAround(ProceedingJoinPoint pjp,CacheResult cr) throws Throwable{
//    System.out.println("方法执行前");
//    pjp.proceed();
//    System.out.println("方法执行后");
    
    
    String key = cr.key();
    String cacheName = cr.cacheName();
    String backName = cr.backName();
    boolean needBloomFilter = cr.needBloomFilter();
    boolean needLock = cr.needLock();
    
    if(needBloomFilter && !bf.mightContain(key)){
      return null;
    }
    
    BigDecimal bd = null;//cs.cacheResult(key,cacheName);
    if(bd!=null){
      return bd;
    }
    
    if(needLock){
      if(lock.tryLock()){
        Object obj = pjp.proceed();//  get from db
        //cs.put(key,obj,cacheName);
        //cs.put(backupname+key,obj,backName);
        return obj;
      }
      
      
      lock.unlock();
    }
    
    return null;
  }
  
  
}
