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

	// �����ݴ����㼰�����ѡȡ��Ҫ�õ�int��ʽ�����ȶ���һЩ��Ӧ��int����

	public RSA(){
		p = new BigInteger("53");
		q = new BigInteger("23");
		// ��p,q��ʱת��Ϊint��ʽ������p*q�ϴ�������Ĳ��������⣩
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

	public static boolean isPrime(int a) {// �ж��Ƿ�������
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



	public void CreateKey() {// ���ɹ�Կ˽Կ����
		BigInteger  e, d, n, phi_n, plain, cipher;
		int e_temp, d_temp, p_temp, q_temp;
		p_temp = Integer.valueOf(p.toString());
		q_temp = Integer.valueOf(q.toString());
		System.out.println("-----����Ϊ��Կ���ɹ���-----");
		BigInteger k;
		BigInteger one;
		one = BigInteger.ONE;
		BigInteger zero;
		zero = BigInteger.ZERO;
		n = p.multiply(q);// �����n��phi_n(ŷ������)
		phi_n = p.subtract(one).multiply(q.subtract(one));
		
		for (;;) {
			e_temp = (int) (1 + Math.random() * p_temp * q_temp);
			e = BigInteger.valueOf(e_temp);// �������e
			if (gcd(e, phi_n).equals(one)) {// �ж�e�Ƿ���phi_n����
				System.out.println("��Կ��e=" + e + "��n=" + n);
				pk = new PublicKey(toByteArray(e),toByteArray(n),toByteArray(phi_n));
				/*PublicKey[0] = toByteArray(e);
				PublicKey[1] = toByteArray(n);*/
				break;
			}
		}
		for (k = BigInteger.ONE;; k = k.add(one)) {// ���˽Կd
			if ((((k.multiply(phi_n)).add(one)).mod(e)).equals(zero)) {
				d = ((k.multiply(phi_n)).add(one)).divide(e);
				if (d.equals(e)) {
					continue;
				} else {
					sk = new PrivateKey(toByteArray(d),toByteArray(n),toByteArray(phi_n));
					/*PrivateKey[0] = toByteArray(d);
					PrivateKey[1] = toByteArray(n);*/
					System.out.println("˽Կ��d=" + d + "��n=" + n);
					break;
				}
			}
		}
		System.out.println("-----������Կ�����-----");

	}
	//����˽Կ���㹫Կ
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
					System.out.println("˽Կ��" + d + "  ����Ĺ�Կ��" + e);
					break;
				}
			}
		}
		
		return r;
		
	}

}