package dynamic;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.objectweb.asm.Type;

import dynamic.test.User;
import net.sf.cglib.core.ClassGenerator;
import net.sf.cglib.core.DefaultGeneratorStrategy;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import net.sf.cglib.transform.ClassTransformer;
import net.sf.cglib.transform.TransformingClassGenerator;
import net.sf.cglib.transform.impl.AddPropertyTransformer;

public class CglibDemo {
  public static void main(String[] args) {
    Enhancer e = new Enhancer();
    e.setSuperclass(User.class);
    e.setStrategy(new DefaultGeneratorStrategy(){
      @Override
      public ClassGenerator transform(ClassGenerator cg) throws Exception {
        ClassTransformer transformer = new AddPropertyTransformer(new String[]{"foo"}, new Type[]{Type.INT_TYPE });
        return new TransformingClassGenerator(cg, transformer);
      }
    });

    e.setCallback(new MethodInterceptor() {
      @Override
      public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        System.out.println("前置代理");  
        //通过代理类调用父类中的方法  
        Object result = proxy.invokeSuper(obj, args);  
        System.out.println("后置代理");  
        return result;
      }
      
    });
    
    User proxy = (User) e.create();
    Field[] fields = proxy.getClass().getFields();
    for (Field field : fields) {
      System.out.println(field.getName());
    }
  }
}
