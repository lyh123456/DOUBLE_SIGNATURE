package Modules;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.DigestInputStream;
/*
 * ����ժҪ
 */
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Digest {
	private byte digest[];
	
	//����ժҪ
	public byte[] generate_digest(String filename) throws NoSuchAlgorithmException, IOException {
		MessageDigest m = MessageDigest.getInstance("MD5");
		FileInputStream fin = new FileInputStream(filename);
		//ʹ��MD5�㷨����ָ����Ϣ��ժҪ
		DigestInputStream din = new DigestInputStream(fin, m);
		while(din.read()!=-1);
		//����ժҪ
		digest = m.digest();
		return digest;
	}
	
}
