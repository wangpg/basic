package unsafe;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class UnsafeUtil {
  public static Object getUnsafe() {
    Object o = null;
    try {
      Class<?> clazz = Class.forName("java.lang.String");
      Method[] methods = clazz.getMethods();
      for (Method m : methods) {
        //System.out.println(Modifier.toString(m.getModifiers())+"    "+m.getName());
      }
      clazz = Class.forName("sun.misc.Unsafe");
      methods = clazz.getMethods();
      for (Method m : methods) {
        //System.out.println(Modifier.toString(m.getModifiers())+"    "+m.getName());
        if(m.getName().equals("getUnsafe")){
          o = m.invoke(null);
          break;
        }
      }
      
      
      if(o!=null){
        return o;
      }
      
      Field field = clazz.getDeclaredField("theUnsafe");
      field.setAccessible(true);
      o = field.get(null);
      if(o!=null){
        return o;
      }
      
      return null;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
}
