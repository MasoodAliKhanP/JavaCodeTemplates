package com.octopusgaming.backoffice.utils;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * <p>Class: EncodeUtil</p>
 * <p>Description: </p>
 *
 * @author Anna Dotsenko
 */
public class EncodeUtil {

    private static Log log = LogFactory.getLog(EncodeUtil.class);

    public static String toHexString(byte[] in) {
        String hexStr = (new BigInteger(1, in)).toString(16);
        return hexStr.length() % 2 != 0 ? "0"+hexStr : hexStr;
    }

    public static byte[] fromHexString(String in) {
        byte[] bytes = (new BigInteger(in, 16)).toByteArray();
        int i = bytes.length % 16;

        boolean drop = (bytes.length / 16) >= i * (bytes.length % 16);
        if (drop) {
            bytes = bytes.length > 0 ? Arrays.copyOfRange(bytes, i, bytes.length) : bytes;
        } else {
            if (i > 0) {
                i = 16 - i;
                byte[] temp = new byte[bytes.length+i];
                // add to first symbols '0'
                for (int k=0;k<temp.length;k++) {
                    if (k<i) {
                        temp[k] = 0;
                    } else {
                        temp[k] = bytes[k-i];
                    }
                }
                bytes = temp;
            }
        }
        return bytes;
    }

    public static String encode(String text, String algorithm) {

	    MessageDigest md;
        if (text == null) {
            return "";
        }
        try {
            md = MessageDigest.getInstance(algorithm);
	        md.update(text.getBytes("UTF-8"), 0, text.length());
        } catch (NoSuchAlgorithmException e) {
            log.error("There is no "+algorithm+" algorithm avaliable.", e);
            return "";
        } catch (UnsupportedEncodingException e) {
            log.error("Could not encoding with UTF-8 "+text+". ", e);
            return "";
        }
        return toHexString(md.digest());
    }

    public static boolean isSameHash(String data, String hash, String algorithm) {

        String newHash = encode(data, algorithm);

        if (newHash.equals(hash)) {
            return true;
        } else {
            newHash = newHash.toUpperCase();
            return newHash.equals(hash);
        }
    }

//    public static String base64(String data) {
//        try {
//            byte[] bytes = data.getBytes(DefaultValues.DEFAULT_CHARSET);
//            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
//            InputStream b64is = MimeUtility.decode(bais, "base64");
//            byte[] tmp = new byte[bytes.length];
//            int readBytes = b64is.read(tmp);
//            byte[] decodedResult = new byte[readBytes];
//            System.arraycopy(tmp, 0, decodedResult, 0, readBytes);
//            return new String(decodedResult, 0, readBytes, "UTF-8");
//        } catch (Exception e) {
//            if (log.isErrorEnabled()) {
//                log.error(e.getMessage(), e);
//            }
//        }
//        return "";
//    }

    public static String md5(String data) {
        return encode(data, "MD5");
    }

    public static String sha1(String data) {
        return encode(data, "SHA1");
    }

    public static String sha256(String data) {
        return encode(data, "SHA-256");
    }

    public static String sha512(String data) {
        return encode(data, "SHA-512");
    }

    public static boolean isMd5Valid(String data, String hash) {
        return isSameHash(data, hash, "MD5");
    }

    public static boolean isSha1Valid(String data, String hash) {
        return isSameHash(data, hash, "SHA1");
    }

    public static boolean isSha256Valid(String data, String hash) {
        return isSameHash(data, hash, "SHA-256");
    }

    public static boolean isSha512Valid(String data, String hash) {
        return isSameHash(data, hash, "SHA-512");
    }
}
