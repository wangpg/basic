package basic.anno;

import java.math.BigDecimal;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl {

  @Cacheable(key="userId",cacheNames={})
  public BigDecimal getAmount(Integer userId){
    return new BigDecimal(100);
  }
  
}
