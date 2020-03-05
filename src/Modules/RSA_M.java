package Modules;

import java.util.*;

import java.math.*;



public class RSA_M {

	public  BigInteger p, q, e, d, n, phi_n, plain, cipher;
	 byte PublicKey[][] = new byte[2][];
	 byte PrivateKey[][] = new byte[2][];
	public  int e_temp, d_temp, p_temp, q_temp;

	// 由于幂次运算及随机数选取需要用到int形式，故先定义一些对应的int变量

	public RSA_M(){
		p = new BigInteger("53");
		q = new BigInteger("23");
		// 将p,q临时转换为int形式（但若p*q较大，则下面的步骤会出问题）
		p_temp = Integer.valueOf(p.toString());
		q_temp = Integer.valueOf(q.toString());
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

	public static boolean isPrime(int a) {// 判断是否是素数
		boolean flag = true;
		if (a < 2) {
			return false;
		} else {
			for (int i = 2; i <= Math.sqrt(a); i++) {
				if (a % i == 0) {
					flag = false;
					break;
				}
			}
		}
		return flag;
	}



	public void CreateKey() {// 生成公钥私钥流程

		System.out.println("-----以下为秘钥生成过程-----");
		BigInteger k;
		BigInteger one;
		one = BigInteger.ONE;
		BigInteger zero;
		zero = BigInteger.ZERO;
		n = p.multiply(q);// 计算出n和phi_n(欧拉函数)
		phi_n = p.subtract(one).multiply(q.subtract(one));
		
		for (;;) {
			e_temp = (int) (1 + Math.random() * p_temp * q_temp);
			e = BigInteger.valueOf(e_temp);// 随机生成e
			if (gcd(e, phi_n).equals(one)) {// 判断e是否与phi_n互质
				System.out.println("公钥：e=" + e + "；n=" + n);
				PublicKey[0] = toByteArray(e);
				PublicKey[1] = toByteArray(n);
				break;
			}
		}
		for (k = BigInteger.ONE;; k = k.add(one)) {// 求解私钥d
			if ((((k.multiply(phi_n)).add(one)).mod(e)).equals(zero)) {
				d = ((k.multiply(phi_n)).add(one)).divide(e);
				if (d.equals(e)) {
					continue;
				} else {
					PrivateKey[0] = toByteArray(d);
					PrivateKey[1] = toByteArray(n);
					System.out.println("私钥：d=" + d + "；n=" + n);
					break;
				}
			}
		}
		System.out.println("-----生成秘钥已完成-----");

	}

	public  byte[][] getPublicKey() {
		return PublicKey;
	}

	

	public  byte[][] getPrivateKey() {
		return PrivateKey;
	}

	


}