package LP1.rxg190006;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Num {
	
	public static final int base = 10;
	List<Long> digitList;
	boolean isNumberNegative=false;
	
	public Num(long number) {
		if(number<0) {
			isNumberNegative=true;
			number*=-1;
		}
		digitList = new ArrayList<>();
		while(number>0) {
			digitList.add(new Long(number%10));
			number = number/10;
		}	
	}
	public Num(String s) {
		if (s.charAt(0)=='-') {
			isNumberNegative=true;
		}
		digitList = new ArrayList<>();
		long d;
		for(int i = s.length()-1;i>=0;i--) {
			d = (long)s.charAt(i);
			digitList.add(new Long(d));
		}
	}
	public void printList() {
		System.out.println("Base : "+base);
		for(Long num:digitList) {
			System.out.print("   "+num+"   ");
		}
		System.out.println();
	}
	
	
	public static void main(String s[]) {
		Scanner sc = new Scanner(System.in);
//		System.out.println("Enter 3 Long Numbers ");
//		String  input1 = sc.next();
//		long input2=sc.nextLong();
//		Num x = new Num(input1);
//		Num y = new Num(input2);
//		int z =  sc.nextInt();
		Num x = new Num("45646515649485165651651");
		Num y = new Num(65165156);
		long z = 4;
		System.out.println("number 1 = "+x);
		System.out.println("number 2 = " + y);
//		Num addResult= add(x, y);
//		System.out.println(" Result = "+addResult.printList());
//		Num subResult = subtract(x,y);
//		System.out.println(" Result = "+subResult.printList());
//		Num mulResult = product(x,y);
//		System.out.println(" Result = "+mulResult.printList());
//		Num divResult= div(x, y);
//		System.out.println(" Result = "+divResult.printList());
//		Num powResult = power(x,z);
//		System.out.println(" Result = "+powResult.printList());
//		Num modResult = mod(x,y);
//		System.out.println(" Result = "+modResult.printList());
//		Num rootResult = squareRoot(z);
//		System.out.println(" Result = "+rootResult.printList());
	}

}