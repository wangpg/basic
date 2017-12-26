package test.mock;

import javax.servlet.http.HttpServletRequest;

import org.easymock.EasyMock;

public class TestLoginFailedByMock {
  public static void main(String[] args) {
    //MocksControl mc = MocksControl.getControl(HttpServletRequest.class);
    HttpServletRequest request = EasyMock.createMock(HttpServletRequest.class);
    System.out.println(request);
    EasyMock.expect(request.getParameter("username")).andReturn("zhangsan");
    
    new LoginServlet().service(request, null);
  }
}
