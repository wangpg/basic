package basic.loader;

public class TestMyClassLoader {
  public static void main(String[] args) throws Exception {
    MyClassLoaderByFind loader1 = new MyClassLoaderByFind("loader1", "d:/temp/a");
    loader1.loadClass("Demo");
  }
}
