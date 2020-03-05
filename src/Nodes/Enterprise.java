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
	
	//��ͬժҪ
	private byte digest[] = null;
	//ǩ��
	private byte sign[] = null;
	
	public Enterprise() {
		//��ʼ����Կ��˽Կ
		RSA rsa = new RSA();
		rsa.CreateKey();
		pk = rsa.getPk();
		sk = rsa.getSk();
		
		try {
			//��ʼ��ժҪ
			Digest d = new Digest();
			digest = d.generate_digest("file/contract.txt");
			//��ʼ��ǩ��
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
	public static BigInteger gcd(BigInteger a, BigInteger b) {// շת�������ʵ��
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
