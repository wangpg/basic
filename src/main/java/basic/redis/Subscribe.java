package basic.redis;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Subscribe {
  InputStream reader;
  OutputStream writer;
  public Subscribe(InputStream reader, OutputStream writer) {
    this.reader = reader;
    this.writer = writer;
  }
  
  public void sub(String topic) throws IOException{
    StringBuilder sb = new StringBuilder();
    sb.append("*2").append("\r\n");
    sb.append("$9").append("\r\n");
    sb.append("SUBSCRIBE").append("\r\n");
    sb.append("$").append(topic.getBytes().length).append("\r\n");
    sb.append(topic).append("\r\n");

    writer.write(sb.toString().getBytes());
    
    //持续订阅，收到消息
    while(true){
      byte[] buffer = new byte[100000];
      int a = reader.read(buffer);
      System.out.println(new String(buffer,0,a));
    }
  }
}
