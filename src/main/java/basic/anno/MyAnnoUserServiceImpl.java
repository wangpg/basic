package basic.anno;

import java.math.BigDecimal;


public class MyAnnoUserServiceImpl {
  @CacheResult(key="#userId",cacheName="amount",backName="amountBack",needLock=true,needBloomFilter=true)
  public BigDecimal getAmount(Integer userId){
    return new BigDecimal(100);
  }
}
