package com.util;





import java.io.InputStream;
import java.util.Scanner;

public class ScannerUtil {
     static Scanner scanner = new Scanner(System.in);

    public static int lerInteiro(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextInt();
    }

    public static double lerDouble(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextDouble();
    }

    public static String lerString(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine();
    }
    
    public void Scanner(InputStream in) {
		// TODO Auto-generated constructor stub
	}

}
