package Nodes;

import java.math.BigInteger;


import MODEL.*;
/**
 * 
 * @author hp
 *
 *
 *
 */
public class TTP {
	private PrivateKey sk = null;
	private PublicKey pk = null;
	// VC
	private byte vc[] = null;
	//摘要
	private byte  digest[]= null;
	public TTP(){
		//初始化公钥和私钥
		RSA rsa = new RSA();
		rsa.CreateKey();
		pk = rsa.getPk();
		sk = rsa.getSk();	
	}
	
	//生成vc
	public void generate_Vc(PublicKey pk1) {
		try {
			//初始化签名
			vc = new Signature().generateSignature(sk, pk1.get_E());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
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

	public byte[] getDigest() {
		return digest;
	}

	public void setDigest(byte[] digest) {
		this.digest = digest;
	}

	public byte[] getVc() {
		return vc;
	}

	public void setVc(byte[] vc) {
		this.vc = vc;
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
