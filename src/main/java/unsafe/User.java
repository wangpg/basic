package unsafe;

public class User {
  private String name = "zhangsan";
  private int age = 20;
  double height = 1.77;

  public User() {
    super();
    System.out.println("无参构造");
  }

  public User(String name, int age) {
    super();
    this.name = name;
    this.age = age;
    System.out.println("有参构造");
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  @Override
  public String toString() {
    return "User [name=" + name + ", age=" + age + ",height===" + height + "]";
  }

}
