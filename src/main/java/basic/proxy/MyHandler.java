package basic.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MyHandler implements InvocationHandler{
  People people;
  MyHandler(People people){
    this.people=people;
  }
  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    before();
    method.invoke(people, args);
    after();
    return null;
  }
  private void before() {
    System.out.println("我在吃猪脚之前要洗手");
  }
  private void after() {
    System.out.println("我在吃猪脚之后要洗手");
    
  }
  
}