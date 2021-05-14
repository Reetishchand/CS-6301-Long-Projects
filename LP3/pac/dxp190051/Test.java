package dxp190051;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        Scanner sc;

        System.out.println("Works");
        System.out.println(args[0]);

        if (args.length > 0) {
            File file = new File(args[0]);
            try {
                sc = new Scanner(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            sc = new Scanner(System.in);
        }
    }
}
