package basic.redis;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * 协议的内容和aof一样
 * 有额可以使用serversocket,让jedis发到这个serversocket,然后打印请求信息
 * @author wang123
 *
 */
public class MyRedisClient {
  Socket socket;
  InputStream reader;
  OutputStream writer;

  public MyRedisClient() throws IOException {
    socket = new Socket("127.0.0.1", 6379);
    reader = socket.getInputStream();
    writer = socket.getOutputStream();
  }

  public String set(String key, String value) throws Exception {
    StringBuilder sb = new StringBuilder();
    sb.append("*3").append("\r\n");
    sb.append("$3").append("\r\n");
    sb.append("SET").append("\r\n");
    sb.append("$").append(key.getBytes().length).append("\r\n");
    sb.append(key).append("\r\n");
    sb.append("$").append(value.getBytes().length).append("\r\n");
    sb.append(value).append("\r\n");

    writer.write(sb.toString().getBytes());

    
    byte[] buffer = new byte[1024];
    reader.read(buffer);
    return new String(buffer);
  }
  
  
  public Pipeline getPipeline(){
    return new Pipeline(reader, writer);
  }
}
