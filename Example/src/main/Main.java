package main;
public class Main {

	public static void main(String[] args) {
		UnitPro u=new UnitPro();
		try {
			u.parse("sinx+x^2");
			System.out.println(u.cal(10));
		} catch (ExpressionErrorException e) {
			e.printStackTrace();
		}
	}

}
