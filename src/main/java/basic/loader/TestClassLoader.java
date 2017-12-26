package basic.loader;

/**
 * 启动类加载器 rt.jar  /-Xbootclasspath
 * JAVA_HOME/lib/ext/*.jar 扩展类加载器
 * AppClassLoader  启动classpath下的类
 * CustomClassLoader 完全自定义路径
 * @author wang123
 *
 *右键启动  配置  VM Arguments  -Xbootclasspath/a:d:/temp   -XX:+TraceClassLoading
 ************************
 *
 */
public class TestClassLoader {
  public static void main(String[] args) {
    DemoLoader loader = new DemoLoader();
    loader.print();
  }
}
