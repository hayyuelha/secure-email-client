/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package secureemailclient.crypto.sha1;


/**
 *
 * @author HP
 */
public class SHA1 {
    private String message;
    private String MD; //hex string of message digest
    private int blockSize;
    private int messageSize; 
    private byte[] messageInByte;
    private int bufferA, bufferB, bufferC, bufferD, bufferE;
    private int[] buffer = {0x67452301, 0xEFCDAB89, 0x98BADCFE, 0x10325476, 0xC3D2E1F0};
    
    public SHA1 (){
        blockSize = 64; //64 byte
        message = ""; 
        messageSize = 0; 
        MD = ""; //should be "81fe8bfe87576c3ecb22426f8e57847382917acf" for "abcd"
    }
    
    public void setMsg(String s){
        this.message = s;
        this.messageSize = s.length();
        this.messageInByte = s.getBytes();
    }
    
    public String getMsg(){
        return this.message;
    }
    
    public String getMD(){
        return this.MD;
    }
    
    public byte[] addPaddingByte(){
        int rem = (messageSize % blockSize);
        int padLen;
        if(64 - rem >= 9){
            padLen = 64 - rem;
        } else {
            padLen = 128 - rem;
        }
        byte[] padBytes = new byte[padLen];
        padBytes[0] = (byte)0x80;
        long msgLenInBits = messageSize * 8;
        //append message length in the last 8 bytes of padBytes
        for (int i = 0; i < 8; i++) {
            padBytes[padBytes.length - 1 - i] = (byte) ((msgLenInBits >> (8 * i)) & 0x00000000000000FF);
        }
        byte[] output = new byte[messageSize + padLen];
        System.arraycopy(messageInByte, 0, output, 0, messageSize);
        System.arraycopy(padBytes, 0, output, messageSize, padLen);
        return output;
    }
    
    public void mainLoop(int[] w){
        int f = 0;
        int k = 0;
        int temp;
        bufferA = buffer[0];
        bufferB = buffer[1];
        bufferC = buffer[2];
        bufferD = buffer[3];
        bufferE = buffer[4];
        for (int i = 0 ; i < 80 ; i++){
            if ( i >= 0 && i < 20) {
                f = (bufferB & bufferC) | ((~bufferB) & bufferD);
                k = 0x5A827999;
            } else if (i >= 20 && i < 40){
                f = bufferB ^ bufferC ^ bufferD;
                k = 0x6ED9EBA1;
            } else if (i >= 40 && i < 60){
                f = (bufferB & bufferC) | (bufferB & bufferD) | (bufferC & bufferD);
                k = 0x8F1BBCDC;
            } else if (i >= 60 && i < 80) {
                f = bufferB ^ bufferC ^ bufferD;
                k = 0xCA62C1D6;
            }
            temp = rotateLeft(bufferA, 5) + f + bufferE + k + w[i];
            bufferE = bufferD;
            bufferD = bufferC;
            bufferC = rotateLeft(bufferB, 30);
            bufferB = bufferA;
            bufferA = temp;
        }
        //update the buffer
        buffer[0] += bufferA;
        buffer[1] += bufferB;
        buffer[2] += bufferC;
        buffer[3] += bufferD;
        buffer[4] += bufferE;
    }
    
    public void processChunk (byte[] mib){ //mib is a chunk (64 bytes)
        int[] w = new int[80];
        //split a chunk to int[16]
        for (int i = 0; i < 16 ; i++){
            int temp = 0;
            for (int j = 0; j < 4; j++) {
                temp = (mib[i * 4 + j] & 0x000000FF) << (24 - j * 8);
                w[i] = w[i] | temp;
            }
        }
        //initialize the remainder of w
        for (int i = 16; i < 80 ; i++){
            w[i] = rotateLeft((w[i-3] ^ w[i-8] ^ w[i-14] ^ w[i-16]), 1);
        }
        //call mainLoop
        mainLoop(w);
    }
    
    public void buildMD (){
        //add padding byte to original message
        byte[] paddedData = addPaddingByte();
        //check the validity of paddedData
        if (paddedData.length % 64 != 0) {
            System.out.println("Invalid padded data length.");
            System.exit(0);
        }
        int nLoop = paddedData.length / 64; //number of chunks in this paddedData
        byte[] work = new byte[64]; //for one chunk

        for (int i = 0; i < nLoop; i++) {
            System.arraycopy(paddedData, 64 * i, work, 0, 64);
            processChunk(work);
        }
        MD = intArrayToHexStr(buffer);
    } 
    
    final int rotateLeft(int value, int bits) {
        int q = (value << bits) | (value >>> (32 - bits));
        return q;
    }
    
    private String intArrayToHexStr(int[] data) {
        String result = "";
        String tempStr = "";
        int tempInt = 0;
        for (int i = 0; i < data.length; i++) {
            tempInt = data[i];
            tempStr = Integer.toHexString(tempInt);
            //add leading zero to maintain the size of tempStr (always 8 digits hex)
            if (tempStr.length() == 1) {
                tempStr = "0000000" + tempStr;
            } else if (tempStr.length() == 2) {
                tempStr = "000000" + tempStr;
            } else if (tempStr.length() == 3) {
                tempStr = "00000" + tempStr;
            } else if (tempStr.length() == 4) {
                tempStr = "0000" + tempStr;
            } else if (tempStr.length() == 5) {
                tempStr = "000" + tempStr;
            } else if (tempStr.length() == 6) {
                tempStr = "00" + tempStr;
            } else if (tempStr.length() == 7) {
                tempStr = "0" + tempStr;
            }
            result += tempStr;
        }
        return result;
    }
 
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        SHA1 sha = new SHA1();
        sha.setMsg("abcdefghijklmnopqrstuvwxyz");
        sha.buildMD();
        System.out.println(sha.getMD());
    }
    
}
