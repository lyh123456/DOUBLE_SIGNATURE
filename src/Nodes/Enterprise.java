package Nodes;

import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;


import MODEL.*;
/**
 * 
 * @author hp
 *
 *
 *
 */
public class Enterprise {
	private PrivateKey sk = null;
	private PublicKey pk = null;
	
	//合同摘要
	private byte digest[] = null;
	//签名
	private byte sign[] = null;
	
	public Enterprise() {
		//初始化公钥和私钥
		RSA rsa = new RSA();
		rsa.CreateKey();
		pk = rsa.getPk();
		sk = rsa.getSk();
		
		try {
			//初始化摘要
			Digest d = new Digest();
			digest = d.generate_digest("file/contract.txt");
			//初始化签名
			sign = new Signature().generateSignature(sk, digest);
			
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
