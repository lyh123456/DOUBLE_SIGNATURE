package Modules;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;


public class Signature_MD5 {
	
	private  byte[] src;
	public Signature_MD5( byte[] source){
		src = source;
	}
	public byte[] getSrc() {
		return src;
	}


	public void setSrc(byte[] src) {
		this.src = src;
	}
	
	public byte[] executeSignature(PrivateKey rsaPrivateKey) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, SignatureException{
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(rsaPrivateKey.getEncoded());
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        Signature signature = Signature.getInstance("MD5withRSA");
        signature.initSign(privateKey);
        signature.update(src);
        byte[] result = signature.sign();
        
        return result;
    }
	public boolean verifySignature(PublicKey rsaPublicKey,byte[] result) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException{
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(rsaPublicKey.getEncoded());
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
        Signature signature = Signature.getInstance("MD5withRSA");
        signature.initVerify(publicKey);
        signature.update(src);
        boolean bool = signature.verify(result);
        
        return bool;
    }
	
	
}
