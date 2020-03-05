package test;

import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import MODEL.*;
import sign_process.sign_process;

/*
 *    算法实现及拆分可行性测试
 */

public class test {
	public static BigInteger gcd(BigInteger a, BigInteger b) {// 辗转相除法的实现
		BigInteger r, temp;
		BigInteger zero;
		zero = BigInteger.ZERO;
		if (a.compareTo(b) == -1) {
			temp = a;
			a = b;
			b = temp;
		}
		r = a.mod(b);
		while (!r.equals(zero)) {
			a = b;
			b = r;
			r = a.mod(b);
		}
		return b;
	}
	public static byte[] toByteArray(BigInteger bi) {
	    byte[] array = bi.toByteArray();
	    if (array[0] == 0) {
	      byte[] tmp = new byte[array.length - 1];
	      System.arraycopy(array, 1, tmp, 0, tmp.length);
	      array = tmp;
	    }
	    return array;
	}
	static public void pb(byte b[]) {
		for(int i=0; i<b.length;i++)
			System.out.print(b[i]+" ");
		System.out.println();
	}
	
	public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
		// TODO Auto-generated method stub
		sign_process sp = new sign_process();
		if(!sp.init())
			System.out.println("failure");
		if(!sp.sign())
			System.out.println("failure");
		if(!sp.dispute_settlement())
			System.out.println("failure");
		
	}

}
