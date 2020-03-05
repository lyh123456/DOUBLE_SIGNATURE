package Nodes;

import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.SignatureException;
import java.util.Map;
import java.util.Random;

import MODEL.*;

/**
 * 
 * @author hp
 * 
 * 
 * 
 * 
 */

public class Client {
	//公私钥
	private PrivateKey sk = null;
	private PublicKey pk = null;
	//拆分的公私钥
	public PrivateKey sk1 = null;
	public PrivateKey sk2 = null;
	public PublicKey pk1 = null;
	
	
	//合同摘要
	private byte digest[] = null;
	//签名
	private byte sign[] = null;
	public byte sign1[] = null;
	public byte sign2[] = null;
	
	public Client() {
		//初始化公钥和私钥
		RSA rsa = new RSA();
		rsa.CreateKey();
		pk = rsa.getPk();
		sk = rsa.getSk();
		//拆分私钥
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
		sk1 = new PrivateKey(toByteArray(temp1),sk.get_N(),sk.getPhi_n());
		sk2 = new PrivateKey(toByteArray(temp.subtract(temp1)),sk.get_N(),sk.getPhi_n());
		pk1 = RSA.computePublicKey(sk1);
		
		try {
			//初始化摘要
			Digest d = new Digest();
			digest = d.generate_digest("file/contract.txt");
			//初始化签名
			sign = new Signature().generateSignature(sk, digest);
			sign1 = new Signature().generateSignature(sk1, digest);
			sign2 = new Signature().generateSignature(sk2, digest);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	public PublicKey getPk() {
		return pk;
	}


	public void setPk(PublicKey pk) {
		this.pk = pk;
	}


	public PrivateKey getSk1() {
		return sk1;
	}


	public void setSk1(PrivateKey sk1) {
		this.sk1 = sk1;
	}


	public PrivateKey getSk2() {
		return sk2;
	}


	public void setSk2(PrivateKey sk2) {
		this.sk2 = sk2;
	}


	public PublicKey getPk1() {
		return pk1;
	}


	public void setPk1(PublicKey pk1) {
		this.pk1 = pk1;
	}


	public byte[] getSign1() {
		return sign1;
	}


	public void setSign1(byte[] sign1) {
		this.sign1 = sign1;
	}


	public byte[] getSign2() {
		return sign2;
	}


	public void setSign2(byte[] sign2) {
		this.sign2 = sign2;
	}


	public byte[] getDigest() {
		return digest;
	}

	public void setDigest(byte[] digest) {
		this.digest = digest;
	}

	public byte[] getSign() {
		return sign;
	}

	public void setSign(byte[] sign) {
		this.sign = sign;
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
	public static byte[] toByteArray(BigInteger bi) {
	    byte[] array = bi.toByteArray();
	    if (array[0] == 0) {
	      byte[] tmp = new byte[array.length - 1];
	      System.arraycopy(array, 1, tmp, 0, tmp.length);
	      array = tmp;
	    }
	    return array;
	}
	
	
}
