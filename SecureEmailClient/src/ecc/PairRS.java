/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ecc;

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
}
