package MODEL;

import java.util.*;
import MODEL.PrivateKey;
import MODEL.PublicKey;
import java.math.*;



public class RSA {

	BigInteger p, q;
	PrivateKey sk = null;
	PublicKey pk = null;
	public PrivateKey getSk() {
		return sk;
	}

	public void setSk(PrivateKey sk) {
		this.sk = sk;
	}

	public PublicKey getPk() {
		return pk;
	}

	public void setPk(PublicKey pk) {
		this.pk = pk;
	}
	
	/*byte PublicKey[][] = new byte[2][];
	byte PrivateKey[][] = new byte[2][];*/

	// 由于幂次运算及随机数选取需要用到int形式，故先定义一些对应的int变量

	public RSA(){
		p = new BigInteger("53");
		q = new BigInteger("23");
		// 将p,q临时转换为int形式（但若p*q较大，则下面的步骤会出问题）
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
		BigInteger  e, d, n, phi_n, plain, cipher;
		int e_temp, d_temp, p_temp, q_temp;
		p_temp = Integer.valueOf(p.toString());
		q_temp = Integer.valueOf(q.toString());
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
				pk = new PublicKey(toByteArray(e),toByteArray(n),toByteArray(phi_n));
				/*PublicKey[0] = toByteArray(e);
				PublicKey[1] = toByteArray(n);*/
				break;
			}
		}
		for (k = BigInteger.ONE;; k = k.add(one)) {// 求解私钥d
			if ((((k.multiply(phi_n)).add(one)).mod(e)).equals(zero)) {
				d = ((k.multiply(phi_n)).add(one)).divide(e);
				if (d.equals(e)) {
					continue;
				} else {
					sk = new PrivateKey(toByteArray(d),toByteArray(n),toByteArray(phi_n));
					/*PrivateKey[0] = toByteArray(d);
					PrivateKey[1] = toByteArray(n);*/
					System.out.println("私钥：d=" + d + "；n=" + n);
					break;
				}
			}
		}
		System.out.println("-----生成秘钥已完成-----");

	}
	//根据私钥计算公钥
	static public PublicKey computePublicKey(PrivateKey s) {
		PublicKey r = null;
		BigInteger k;
		BigInteger one;
		one = BigInteger.ONE;
		BigInteger zero;
		zero = BigInteger.ZERO;
		BigInteger n = new BigInteger(1, s.get_N());
		BigInteger phi_n = new BigInteger(1, s.getPhi_n());
		BigInteger d = new BigInteger(1, s.get_D());
		BigInteger e = null;
		for (k = BigInteger.ONE;; k = k.add(one)) {
			if ((((k.multiply(phi_n)).add(one)).mod(d)).equals(zero)) {
				e = ((k.multiply(phi_n)).add(one)).divide(d);
				if (e.equals(d)) {
					continue;
				} else {
					r = new PublicKey(toByteArray(e),toByteArray(n),toByteArray(phi_n));
					/*PrivateKey[0] = toByteArray(d);
					PrivateKey[1] = toByteArray(n);*/
					System.out.println("私钥：" + d + "  计算的公钥：" + e);
					break;
				}
			}
		}
		
		return r;
		
	}

}