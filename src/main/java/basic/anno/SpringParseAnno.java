package basic.anno;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.AnnotationUtils;

public class SpringParseAnno implements ApplicationContextAware,ApplicationListener<ContextRefreshedEvent>{
  ApplicationContext applicationContext;
  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {
    Class<CacheResult> c = CacheResult.class;
    Map<String, Object> beans = applicationContext.getBeansWithAnnotation(c);
    Set<Entry<String, Object>> entrySet = beans.entrySet();
    for (Entry<String, Object> entry : entrySet) {
      String beanId = entry.getKey();
      Class<? extends Object> clazz = entry.getValue().getClass();
      System.out.println(clazz.getName());
      
      //使用spring工具类获取注解类实例
      CacheResult cr = AnnotationUtils.findAnnotation(clazz, c);
      System.out.println(cr.key()+","+cr.cacheName());
    }
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext=applicationContext;
  }

}
