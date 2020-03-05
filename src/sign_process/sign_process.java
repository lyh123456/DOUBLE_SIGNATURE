package sign_process;

import java.math.BigInteger;
import java.security.SignatureException;


import MODEL.*;
import Nodes.*;

public class sign_process {
	
	public Client client = new Client();
	public Enterprise enterprise = new Enterprise();
	public TTP ttp = new TTP();
	
	
	//所有共享部分(公钥和摘要)
	public PublicKey pkA=null;
	public PublicKey pkB=null;
	public PublicKey pkTTP=null;
	public byte[] digest=null;
	
	//TTP收到的数据
	public PrivateKey sk2_TTP = null;
	public PublicKey pk1_TTP = null;
	byte sign1_TTP[] = null;
	byte vc_TTP[] = null;
	byte signB_TTP[]= null;
	//A收到的数据
	public byte vc_A[] = null;
	byte signB_A[] = null;
	//B收到的数据
	public byte vc_B[] = null;
	public byte sign1_B[] = null;
	public PublicKey pk1_B = null;
	public byte signA_B[] = null;
	//初始化过程
	public boolean init() {
		System.out.println("========= 初始化阶段开始 ==========");
		
		//1、三方交换公钥和合同摘要
		System.out.println("- 1  -   三方交换公钥和合同摘要");
		pkA = client.getPk();
		pkB = enterprise.getPk();
		pkTTP = ttp.getPk();
		digest = client.getDigest();
		
		//2、A将sk2和pk1发送给TTP
		System.out.println("- 2 -  A将sk2和pk1发送给TTP");
		sk2_TTP = client.getSk2();
		pk1_TTP = client.getPk1();
		
		//3、生成Vc
		System.out.println("- 3 -  TTP生成Vc");
		ttp.generate_Vc(pk1_TTP);
		
		//4、发送Vc给A
		System.out.println("- 4 -  TTP发送Vc给A");
		vc_A = ttp.getVc();
		
		//5、A验证vc有效性
		System.out.println("- 5 -  A验证vc有效性");
		if(!new Signature().verifySignature(pkTTP, client.getPk1().get_E(), vc_A))
			return false;   //终止签署过程
		
		System.out.println("========= 初始化阶段结束 ==========");
		return true;
	}
	
	//签署过程
	public boolean sign() {
		System.out.println("========= 签署阶段开始 ==========");
		//1、A发送签名1,vc,pk1给B
		System.out.println("- 1 -  A发送签名1,vc,pk1给B");
		vc_B = ttp.getVc();
		sign1_B = client.getSign1();
		pk1_B = client.getPk1();
		
		//2、验证vc有效性
		System.out.println("- 2 -  B验证vc有效性");
		if(!new Signature().verifySignature(pkTTP, pk1_B.get_E(), vc_B))
			return false;   //终止签署过程
		
		
		//3、B验证签名1的有效性
		System.out.println("- 3 -  B验证签名1的有效性");
		if(!new Signature().verifySignature(pk1_B, digest, sign1_B)) {
			System.out.println("-3-f");
			return false;  //终止签署过程
			
		}
			
		else {
			
			//4、B发送签名给A
			System.out.println("- 4 - B发送签名给A");
			byte signB_A[] = enterprise.getSign();
				
			//5、A验证signB的有效性
			System.out.println("- 5 - A验证signB的有效性");
			if(!new Signature().verifySignature(pkB, digest, signB_A))
					return false; //终止签署过程
			else {
					
				//6、A发送完整签名给B
				System.out.println("- 6 - A发送完整签名给B");
				signA_B = client.getSign();
					
				//7、验证A完整签名的有效性
				System.out.println("- 7 - B验证A完整签名的有效性");
				if(!new Signature().verifySignature(pkA, digest, signA_B)) {
					//执行争端协议
					dispute_settlement();
				}
					
				
				else {
					return true;  //success
				}
						
			}
			System.out.println("========= 签署阶段结束 ==========");
			return true;
		}
		
	
	}
	
	//争端解决过程
	public boolean dispute_settlement() {
		System.out.println("========= 争端解决阶段开始 ==========");
		//1、B发送ttp相关数据
		System.out.println("- 1 - B发送给TTP相关数据");
		sign1_TTP = client.getSign1();
		vc_TTP = ttp.getVc();
		signB_TTP = enterprise.getSign();
		
		//2、ttp验证收到数据的有效性
		System.out.println("- 2 - TTP验证收到数据的有效性");
		if(!new Signature().verifySignature(pkTTP, pk1_TTP.get_E(), vc_TTP)
				||!new Signature().verifySignature(pk1_TTP, digest, sign1_TTP)
					||!new Signature().verifySignature(pkB, digest, signB_TTP))
			return false;  //终止签署
		else {
				
			//3、ttp计算出A第二部分的签名
			System.out.println("- 3 - TTP计算出A第二部分的签名");
			byte sign2[] = new Signature().generateSignature(sk2_TTP, digest);
				
			//4、ttp并合并两签名生成A的完整签名
			System.out.println("- 4 - TTP并合并两签名生成A的完整签名");
			byte signA[] = toByteArray(new BigInteger(1, sign1_TTP).multiply(new BigInteger(1, sign2)).mod(new BigInteger(1, sk2_TTP.get_N())));
				
			//5、ttp发送彼此的签名给A和B
			System.out.println("- 5 - TTP发送彼此的签名给A和B");
			System.out.println("========= 争端解决阶段结束 ==========");
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
