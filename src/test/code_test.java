package test;

import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import MODEL.*;

/*
 *    算法实现及拆分可行性测试
 */

public class code_test {
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
		RSA r = new RSA();
		r.CreateKey();
		PrivateKey sk = r.getSk();
		PublicKey pk = r.getPk();
		byte d[] = new Digest().generate_digest("file/contract.txt");
		byte sign[] = new Signature().generateSignature(sk, d);
		//pb(sign);
		new Signature().verifySignature(pk, d, sign);
		
		Random rand = new Random();
		BigInteger temp = new BigInteger(1, sk.get_D());
		int t = temp.intValue();
		BigInteger temp1 = null;
		while(true) {
			int s = rand.nextInt(t-2)+1;
			if (gcd(new BigInteger(String.valueOf(s)), new BigInteger(1,sk.getPhi_n())).equals(BigInteger.ONE)) {
				temp1 = new BigInteger(String.valueOf(s));
				break;
			}
		}			
		
		
		PrivateKey sk1 = new PrivateKey(toByteArray(temp1),sk.get_N(),sk.getPhi_n());
		PrivateKey sk2 = new PrivateKey(toByteArray(temp.subtract(temp1)),sk.get_N(),sk.getPhi_n());
		
		System.out.print("SK : ");
		System.out.print(temp+" = ");
		pb(sk.get_D());
		System.out.println("PK : " + new BigInteger(1, RSA.computePublicKey(sk).get_E()));
		System.out.print("SIGN : ");
		sign = new Signature().generateSignature(sk, d);
		System.out.println(new BigInteger(1, sign));
		
		
		System.out.print("SK1 : ");
		System.out.print(temp1+" = ");
		pb(sk1.get_D());
		System.out.println("PK1 : " + new BigInteger(1, RSA.computePublicKey(sk1).get_E()));
		System.out.print("SIGN1 : ");
		byte sign1[] = new Signature().generateSignature(sk1, d);
		System.out.println(new BigInteger(1, sign1));
		
		System.out.print("SK2 : ");
		System.out.print(temp.subtract(temp1)+" = ");
		pb(sk2.get_D());
		byte sign2[] = new Signature().generateSignature(sk2, d);
		System.out.print("SIGN2 : ");
		System.out.println(new BigInteger(1, sign2));
		
		
		System.out.print("使用SIGH1和SIGN2计算SIGN结果为 ");
		System.out.println(new BigInteger(1, sign1).multiply(new BigInteger(1, sign2)).mod(new BigInteger(1, sk.get_N())));
		
	}

}
