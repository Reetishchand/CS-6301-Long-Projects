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

	public static Num add(Num a, Num b) {

		int l1 = a.getItemSize();
		int i = 0, j = 0;
		int l2 = b.getItemSize();
		Num res;
		long carryBit = 0;
		if (l1 < l2)
			res = b;
		else
			res = a;
		long temp;
		while (l1 > i && l2 > j) {
			temp = a.getDigitByIndex(i) + b.getDigitByIndex(j) + carryBit;
			if (temp > 9) {
				carryBit = 1;
				temp = temp - 10;
			} else {
				carryBit = 0;
			}
			res.updateItemInIndex(i, temp);
			i += 1;
			j += 1;

		}
		while (l1 > i) {
			temp = a.getDigitByIndex(i) + carryBit;
			if (temp > 9) {
				carryBit = 1;
				temp = temp - 10;
			} else {
				carryBit = 0;
			}
			try {
				res.updateItemInIndex(i, temp);
			} catch (Exception e) {
				res.addItemToList(temp);
			}
			i += 1;
		}

		while (l2 > j) {
			temp = b.getDigitByIndex(j) + carryBit;
			if (temp > 9) {
				carryBit = 1;
				temp = temp - 10;
			} else {
				carryBit = 0;
			}
			try {
				res.updateItemInIndex(j, temp);
			} catch (Exception e) {
				res.addItemToList(temp);
			}
			j += 1;
		}
		if (carryBit != 0)
			res.addItemToList(carryBit);
		return res;
	}

	private static Num evaluateExp(String postFixArr) {
		return null;
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

	public static int findLargest(Num a, Num b) {
		int l1 = a.getItemSize(), l2 = b.getItemSize();
		if (l2 == l1) {
			for (int i = l1 - 1; i >= 0; i--) {
				if (a.getDigitByIndex(i) != b.getDigitByIndex(i)) {
					return a.getDigitByIndex(i) > b.getDigitByIndex(i) ? 1 : 2;
				}
			}
		} else {
			return l2 > l1 ? 2 : 1;
		}
		return 1;
	}

	public static void main(String s[]) {
		Scanner sc = new Scanner(System.in);
		String input1;
		long input2;
		Num x = null;
		Num y = null;
		Num result = null;
		System.out.println(
				"choose your operation: \n 1. Add\t2. Subtract\t3. Multiply\t4. Division\t5. Mod\t6. Power\n7. Square Root\t 8. Evaluate Postfix Expression\n9. Explore Recursive Descent Parser");
		int choice = sc.nextInt();
		try {
			if (choice >= 1 && choice <= 6) {
				System.out.println("Enter 1st  input : ");
				input1 = sc.next();
				System.out.print("Enter 2nd input: ");
				input2 = sc.nextLong();
				x = new Num(input1);
				y = new Num(input2);
				switch (choice) {
				case 1:
					System.out.println("Addition");
					result = add(x, y);
					break;
				case 2:
					System.out.println("Subtraction");
					result = subtract(x, y);
					break;

				case 3:
					System.out.println("Multiplication");
//					result = product(x, y);
					break;
				case 4:
					System.out.println("Division");
					if (input2 != 0) {
//						result = division(x, y);
					}
					else {
						result = new Num(0);
					}
					break;
					
					
				case 5:
					System.out.println("Mod");
//					result = mod(x, y);
					break;
				case 6:
					System.out.println("Power");
//					result = power(x, input2);
					break;
				default:
					throw new Exception("Enter a valid input");
				}
			} else if (choice == 7) {
				System.out.println("Square Root \n Enter a number :");
				input2 = sc.nextLong();
				x = new Num(input2);
				if (input2 < 0)
					throw new Exception("Can't find square root for a negative number");
				result = squareRoot(x);
			} else if (choice > 7 && choice <= 9) {
				System.out.println("Enter a valid expression: ");
				input1 = sc.next();
				switch (choice) {
				case 8:
					String[] postFixArr = new String[input1.length()];
					for (int i = 0; i < postFixArr.length; i++) {
						postFixArr[i] = input1.substring(i, i + 1);
					}
					result = evaluatePostFix(postFixArr);
					break;
				case 9:

					result = evaluateExp(input1);
					break;
				}
			} else {
				throw new Exception("Enter a valid input");

			}

			result.printList();

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}

	}

	public static Long numToLong(Num a) {
		long res = 0;
		int l = a.getItemSize();
		for (int i = l - 1; i >= 0; i--) {
			res = res * 10 + (a.getDigitByIndex(i));
		}
		return res;
	}

	public static Num squareRoot(Num a) {
		
		Long number = numToLong(a);
		long start = 0, end = number;
		long mid;
		double ans = 0.0;
		while (start <= end) {
			mid = (start + end) / 2;

			if (mid * mid == number) {
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

		return new Num((long) Math.floor(ans));

	}

	public static Num subtract(Num a, Num b) {
		int largest = findLargest(a, b);
		boolean isNeg = (largest == 1) ? false : true;
		StringBuilder str = new StringBuilder();
		long borrow = 0;
		int i = 0, j = 0;
		int l1 = a.getItemSize(), l2 = b.getItemSize();
		long d1 = 0, d2 = 0;
		while (i < l1 && j < l2) {

			long current = 0;
			d1 = a.getDigitByIndex(i);
			d2 = b.getDigitByIndex(j);
			if (largest == 1) {
				d1 = d1 - borrow;
				if (d1 >= d2) {

					current = d1 - d2;
					borrow = 0;
				} else {
					d1 += 10;
					borrow = 1;
					current = d1 - d2;
				}
			} else {
				d2 = d2 - borrow;
				if (d2 >= d1) {
					current = d2 - d1;
					borrow = 0;
				} else {
					borrow = 1;
					current = d2 + 10 - d1;

				}
			}

			str.append(String.valueOf(current));
			i += 1;
			j += 1;

		}
		while (i < l1) {
			d1 = a.getDigitByIndex(i) - borrow;
			i += 1;
			borrow = 0;
			str.append(String.valueOf(d1));

		}
		while (j < l2) {
			d2 = b.getDigitByIndex(j) - borrow;
			j += 1;
			borrow = 0;
			str.append(String.valueOf(d2));
		}

		if (isNeg) {
			str.append("-");
		}
		str = str.reverse();
		return new Num(str.toString());

	}

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
			s = s.substring(1);
		}
		digitList = new ArrayList<>();
		for (int i = s.length() - 1; i >= 0; i--) {
			digitList.add(Long.parseLong(s.substring(i, i + 1)));
		}
	}

	public void addItemToList(long item) {
		digitList.add(item);
	}

	public long getDigitByIndex(int index) {
		return digitList.get(index);
	}

	public int getItemSize() {
		return digitList.size();
	}

	public void printList() {
		System.out.println("------ Result ------\n Base : " + base);
		if (isNumberNegative)
			System.out.print(" - ");
		System.out.print(numToLong(this));
		System.out.println();
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

	public void updateItemInIndex(int index, long newItem) {
		digitList.set(index, newItem);
	}

}
