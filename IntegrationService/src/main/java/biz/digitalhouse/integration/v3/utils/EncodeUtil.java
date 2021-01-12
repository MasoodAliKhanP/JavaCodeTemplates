package biz.digitalhouse.integration.v3.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author vitaliy.babenko
 *         created: 31.01.14 10:46
 */
public class EncodeUtil {

    private static Log log = LogFactory.getLog(EncodeUtil.class);

    public static byte[] encode(String text, String algorithm) {

        MessageDigest md;
        if (text == null) {
            return new byte[]{};
        }
        try {
            md = MessageDigest.getInstance(algorithm);
            md.update(text.getBytes("UTF-8"), 0, text.length());
        } catch (NoSuchAlgorithmException e) {
            log.error("There is no "+algorithm+" algorithm avaliable.", e);
            return new byte[]{};
        } catch (UnsupportedEncodingException e) {
            log.error("Could not encoding with UTF-8 "+text+". ", e);
            return new byte[]{};
        }
        return md.digest();
    }

    public static String md5(String data) {
        byte[] in = encode(data, "MD5");
        return DatatypeConverter.printHexBinary(in).toLowerCase();
    }
}
