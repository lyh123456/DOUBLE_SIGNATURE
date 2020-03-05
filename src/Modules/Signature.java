package Modules;

import java.math.BigInteger;
import java.security.PrivateKey;

public class Signature {
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
	
	public byte[] generateSignature(byte sk[][], byte digest[]) {
		byte sign[] = null;
		BigInteger m = new BigInteger(1, digest);
	
		BigInteger d = new BigInteger(1, sk[0]);
		
		BigInteger n = new BigInteger(1, sk[1]);
		
		BigInteger s = m.pow(d.intValue()).mod(n);
		return toByteArray(s);
	}
	public boolean verifySignature(byte pk[][], byte digest[],byte sign[]) {
		BigInteger m = new BigInteger(1, digest);
	
		BigInteger e = new BigInteger(1, pk[0]);
		BigInteger n = new BigInteger(1, pk[1]);
		BigInteger s = new BigInteger(1, sign);
		
		System.out.println("RSA签名验证结果:"+m.mod(n) +"  =  " +s.pow(e.intValue()).mod(n));
		if(m.compareTo(s.pow(e.intValue()).mod(n))==0)
			return true;
		else
			return false;
				
	}
}
