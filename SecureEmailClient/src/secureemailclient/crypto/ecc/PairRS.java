/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package secureemailclient.crypto.ecc;

import java.math.BigInteger;

/**
 *
 * @author Riady
 */
public class PairRS {
    
    public BigInteger r,s;
    
    public PairRS(){
        r = BigInteger.ZERO;
        s = BigInteger.ZERO;
    }
    
    public PairRS(BigInteger r,BigInteger s){
        this.r = r;
        this.s = s;
    }
    
    public byte[] toArrayBytes(){
        byte[] ret= new byte[r.toByteArray().length+s.toByteArray().length];
        
        byte[] rArr = r.toByteArray();
        
        byte[] sArr = s.toByteArray();
        
        
        System.arraycopy(rArr, 0, ret, 0, rArr.length);
        
        System.arraycopy(sArr, 0, ret, rArr.length, sArr.length);
        
        return ret;
    }
    
    public byte[] RtoArrayBytes(){
        return r.toByteArray();
    }
    
    public byte[] StoArrayBytes(){
        return s.toByteArray();
    }
    
    public String getRString(){
        return r.toString(16);
    }
    
    public String getSString(){
        return s.toString(16);
    }
    
    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
}
