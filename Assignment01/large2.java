package assignmentday1;
import java.util.Scanner;
public class large2 {
public static void main(String[] args) {
	Scanner sc = new Scanner(System.in);
	System.out.println("Enter the first num1");
	int num1=sc.nextInt();
	System.out.println("enter the second num2");
	int num2=sc.nextInt();
	sc.close();
	
	if(num1>num2) {
		System.out.print("Number1 is larger num");
	}
	else if(num1==num2) {
		System.out.print("both numbers are equal");
	}
	else {
		System.out.print("Number2 is larger");
	}
}
}
S