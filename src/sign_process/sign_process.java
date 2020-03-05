package sign_process;

import java.math.BigInteger;
import java.security.SignatureException;


import MODEL.*;
import Nodes.*;

public class sign_process {
	
	public Client client = new Client();
	public Enterprise enterprise = new Enterprise();
	public TTP ttp = new TTP();
	
	
	//���й�����(��Կ��ժҪ)
	public PublicKey pkA=null;
	public PublicKey pkB=null;
	public PublicKey pkTTP=null;
	public byte[] digest=null;
	
	//TTP�յ�������
	public PrivateKey sk2_TTP = null;
	public PublicKey pk1_TTP = null;
	byte sign1_TTP[] = null;
	byte vc_TTP[] = null;
	byte signB_TTP[]= null;
	//A�յ�������
	public byte vc_A[] = null;
	byte signB_A[] = null;
	//B�յ�������
	public byte vc_B[] = null;
	public byte sign1_B[] = null;
	public PublicKey pk1_B = null;
	public byte signA_B[] = null;
	//��ʼ������
	public boolean init() {
		System.out.println("========= ��ʼ���׶ο�ʼ ==========");
		
		//1������������Կ�ͺ�ͬժҪ
		System.out.println("- 1  -   ����������Կ�ͺ�ͬժҪ");
		pkA = client.getPk();
		pkB = enterprise.getPk();
		pkTTP = ttp.getPk();
		digest = client.getDigest();
		
		//2��A��sk2��pk1���͸�TTP
		System.out.println("- 2 -  A��sk2��pk1���͸�TTP");
		sk2_TTP = client.getSk2();
		pk1_TTP = client.getPk1();
		
		//3������Vc
		System.out.println("- 3 -  TTP����Vc");
		ttp.generate_Vc(pk1_TTP);
		
		//4������Vc��A
		System.out.println("- 4 -  TTP����Vc��A");
		vc_A = ttp.getVc();
		
		//5��A��֤vc��Ч��
		System.out.println("- 5 -  A��֤vc��Ч��");
		if(!new Signature().verifySignature(pkTTP, client.getPk1().get_E(), vc_A))
			return false;   //��ֹǩ�����
		
		System.out.println("========= ��ʼ���׶ν��� ==========");
		return true;
	}
	
	//ǩ�����
	public boolean sign() {
		System.out.println("========= ǩ��׶ο�ʼ ==========");
		//1��A����ǩ��1,vc,pk1��B
		System.out.println("- 1 -  A����ǩ��1,vc,pk1��B");
		vc_B = ttp.getVc();
		sign1_B = client.getSign1();
		pk1_B = client.getPk1();
		
		//2����֤vc��Ч��
		System.out.println("- 2 -  B��֤vc��Ч��");
		if(!new Signature().verifySignature(pkTTP, pk1_B.get_E(), vc_B))
			return false;   //��ֹǩ�����
		
		
		//3��B��֤ǩ��1����Ч��
		System.out.println("- 3 -  B��֤ǩ��1����Ч��");
		if(!new Signature().verifySignature(pk1_B, digest, sign1_B)) {
			System.out.println("-3-f");
			return false;  //��ֹǩ�����
			
		}
			
		else {
			
			//4��B����ǩ����A
			System.out.println("- 4 - B����ǩ����A");
			byte signB_A[] = enterprise.getSign();
				
			//5��A��֤signB����Ч��
			System.out.println("- 5 - A��֤signB����Ч��");
			if(!new Signature().verifySignature(pkB, digest, signB_A))
					return false; //��ֹǩ�����
			else {
					
				//6��A��������ǩ����B
				System.out.println("- 6 - A��������ǩ����B");
				signA_B = client.getSign();
					
				//7����֤A����ǩ������Ч��
				System.out.println("- 7 - B��֤A����ǩ������Ч��");
				if(!new Signature().verifySignature(pkA, digest, signA_B)) {
					//ִ������Э��
					dispute_settlement();
				}
					
				
				else {
					return true;  //success
				}
						
			}
			System.out.println("========= ǩ��׶ν��� ==========");
			return true;
		}
		
	
	}
	
	//���˽������
	public boolean dispute_settlement() {
		System.out.println("========= ���˽���׶ο�ʼ ==========");
		//1��B����ttp�������
		System.out.println("- 1 - B���͸�TTP�������");
		sign1_TTP = client.getSign1();
		vc_TTP = ttp.getVc();
		signB_TTP = enterprise.getSign();
		
		//2��ttp��֤�յ����ݵ���Ч��
		System.out.println("- 2 - TTP��֤�յ����ݵ���Ч��");
		if(!new Signature().verifySignature(pkTTP, pk1_TTP.get_E(), vc_TTP)
				||!new Signature().verifySignature(pk1_TTP, digest, sign1_TTP)
					||!new Signature().verifySignature(pkB, digest, signB_TTP))
			return false;  //��ֹǩ��
		else {
				
			//3��ttp�����A�ڶ����ֵ�ǩ��
			System.out.println("- 3 - TTP�����A�ڶ����ֵ�ǩ��");
			byte sign2[] = new Signature().generateSignature(sk2_TTP, digest);
				
			//4��ttp���ϲ���ǩ������A������ǩ��
			System.out.println("- 4 - TTP���ϲ���ǩ������A������ǩ��");
			byte signA[] = toByteArray(new BigInteger(1, sign1_TTP).multiply(new BigInteger(1, sign2)).mod(new BigInteger(1, sk2_TTP.get_N())));
				
			//5��ttp���ͱ˴˵�ǩ����A��B
			System.out.println("- 5 - TTP���ͱ˴˵�ǩ����A��B");
			System.out.println("========= ���˽���׶ν��� ==========");
			return true;
		}
		
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
