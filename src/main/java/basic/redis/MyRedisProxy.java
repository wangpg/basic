package basic.redis;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

/** 
 * 负载均衡 推荐使用开源代理成熟服务
 * @author wang123
 *
 */
public class MyRedisProxy {
  static List<String> servers;
  static {
    servers.add("127.0.0.1:6380");
    servers.add("127.0.0.1:6381");
    servers.add("127.0.0.1:6382");
  }
  
  public static void main(String[] args) throws Exception {
    ServerSocket ss = new ServerSocket(19000);
    Socket s = null;
    while((s=ss.accept())!=null){
      OutputStream os = s.getOutputStream();
      InputStream is = s.getInputStream();
      //获取报文
      byte[] request = new byte[1024];
      is.read(request);
      String req = new String(request);
      
      String[] arr = req.split("\r\n");
      int keylengths = Integer.parseInt(arr[3].split("\\$")[1]);//根据key的长度负载均衡
      
      int mod = keylengths%servers.size();
      
      String[] serverinfo = servers.get(mod).split(":");
      Socket client = new Socket(serverinfo[0],Integer.parseInt(serverinfo[1]));
      //读取响应
      //socket写响应给原客户端
    }
  }
}
