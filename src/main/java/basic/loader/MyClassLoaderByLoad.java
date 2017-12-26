package basic.loader;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class MyClassLoaderByLoad extends ClassLoader{
  String name;//加载器名字
  String path;//加载路径
  
  public MyClassLoaderByLoad(String name,String path){
    this.name=name;
    this.path=path;
  }
  public MyClassLoaderByLoad(ClassLoader parent, String name,String path){
    super(parent);
    this.name=name;
    this.path=path;
  }
  
  @Override
  public Class<?> loadClass(String name) throws ClassNotFoundException {
    Class<?> c = null;
    if(this.name.equals(name)){
      c = this.findLoadedClass(name);
      if(c==null){
        c = this.findClass(name);
      }
    }
    return super.loadClass(name);
  }
  
  
  @Override
  protected Class<?> findClass(String name) throws ClassNotFoundException {
    byte[] bs = readFileToByteArray(name);
    
    //指定自截留返回类的对象
    return this.defineClass(name, bs, 0,bs.length);
  }
  
  
  private byte[] readFileToByteArray(String name) {
    InputStream is = null;
    byte[] returnData = null;
    name = name.replaceAll("\\.", "/");
    String filePath = this.path+name+".class";
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    byte[] buffer = new byte[1024];
    int len ;
    
    try {
      is = new FileInputStream(filePath);
      while((len=is.read(buffer))!=-1){
        baos.write(buffer, 0, len);
      }
      is.close();
      baos.close();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return baos.toByteArray();
  }
  
  
  public static void main(String[] args) {
    System.out.println(Thread.currentThread().getContextClassLoader());
    //System.out.println(new MyClassLoaderByLoad(Thread.currentThread().getContextClassLoader(), ""));
  }
}
