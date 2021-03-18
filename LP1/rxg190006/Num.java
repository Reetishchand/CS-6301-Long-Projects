//package com.anj;

/*
 * CS 6301.002 LP1
 * Team Members:
 *  - Reetish Chand Guntakal Patil -> RXG190006
 *  - Umar Khalid -> uxk150630
 *  - Dhara Patel -> dxp190051
 *  - Rohan Vannala -> rxv190003
 */
package rxg190006;

import java.util.*;

public class Num implements Comparable<Num> {

    public static final long base = 10;
    long baseLong = 1000000000;
    ArrayList<Long> digitList;
    boolean isNumberNegative = false;
    int size;

    /*
     * Utility method for Num
     * @return : Num
     */
    public Num(){
        size = 0;
        digitList = new ArrayList<Long>();
    }

    /*
     * Utility method for Num with String input
     * @param String s
     * @return : Num
     */
    public Num(String s) {
        if (s.charAt(0) == '-') {
            isNumberNegative = true;
            s = s.substring(1);
        }
        digitList = new ArrayList<>();
        for (int i = s.length() - 1; i >= 0; i--) {
            digitList.add(Long.parseLong(s.substring(i, i + 1)));
        }
//        System.out.println.println("digitList >> " + digitList);
    }

    /*
     * Utility method for Num with long input
     * @param long num
     * @return : Num
     */
    public Num(long num) {
        this(num,1000000000);
    }

    /*
     * Utility method for Num with long input
     * @param long number
     * @param long base
     * @return : Num
     */
    public Num(long number, long base) {
        long defaultBase = 10;
        defaultBase = base;
        if (number < 0) {
            isNumberNegative = true;
            number *= -1;
        }
        digitList = new ArrayList<>();
        while (number > 0) {
            digitList.add(new Long(number % 10));
            number = number / 10;
        }
//        System.out.println("number >> " + number);
    }

    /*
     * Utility method for Num with List<Long> input
     * @param List<Long> digits
     * @return : Num
     */
    public Num(List<Long> digits){
        this(digits,1000000000);
    }

    /*
     * Utility method for Num with List<Long> input
     * @param List<Long> digits
     * @param long base
     * @return : Num
     */
    public Num(List<Long> digits, long base){
        this.digitList = new ArrayList<Long>(digits);
        this.size = digits.size();
        this.baseLong = base;
    }

    /*
     * Utility method to add two numbers
     * @param a
     * @param b
     * @return : Num
     */
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
        long temp = 0;
        while (l1 > i && l2 > j) {
            if (a.isNumberNegative && !b.isNumberNegative) {
                //For avoiding double check in subtract and infinite loop
                a.isNumberNegative = false;
                b.isNumberNegative = false;
                return subtract(b,a);
            }
            else if (!a.isNumberNegative && b.isNumberNegative) {
                //For avoiding double check in subtract and infinite loop
                a.isNumberNegative = false;
                b.isNumberNegative = false;
                return subtract(a,b);
            }
            else if (a.isNumberNegative && b.isNumberNegative) {
                temp = a.getDigitByIndex(i) + b.getDigitByIndex(j) + carryBit;
                res.isNumberNegative = true;
            }
            else if (!a.isNumberNegative && !b.isNumberNegative) {
                temp = a.getDigitByIndex(i) + b.getDigitByIndex(j) + carryBit;
                res.isNumberNegative = false;
            }

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
        while (l1 > i && carryBit != 0) {
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

        while (l2 > j && carryBit != 0) {
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

    /*
     * Utility method to subtract two numbers
     * @param a
     * @param b
     * @return : Num
     */
    public static Num subtract(Num a, Num b) {
        int largest = findLargest(a, b);
        boolean isNeg = (largest == 1) ? false : true;
        StringBuilder str = new StringBuilder();
        long borrow = 0;
        int i = 0, j = 0;
        int l1 = a.getItemSize(), l2 = b.getItemSize();
        long d1 = 0, d2 = 0;
        Num res = new Num(0, a.base());

        while (i < l1 && j < l2) {
            if (a.isNumberNegative && !b.isNumberNegative) {
                //For avoiding double check in add and infinite loop
                a.isNumberNegative = false;
                b.isNumberNegative = false;
                res = add(a,b);
                res.isNumberNegative = true;
                return res;
            }
            else if (!a.isNumberNegative && b.isNumberNegative) {
                //For avoiding double check in add and infinite loop
                a.isNumberNegative = false;
                b.isNumberNegative = false;
                res = add(a,b);
                res.isNumberNegative = false;
                return res;
            }
            else if (a.isNumberNegative && b.isNumberNegative) {
                //For avoiding double check and infinite loop
                a.isNumberNegative = false;
                b.isNumberNegative = false;
                res = subtract(b,a);
                return res;
            }

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

    /*
     * Utility method to multiply two numbers
     * @param a
     * @param b
     * @return : Num
     */
    public static Num product(Num a, Num b) {
        int l1 = a.getItemSize();
        int l2 = b.getItemSize();
        int i = 0, j = 0;

        Num res = new Num(0, a.base());
        long carryBit = 0;
        long temp;

        //MARK: TODO - Check if a and b have the same base

        if (l1 == 0 || l2 == 0) {
            res = new Num(0, a.base());
            return res;
        }

        while (l1 > i) {
            while (l2 > j) {
                temp = (a.getDigitByIndex(i) * b.getDigitByIndex(j)) + carryBit;
                if (i > 0) {
                    try {
                        res.updateItemInIndex(i + j, temp + res.getDigitByIndex(i + j));
                    } catch (Exception e) {
                        res.addItemToList(temp);
                    }
                }
                else {
                    res.addItemToList(temp);
                }
                carryBit = 0;
                int maxLimit = b.getItemSize() - 1;
                if (j < maxLimit) {
                    carryBit = res.getDigitByIndex(i+j) / 10;
                    if (carryBit > 0)
                        res.updateItemInIndex(i+j, res.getDigitByIndex(i+j) % 10);
                }
                j += 1;
            }
            i += 1;
            if (l1 > i)
                j = 0;
        }
        while (l1 > i) {
            temp = a.getDigitByIndex(l1) + carryBit;
            try {
                res.updateItemInIndex(i, temp);
            } catch (Exception e) {
                res.addItemToList(temp);
            }
            carryBit = res.getDigitByIndex(l1+j) / 10;
            i += 1;
        }
        while (l2 > j) {
            System.out.println(b);
            temp = b.getDigitByIndex(l2) + carryBit;
            try {
                res.updateItemInIndex(j, temp);
            } catch (Exception e) {
                res.addItemToList(temp);
            }
            carryBit = res.getDigitByIndex(i+l2) / 10;
            j += 1;
        }

        if (carryBit != 0) {
            res.addItemToList(carryBit);
        }

        if (a.isNumberNegative && b.isNumberNegative) {
            res.isNumberNegative = false;
        }
        else if ((!a.isNumberNegative && b.isNumberNegative) || (a.isNumberNegative && !b.isNumberNegative)) {
            res.isNumberNegative = true;
        }
        return res;
    }

    /*
     * Utility method to find the power (n) of a number
     * @param x
     * @param n
     * @return : Num
     */
    public static Num power(Num x, long n) {
        Num res = new Num(1, x.base());
        Num temp;
        Num pow = new Num(n, x.base());

        if(n == 0)
            return res;

        Num mid = new Num(2, x.base());
        Num tempPow =  divide(pow, mid);
        if (tempPow.getItemSize() > 0)
            res = power(x,numToLong(tempPow));

        if (mod(pow, mid).getDigitByIndex(0) == 0) {
            res = product(res, res);
            //return res;
        }
        else {
            res = product(res, res);
            res = product(res, x);
            //return res;
        }

        if (n < 0) {
            Num num1 = new Num(1, x.base());
            res =  divide(num1,res);
            return res;
        }
        return res;
    }

    /*
     * Utility method to divide two numbers
     * @param a
     * @param b
     * @return : Num
     */
    public static Num divide(Num a, Num b) {
        boolean minus = false;
        Num zero = new Num(0, a.base());
        Num res = new Num(0, a.base());

        int l1 = a.getItemSize();
        int l2 = b.getItemSize();

        if(b.compareTo(zero) == 0) {
            return null;
        }

        if((l1<0 && l2<0) || (l1>0 && l2>0)) {
            minus = false;
        } else if(l1<0 || l2<0) {
            minus = true;
        }

        long dividend = Math.abs(numToLong(a));
        long divisor = Math.abs(numToLong(b));

        long lo = 1;
        long hi = dividend;

        while(lo<=hi) {
            long mid = lo + (hi-lo)/2;
            if(mid<=(dividend/divisor) && mid+1>(dividend/divisor)) {
                res = new Num(minus ? -mid : mid, a.base());
                if (a.isNumberNegative && b.isNumberNegative) {
                    res.isNumberNegative = false;
                }
                else if ((!a.isNumberNegative && b.isNumberNegative) || (a.isNumberNegative && !b.isNumberNegative)) {
                    res.isNumberNegative = true;
                }
                return res;
            } else if(mid>(dividend/divisor)) {
                hi = mid - 1;
            } else {
                lo = mid + 1;
            }
        }

        return zero;
    }

    /*
     * Utility method to find the modulus of two numbers
     * @param a
     * @param b
     * @return : Num
     */
    public static Num mod(Num a, Num b) {
        Num zero = new Num(0, a.base());
        Num one = new Num(1, a.base());

        if(b.compareTo(zero) == 0) {
            return null;
        }
        if(b.compareTo(one) == 0) {
            return a;
        }

        Num quotient = divide(a, b);
        Num product = product(b, quotient);
        Num remainder = subtract(a, product);

        if (a.isNumberNegative && !b.isNumberNegative) {
            remainder.isNumberNegative = true;
        } else if (a.isNumberNegative && b.isNumberNegative) {
            remainder.isNumberNegative = true;
        } else if (!a.isNumberNegative && b.isNumberNegative) {
            remainder.isNumberNegative = false;
        }
        return remainder;
    }

    /*
     * Utility method to find the square root of a number
     * @param a
     * @return : Num
     */
    public static Num squareRoot(Num a) {
        if (a.isNumberNegative) {
            String message = "Error: Cannot find the square root for a negative number";
            throw new NumberFormatException(message);
        }

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
        return new Num((long) Math.floor(ans), a.base());
    }

    /*
     * Utility method to print list
     */
    public void printList() {
        System.out.println("------ Result ------\n Base : " + base);
        if (isNumberNegative)
            System.out.println(" - ");
        System.out.print(numToLong(this));
        System.out.println();
    }

    /*
     * Utility method to evaluate postfix
     * @param postFixArr
     * @return : Num
     */
    public static Num evaluatePostfix(String[] postFixArr) {
        ArrayDeque<Num> numberStack = new ArrayDeque<>();

        for(int i=0 ; i<postFixArr.length ; i++){
            char current = postFixArr[i].charAt(0);
            if(Character.isDigit(current)){
                numberStack.push(new Num(postFixArr[i]));
            }
            else{
                Num n1 = numberStack.pop();
                Num n2 = numberStack.pop();

                switch (current) {
                    case '+':
                        numberStack.push(add(n2,n1));
                        break;
                    case '-':
                        numberStack.push(subtract(n2,n1));
                        break;
                    case '/':
                        numberStack.push(divide(n2,n1));
                        break;
                    case '*':
                        numberStack.push(product(n2,n1));
                        break;
                    case '%':
                        numberStack.push(mod(n2,n1));
                        break;
                    case '^':
                        numberStack.push(power(n2,numToLong(n1)));
                        break;
                }
            }
        }
        return numberStack.pop();
    }

    /*
     * Utility method to evaluate expression
     * @param inFixStr
     * @return : Num
     */
    public static Num evaluateExp(String inFixStr) {
        return null;
    }

    /*
     * Utility method to check precedence of operators
     * @param op1
     * @param op2
     * @return : Boolean
     */
    public static boolean isPrecedent(String op1, String op2){
        if(op2.equals("(") || op2.equals(")")){
            return false;
        }
        if((op1.equals("*") || op1.equals("/")) && (op2.equals("+") || op2.equals("-"))){
            return false;
        }else{
            return true;
        }
    }

    /*
     * Utility method to evaluate expression
     * @param infixArr
     * @return : Num
     */
    public static Num evaluate(String op,Num n1,Num n2){
        switch(op){
            case "+":
                return add(n2,n1);
            case "-":
                return (subtract(n2,n1));

            case "/":
                return  (divide(n2,n1));

            case "*":
                return  (product(n2,n1));

            case "%":
                return (mod(n2,n1));

            case "^":
                return  (power(n2,numToLong(n1)));

        }
        return new Num(0);
    }

    /*
     * Utility method to find largest number
     * @param a
     * @param b
     * @return : Num
     */
    public static int findLargest(Num a, Num b) {
        int l1 = a.getItemSize(), l2 = b.getItemSize();
        if (l2 == l1) {
            for (int i = l1 - 1; i >= 0; i--) {
                if (a.getDigitByIndex(i) != b.getDigitByIndex(i)) {
                    return a.getDigitByIndex(i) > b.getDigitByIndex(i) ? 1 : 2;
                }
            }
        }
        else {
            return l2 > l1 ? 2 : 1;
        }
        return 1;
    }

    /*
     * Utility method to convert Num to Long
     * @param a
     * @return : Num
     */
    public static Long numToLong(Num a) {
        long res = 0;
        int l = a.getItemSize();
        for (int i = l - 1; i >= 0; i--) {
            res = res * 10 + (a.getDigitByIndex(i));
        }
        return res;
    }

    /*
     * Return number as string without base change.
     * @return
     */
    public String toStringWithoutBaseChange() {
        StringBuilder output = new StringBuilder();
        for(int i=size-1; i>=0; i--)
            output.append(digitList.get(i));
        return String.valueOf(output);
    }
    /*
     * returns base
     * @return base
     */
    public long base() { return base; }

    /*
     * Converts given Num object to new base
     * @param newBase number equal to "this" number, in base=newBase
     * @return result number in new base
     */
    public long getBase() {
        return baseLong;
    }

    public Num convertBase(int base) {
        Num thisNum = new Num(this.digitList);
        ArrayList<Long> newNum =  convertBase(thisNum.digitList,(int)thisNum.baseLong,base);
        Num result = new Num(newNum,base);
        return result;
    }

    /*
     * Converts given array object to array having new base
     * @param thisNumArr Array to be converted
     * @param currentBase current base of given array
     * @param newBase base to which array will be converted
     * @return array with new base
     */
    public ArrayList<Long> convertBase(ArrayList<Long> thisNumArr, int currentBase,int newBase){
        Num zero = new Num(0,currentBase);
        int arrSize = 0;
        Num thisNum = new Num(thisNumArr,currentBase);
        Num b = new Num(newBase,currentBase);
        arrSize = (int) Math.ceil((thisNum.size+1)/Math.log10(newBase)+1);
        ArrayList<Long> newNum= new ArrayList<>();

        while(thisNum.compareTo(zero) > 0){
            newNum.add( Long.parseLong(mod(thisNum,b).toStringWithoutBaseChange()));
            thisNum = divide(thisNum,b);
        }
        ArrayList<Long> output;
        //removing trailing zeros
        int k = newNum.size() -1;
        while(k>=0 && newNum.get(k)== 0)
            k--;
        if(k == -1) {
            output = new ArrayList<Long>();
            output.add(new Long(0));
            return output;
        }
        if(k == 0) {
            output = new ArrayList<Long>();
            output.add(newNum.get(0));
            return output;
        }
        return newNum;
    }

    /*
     * Utility method to add item to list
     * @param item
     */
    public void addItemToList(long item) {
        digitList.add(item);
    }

    /*
     * Utility method to get item from list by index
     * @param index
     * @return : long
     */
    public long getDigitByIndex(int index) {
        return digitList.get(index);
    }

    /*
     * Utility method to get item size
     * @return : int
     */
    public int getItemSize() {
        return digitList.size();
    }

    /*
     * Utility method to get item from list by index
     * @param index
     * @return : long
     */
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

    /*
     * Utility method to update item in index
     * @param index
     * @param newItem
     */
    public void updateItemInIndex(int index, long newItem) {
        digitList.set(index, newItem);
    }

    /*
     * Utility method to compare two signed numbers
     * @param other
     * @return int
     */
    public int signedCompareTo(Num other) {
        if (this.isNumberNegative && other.isNumberNegative)
            return -1 * compareTo(other);
        else if (!this.isNumberNegative && !other.isNumberNegative)
            return compareTo(other);
        else if (this.isNumberNegative && !other.isNumberNegative)
            return -1;
        else
            return 1;
    }

    /*
     * Utility method to compare two numbers
     * @param other
     * @return int
     */
    public int compareTo(Num other) {
        //For unequal lengths
        if (this.getItemSize() < other.getItemSize()) {
            return 1;
        }
        if (other.getItemSize() < this.getItemSize()) {
            return -1;
        } else {
            //For same length
            int index = this.getItemSize() - 1;
            while (index > -1) {
                // Check for this < other
                if (this.digitList.get(index) < other.digitList.get(index))
                    return 1;

                // Check for this > other
                if (other.digitList.get(index) < this.digitList.get(index))
                    return -1;
                index--;
            }
        }
        //If numbers are equal
        return 0;
    }

    /*
     * Main method
     */
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
                y = new Num(input2, base);
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
                        result = product(x, y);
                        break;
                    case 4:
                        System.out.println("Division");
                        result = divide(x, y);
                        break;
                    case 5:
                        System.out.println("Mod");
                        result = mod(x, y);
                        break;
                    case 6:
                        System.out.println("Power");
                        result = power(x, input2);
                        break;
                    default:
                        throw new Exception("Enter a valid input");
                }
            } else if (choice == 7) {
                System.out.println("Square Root \n Enter a number :");
                input2 = sc.nextLong();
                x = new Num(input2, base);
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
                        result = evaluatePostfix(postFixArr);
                        break;
                    case 9:
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
}
