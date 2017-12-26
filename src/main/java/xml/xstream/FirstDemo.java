package xml.xstream;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class FirstDemo {
  public static void main(String[] args) {
    User user = fromFile(System.getProperty("user.dir")+"/src/main/java/xml/xstream/FirstDemo.xml");
    System.out.println(user);
  }

  public static XStream x;
  static {
    if (x == null) {
      x = new XStream(new DomDriver());
    }
    x.addImmutableType(User.class);
  }

  public static User fromFile(String file) {
    System.out.println(file);
    Reader in = null;
    User cfg = null;
    try {
      in = new InputStreamReader(new FileInputStream(file), "utf-8");
      cfg = (User) x.fromXML(in);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        in.close();
      } catch (IOException e) {
      }
    }
    return cfg;
  }
}
