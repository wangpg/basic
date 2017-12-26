package basic.redis;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Pipeline {
  InputStream reader;
  OutputStream writer;
  public Pipeline(InputStream reader, OutputStream writer) {
    this.reader = reader;
    this.writer = writer;
  }
  
  public String response() throws IOException{
    byte[] buffer = new byte[100000];
    int a = reader.read(buffer);
    return new String(buffer,0,a);
  }
}
