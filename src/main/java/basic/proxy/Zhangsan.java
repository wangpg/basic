package basic.proxy;
public class Zhangsan implements People{
  @Override
  public void eat() throws Throwable {
    System.out.println("张三----eat");
  }
}