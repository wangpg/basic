package basic.proxy;
import java.lang.reflect.*;
public class $Proxy0 implements basic.proxy.People{
    InvocationHandler handler;
    public $Proxy0(InvocationHandler handler){
        this.handler=handler;
    }
    public void eat()throws java.lang.Throwable{
        Method md = basic.proxy.People.class.getMethod("eat",new Class[]{});
        this.handler.invoke(this,md,null);}
}