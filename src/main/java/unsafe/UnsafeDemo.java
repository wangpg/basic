package unsafe;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.junit.Test;


public class UnsafeDemo {

  //初始对象
  @Test
  public void testAllocateInstance() throws Exception{
    Object unsafe = UnsafeUtil.getUnsafe();
    System.out.println(unsafe.getClass());
    Method m = unsafe.getClass().getDeclaredMethod("allocateInstance", Class.class);//初始化对象并不走任何的构造方法 
    User user1 = (User) m.invoke(unsafe, User.class);
    System.out.println(user1);
  }
  
  
  @Test
  public void testPutInt() throws Exception{
    Object unsafe = UnsafeUtil.getUnsafe();
    Field nameF = User.class.getDeclaredField("age");
    
    Method objectFieldOffsetM = unsafe.getClass().getDeclaredMethod("objectFieldOffset", Field.class);
    Long objectFieldOffset = (Long) objectFieldOffsetM.invoke(unsafe, nameF);
    
    Method putIntM = unsafe.getClass().getDeclaredMethod("putLong", Object.class,long.class,long.class);
    putIntM.invoke(unsafe, objectFieldOffset,30L);
    System.out.println();
  }
}
