package basic.proxy;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

public class MyProxy {

  
  public static Object createProxyInstance(ClassLoader loader,Class<?> interf,InvocationHandler handler){
    //1用字符串凭借一个代理的java文件
    String proxyClass = get$Proxy0(interf);
    //2.把java文件写入到文件中
    String fileName = "D:\\workspace\\basic\\src\\main\\java\\basic\\proxy\\$Proxy0.java";
    File f = new File(fileName);
    try {
      FileWriter fw = new FileWriter(f);
      fw.write(proxyClass);
      fw.flush();
      fw.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    //编译类
    compilerJava(fileName);
    
    //写一个属于自己的类加载器
    MyClassLoader loader1 = new MyClassLoader("D:\\workspace\\basic\\src\\main\\java\\basic\\proxy\\");
    
    try {
      Class<?> proxy0Class = loader1.findClass("$Proxy0");
      Constructor<?> constructor = proxy0Class.getConstructor(InvocationHandler.class);
      return constructor.newInstance(handler);
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    return null;
  }
  
  //编译JAVA文件
  public static void compilerJava(String fileName){
    JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
    StandardJavaFileManager standardFileManager = compiler.getStandardFileManager(null, null, null);
    Iterable<? extends JavaFileObject> untis = standardFileManager.getJavaFileObjects(fileName);
    CompilationTask task = compiler.getTask(null, standardFileManager, null, null, null, untis);
    task.call();
    try {
      standardFileManager.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  public static String get$Proxy0(Class<?> interf){
    StringBuilder sb = new StringBuilder();
    sb.append("package basic.proxy;\n");
    sb.append("import java.lang.reflect.*;\n");
    sb.append("public class $Proxy0 implements ").append(interf.getName()).append("{\n");
    sb.append("    InvocationHandler handler;\n");
    sb.append("    public $Proxy0(InvocationHandler handler){\n");
    sb.append("        this.handler=handler;\n");
    sb.append("    }\n");
    
    sb.append(getMethodString(interf.getMethods(), interf));
    
    sb.append("}");
    return sb.toString();
  }
  
  public static String getMethodString(Method[] methods,Class<?> interf){
    StringBuilder sb = new StringBuilder();
    for (Method method : methods) {
      sb.append("    public void "+method.getName()+"(");
      Class<?>[] parameterTypes = method.getParameterTypes();
      if(parameterTypes!=null && parameterTypes.length>0){
        for(int i=0;i<parameterTypes.length;i++){
          sb.append(parameterTypes[i].getName()+"  "+"param"+i);
          if(i!=parameterTypes.length-1){
            sb.append(",");
          }
        }
      }
      sb.append(")");
      Class<?>[] exceptionTypes = method.getExceptionTypes();
      if(exceptionTypes!=null && exceptionTypes.length>0){
        sb.append("throws ");
        for (int i = 0; i < exceptionTypes.length; i++) {
          sb.append(exceptionTypes[i].getName());
          if(i!=exceptionTypes.length-1){
            sb.append(",");
          }
        }
      }
      sb.append("{\n");
      
      sb.append("        Method md = "+interf.getName()+".class.getMethod(\""+method.getName()+"\",new Class[]{});\n");
      sb.append("        this.handler.invoke(this,md,null);");
      
      
      sb.append("}\n");
    }
    
    return sb.toString();
  }
}
