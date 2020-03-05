package Modules;
/**
 * 生成公钥和私钥
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

    //获得公钥
    public  String getPublicKey() throws Exception {
        //获得map中的公钥对象 转为key对象
        Key key = keyPair.getPublic();
        //byte[] publicKey = key.getEncoded();
        //编码返回字符串
        return encryptBASE64(key.getEncoded());
    }

    //获得私钥
    public  String getPrivateKey() throws Exception {
        //获得map中的私钥对象 转为key对象
        Key key = keyPair.getPrivate();
        byte[] privateKey = key.getEncoded();
        byte[] t = new byte[3];
        System.out.println(privateKey.length);
        //编码返回字符串
        for(int i = 0; i<3;i++)
        	t[i]= privateKey[i];
        return encryptBASE64(privateKey);
    }

    //解码返回byte
    public  byte[] decryptBASE64(String key) throws Exception {
        return (new BASE64Decoder()).decodeBuffer(key);
    }

    //编码返回字符串
    public  String encryptBASE64(byte[] key) throws Exception {
       
    	return (new BASE64Encoder()).encodeBuffer(key);
        
    }
    
    //map对象中存放公私钥
    public  KeyPair initKey() throws Exception {
        //获得对象 KeyPairGenerator 参数 RSA 1024个字节
        keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(1024);
        //通过对象 KeyPairGenerator 获取对象KeyPair
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