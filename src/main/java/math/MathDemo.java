package math;

public class MathDemo {

	public static void main(String[] args) {
		double a = 1.002;
		for (int i = 0; ; i++) {
			a*=1.002;
			if(a>5) {
				System.out.println(i);
				break;
			}
		}
	}

}
