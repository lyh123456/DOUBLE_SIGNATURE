package test;
import Modules.Digest;
import sun.misc.BASE64Encoder;




import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

public class spilt_pk {
	
	
	
	
	
	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException, IOException {
		// TODO Auto-generated method stub
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        keyPairGen.initialize(1024);
        //通过对象 KeyPairGenerator 获取对象KeyPair
        KeyPair keyPair = keyPairGen.generateKeyPair();
      
        
        byte sk[] = keyPair.getPrivate().getEncoded();
        byte sk1[]= sk.clone();
        byte sk2[]= sk.clone();
        if(sk[172]==-127)
        {
        	for(int i = 0; i<keyPair.getPrivate().getEncoded().length; i++)
        	{
        	/*
	        	byte d = (byte)(Math.random() * sk[i]);
	        	if(sk[i] <= 0)
	        		d = 0;
	        	System.out.println(sk[i]+" = "+d+" + "+ (sk[i]-d));
	        	sk1[i] = d;
	        	sk2[i] =(byte) (sk[i]-d);*/
	        	if(i>=174&&i<302)
	        		if(sk[i]>1 && sk[i]<127) {
	        			sk1[i]= (byte)(sk[i]-1);
	        			sk2[i]=1;
	        		}
	        		else
		        	{
	        			sk1[i] = sk[i];
	        			sk2[i] = 0;
	        		}
	        	System.out.println(sk[i]+" = "+sk1[i]+" + "+sk2[i]);
	        }
        }
        else if(sk[172]==-128)
        	for(int i = 0; i<keyPair.getPrivate().getEncoded().length; i++)
        	{
        	/*
	        	byte d = (byte)(Math.random() * sk[i]);
	        	if(sk[i] <= 0)
	        		d = 0;
	        	System.out.println(sk[i]+" = "+d+" + "+ (sk[i]-d));
	        	sk1[i] = d;
	        	sk2[i] =(byte) (sk[i]-d);*/
	        	if(i>=173&&i<301)
	        		if(sk[i]>1 && sk[i]<127) {
	        			sk1[i]= (byte)(sk[i]-1);
	        			sk2[i]=1;
	        		}
	        		else
		        	{
	        			sk1[i] = sk[i];
	        			sk2[i] = 0;
	        		}
	        	System.out.println(sk[i]+" = "+sk1[i]+" + "+sk2[i]);
	        }
	       System.out.println(keyPair.getPrivate().getEncoded().length);
        //sign
        
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        Signature signature = Signature.getInstance("MD5withRSA");
        PrivateKey privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(sk));
        signature.initSign(privateKey);
        signature.update(new Digest().generate_digest("file/contract.txt"));
        byte[] sign = signature.sign();
        
        //sign1
        KeyFactory keyFactory1 = KeyFactory.getInstance("RSA");
        Signature signature1 = Signature.getInstance("MD5withRSA");
        PrivateKey privateKey1 = keyFactory1.generatePrivate(new PKCS8EncodedKeySpec(sk1));
        signature1.initSign(privateKey1);
        signature1.update(new Digest().generate_digest("file/contract.txt"));
        byte[] sign1 = signature1.sign();
        //sign2
        KeyFactory keyFactory2 = KeyFactory.getInstance("RSA");
        Signature signature2 = Signature.getInstance("MD5withRSA");
        PrivateKey privateKey2 = keyFactory2.generatePrivate(new PKCS8EncodedKeySpec(sk2));
        signature2.initSign(privateKey2);
        signature2.update(new Digest().generate_digest("file/contract.txt"));
        byte[] sign2 = signature2.sign();
        
        for(int i = 0; i<300;i++)
        	System.out.println(sk2[i]);
        for(int i = 0; i<sign.length;i++)
        	System.out.println(sign[i]+"   "+sign1[i]+"   "+sign2[i]);
	}

}
