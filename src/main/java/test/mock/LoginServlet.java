package test.mock;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginServlet {
  public void service(HttpServletRequest request,HttpServletResponse response){
    String username = request.getParameter("username");
    System.out.println(username);
    if("zhangsan".equals(username)){
      System.out.println("ok");
    }else{
      throw new RuntimeException("login fail");
    }
  }
}
