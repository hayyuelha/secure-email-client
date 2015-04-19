package secureemailclient.crypto;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Ahmad Zaky
 */
public class CryptoHelper {
    /**
     * Pad the array of byte b so its length will be multiple of blockSize.
     * 
     * There will be at least one byte padded. The last byte will contain the
     * number of padded bytes.
     * 
     * @param b
     * @return 
     */
    public static byte[] pad(byte[] b, int blockSize) {
        int paddedLength = blockSize - (b.length % blockSize);
        byte[] padded = new byte[b.length + paddedLength];
        for (int i = 0; i < b.length; ++i) {
            padded[i] = b[i];
        }
        for (int i = 0; i < paddedLength - 1; ++i) {
            padded[b.length + i] = 0;
        }
        padded[padded.length - 1] = (byte)paddedLength;
        
        return padded;
    }
    
    /**
     * Recover the original array of byte given the padded array of byte b.
     * 
     * @param b
     * @param blockSize
     * @return 
     */
    public static byte[] unpad(byte[] b, int blockSize) {
        int paddedLength = b[b.length - 1];
        byte[] unpadded = new byte[b.length - paddedLength];
        for (int i = 0; i < unpadded.length; ++i) {
            unpadded[i] = b[i];
        }
        return unpadded;
    }
    
    public static boolean isBase64Encoded(String s) {
        Pattern p = Pattern.compile("^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{4}|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)$");
        Matcher m = p.matcher(s);
        return m.matches();
    }
        
}
