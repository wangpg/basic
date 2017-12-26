package basic.anno.yuan;

import java.lang.annotation.Annotation;

public class Sub extends Person {
  public static void main(String[] args) {
    Annotation[] annotations = Sub.class.getAnnotations();
    for (Annotation annotation : annotations) {
      System.out.println(annotation.annotationType().getName());
    }
  }
}
