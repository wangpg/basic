package basic.proxy;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class MyClassLoader extends ClassLoader {
  private File dir;
  
  public MyClassLoader(String path) {
    this.dir = new File(path);
  }
  @Override
  protected Class<?> findClass(String name) throws ClassNotFoundException {
    if(dir!=null){
      File classFile = new File(dir,name+".class");
      if(classFile.exists()){
        try {
          InputStream is = new FileInputStream(classFile);
          ByteArrayOutputStream baos = new ByteArrayOutputStream();
          
          byte[] buffer = new byte[1024];
          int len;
          while((len=is.read(buffer))!=-1){
            baos.write(buffer, 0, len);
          }
          is.close();
          return defineClass("basic.proxy."+name, baos.toByteArray(), 0,baos.size());
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
    return super.findClass(name);
  }
  
}
