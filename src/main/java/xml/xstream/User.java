package xml.xstream;

import java.util.List;

import javax.xml.bind.annotation.XmlList;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("user")
public class User {

  String name;
  int age;
  
  @XStreamAlias("books")
  Books books;
  
  
  static class Books{
    @XmlList
    @XStreamAlias("book")
    List<Book> bookList;
  }
  
  static class Book{
    String name;
  }
}
