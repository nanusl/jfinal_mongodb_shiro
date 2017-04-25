import org.apache.shiro.codec.Base64;
import org.apache.shiro.codec.CodecSupport;
import org.junit.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @version V1.0
 * @Title： //TODO
 * @Package： PACKAGE_NAME
 * @Description： //TODO 用一句话描述该文件做什么
 * @author： nan
 * @date： 2017-04-24 15:22
 */
public class Base64Test {

    @Test
    public  void asd() throws UnsupportedEncodingException {
        System.out.println(CodecSupport.toString(Base64.encode("adasdasda".getBytes("UTF-8"),true)));
        System.out.println(CodecSupport.toString(Base64.encode("adasdasda".getBytes("UTF-8"),false)));
        System.out.println(CodecSupport.toString(Base64.decode("4AvVhmFLUs0KTA3Kprsdag==")));
        System.out.println(Base64.decode("YWRhc2Rhc2Rh").clone());
    }
    @Test
    public  void asds() throws UnsupportedEncodingException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        String s = "99999999";
        byte[] plaintext = s.getBytes("UTF-8");
        String s2 = "asdhjkio";
        byte[] key = s2.getBytes("UTF-8");
        Cipher c = Cipher.getInstance("AES");
        SecretKeySpec k =  new SecretKeySpec(key, "AES");
        c.init(Cipher.ENCRYPT_MODE, k);
        byte[] encryptedData = c.doFinal(plaintext);
        System.out.println(encryptedData);
    }


}
