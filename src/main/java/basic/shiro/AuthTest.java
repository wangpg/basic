package basic.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 *[users]
 *zhangsan=111111,husband
 *loawang=222222,py
 *lision=333333,waiter
 *
 * [roles]
 * husband=*
 * py=livingroom:walk:A,bed:ml:A
 * waiter=livingroom:walk:*,*:clean:A
 *
 */
public class AuthTest {

  @Test
  public void authTest(){
    IniSecurityManagerFactory factory = new IniSecurityManagerFactory();
    SecurityManager instance = factory.createInstance();
    SecurityUtils.setSecurityManager(instance);
    
    Subject subject = SecurityUtils.getSubject();
    System.out.println(subject.getPrincipal());
  }
}
