/*
 *  CS 6301.002 LP-1
 * Team Members:
 *  Reetish Chand Guntakal Patil - RXG190006
 * 
 */

package LP1.rxg190006;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class Num {

	public static final int base = 10;
	List<Long> digitList;
	boolean isNumberNegative = false;

	public Num(long number) {
		if (number < 0) {
			isNumberNegative = true;
			number *= -1;
		}
		digitList = new ArrayList<>();
		while (number > 0) {
			digitList.add(new Long(number % 10));
			number = number / 10;
		}
	}

	public Num(String s) {
		if (s.charAt(0) == '-') {
			isNumberNegative = true;
		}
		digitList = new ArrayList<>();
		for(int i=s.length()-1;i>=0;i--){
			digitList.add(Long.parseLong(s.substring(i,i+1)));
		}
	}

	public void printList() {
		System.out.println("Base : " + base);
		int l = getItemSize();
		if (isNumberNegative)
			System.out.print(" - ");
		for (int i=l-1;i>=0;i--) {
			System.out.print("   " + digitList.get(i)+ "   ");
		}
		
		System.out.println();
	}
	public static Long numToLong(Num a) {
		 long res =0;
		 int l=a.getItemSize();
		for (int i=l-1;i>=0;i--) {
			res = res*10 + (a.getDigitByIndex(i));
		}
		 System.out.println("converted : "+res);
		 return res;
	 }
	
	public long getDigitByIndex(int index) {
		return digitList.get(index);
	}
	
	public int getItemSize() {
		return digitList.size();
	}
	public void addItemToList(long item) {
		digitList.add(item);
	}
	public void updateItemInIndex(int index,long newItem) {
		digitList.set(index, newItem);
	}
	
	public static Num squareRoot(Num a) {
		a.printList();
		Long number = numToLong(a);
		long start = 0, end = number; 
        long mid; 
        double ans = 0.0;
        while (start <= end)  
        { 
            mid = (start + end) / 2; 
              
            if (mid * mid == number)  
            { 
                ans = mid; 
                break; 
            } 
  
            if (mid * mid < number) { 
                start = mid + 1; 
                ans = mid; 
            } 
  
            else { 
                end = mid - 1; 
            } 
        }
       
       
        System.out.println("root ans :"+ans);
        return new Num((long)Math.floor(ans));
        
        
	}
	
	
	
public static Num add(Num a, Num b) {
		
		int l1=a.getItemSize();
		int i=0,j=0;
		int l2=b.getItemSize();
		Num res;
		long carryBit=0;
		if (l1<l2)
			 res = b;
		else
			 res =a;
		long temp;
		while (l1>i && l2>j) {
			temp = a.getDigitByIndex(i)+b.getDigitByIndex(j)+carryBit;
			if (temp>9) {
				carryBit=1;
				temp = temp-10;
			}
			else {
				carryBit=0;
			}
			res.updateItemInIndex(i, temp);
			i+=1;
			j+=1;
					
		}
		while (l1>i) {
			temp = a.getDigitByIndex(i)+carryBit;
			if (temp>9) {
				carryBit=1;
				temp = temp-10;
			}
			else {
				carryBit=0;
			}
			try {
			res.updateItemInIndex(i, temp);
			}
			catch(Exception e){
				res.addItemToList(temp);
			}
			i+=1;
		}
		
		while (l2>j) {
			temp = b.getDigitByIndex(j)+carryBit;
			if (temp>9) {
				carryBit=1;
				temp = temp-10;
			}
			else {
				carryBit=0;
			}
			try {
				res.updateItemInIndex(i, temp);
				}
				catch(Exception e){
					res.addItemToList(temp);
				}
			j+=1;
		}
		if(carryBit!=0)
			res.addItemToList(carryBit);
	return res;
	}
	
	
	public String toString() {
		StringBuilder stemp = new StringBuilder();
		if (isNumberNegative)
			stemp.append("-");
		for (int i = digitList.size() - 1; i >= 0; i--) {
			stemp.append(digitList.get(i));
		}
		String str = new String(stemp);
		return str;
	}

	public static Num evaluatePostFix(String[] postFixArr) {
		Stack<Num> numberStack = new Stack<>();
		for (int i = 0; i < postFixArr.length; i++) {
			char current = postFixArr[i].charAt(0);
			if (Character.isDigit(current))
				numberStack.push(new Num(new Long(current)));
			else {
				Num n1 = numberStack.pop();
				Num n2 = numberStack.pop();
				switch (current) {
				case '+':
//                    	numberStack.push(add(n1,n2)); 
					break;

				case '-':
//                    	numberStack.push(subtract(n1,n2)); 
					break;

				case '/':
//                    	numberStack.push(subtract(n1,n2)); 
					break;

				case '*':
//                    	numberStack.push(subtract(n1,n2)); 
					break;

				case '%':
//                    	numberStack.push(mod(n1,n2)); 
					break;

				case '^':
//                    	numberStack.push(pow(n1,n2)); 
					break;

				}
			}
		}
		return numberStack.pop();
	}

	private static Num evaluateExp(String[] postFixArr) {
		return null;
	}
	
	public static Num subtract(Num a, Num b) {
		Long n1 = numToLong(a);
		Long n2 = numToLong(b);
		return new Num(n1-n2);
	}

	
	

	public static void main(String s[]) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter 2 Long Numbers ");
		String  input1 = sc.next();
		long input2=sc.nextLong();
		Num x ;
		Num y ;
//		int z =  sc.nextInt();
//		Num x = new Num("100");
//		Num y = new Num(125);
		x= new Num(input1);
		y	= new Num(input2);
		System.out.println("number 1 = " + x);
		System.out.println("number 2 = " + y);
		Num addResult= add(x, y);
		System.out.println(" addition Result = ");
		addResult.printList();
		x= new Num(input1);
		y		= new Num(input2);
		Num subResult = subtract(x,y);
		System.out.println(" subtraction Result = ");
		subResult.printList();
		//		Num mulResult = product(x,y);
//		System.out.println(" Result = "+mulResult.printList());
//		Num divResult= div(x, y);
//		System.out.println(" Result = "+divResult.printList());
//		Num powResult = power(x,z);
//		System.out.println(" Result = "+powResult.printList());
//		Num modResult = mod(x,y);
//		System.out.println(" Result = "+modResult.printList());
		x= new Num(input1);
		y	= new Num(input2);
		Num rootResult = squareRoot(x);
		System.out.println(" Root Result = ");
		rootResult.printList();
//		System.out.println("Enter the postfix Expression : ");
//		String postFixString = sc.next();
//		String[] postFixArr = new String[postFixString.length()];
//		for (int i = 0; i < postFixArr.length; i++) {
//			postFixArr[i] = postFixString.substring(i, i + 1);
//		}
//		Num postFixEval = evaluatePostFix(postFixArr);
//		System.out.println(" Result = ");
//		postFixEval.printList();
//
//		System.out.println("Enter the  Expression : ");
//		String expression = sc.next();
//
//		Num evalExp = evaluateExp(postFixArr);
//		System.out.println(" Result = ");
//		evalExp.printList();

	}

}
