package basic.proxy;


public class Main {
  public static void main(String[] args) throws Throwable {
    People proxyInstance = (People) MyProxy.createProxyInstance(Main.class.getClassLoader(), People.class , new MyHandler(new Zhangsan()));
    proxyInstance.eat();
  }
}
