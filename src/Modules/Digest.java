package Modules;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.DigestInputStream;
/*
 * 生成摘要
 */
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Digest {
	private byte digest[];
	
	//生成摘要
	public byte[] generate_digest(String filename) throws NoSuchAlgorithmException, IOException {
		MessageDigest m = MessageDigest.getInstance("MD5");
		FileInputStream fin = new FileInputStream(filename);
		//使用MD5算法生成指定消息的摘要
		DigestInputStream din = new DigestInputStream(fin, m);
		while(din.read()!=-1);
		//计算摘要
		digest = m.digest();
		return digest;
	}
	
}
