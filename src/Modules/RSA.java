package Modules;
/**
 * ���ɹ�Կ��˽Կ
 * 
 */
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class RSA {

    
    public class Keys {
        
    }
    public  final String KEY_ALGORITHM = "RSA";
    private  KeyPairGenerator keyPairGen;
    private  KeyPair keyPair;

    //��ù�Կ
    public  String getPublicKey() throws Exception {
        //���map�еĹ�Կ���� תΪkey����
        Key key = keyPair.getPublic();
        //byte[] publicKey = key.getEncoded();
        //���뷵���ַ���
        return encryptBASE64(key.getEncoded());
    }

    //���˽Կ
    public  String getPrivateKey() throws Exception {
        //���map�е�˽Կ���� תΪkey����
        Key key = keyPair.getPrivate();
        byte[] privateKey = key.getEncoded();
        byte[] t = new byte[3];
        System.out.println(privateKey.length);
        //���뷵���ַ���
        for(int i = 0; i<3;i++)
        	t[i]= privateKey[i];
        return encryptBASE64(privateKey);
    }

    //���뷵��byte
    public  byte[] decryptBASE64(String key) throws Exception {
        return (new BASE64Decoder()).decodeBuffer(key);
    }

    //���뷵���ַ���
    public  String encryptBASE64(byte[] key) throws Exception {
       
    	return (new BASE64Encoder()).encodeBuffer(key);
        
    }
    
    //map�����д�Ź�˽Կ
    public  KeyPair initKey() throws Exception {
        //��ö��� KeyPairGenerator ���� RSA 1024���ֽ�
        keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(1024);
        //ͨ������ KeyPairGenerator ��ȡ����KeyPair
        keyPair = keyPairGen.generateKeyPair();
        return keyPair;
    }

    public static void main(String[] args) {
    	KeyPair keyMap;
        try {
        	RSA rsa = new RSA();
            keyMap = rsa.initKey();
            String publicKey = rsa.getPublicKey();
            System.out.println(publicKey);
            String privateKey = rsa.getPrivateKey();
            System.out.println(privateKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}