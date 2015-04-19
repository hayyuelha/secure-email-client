/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package secureemailclient.crypto.blockcipher;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Toshiba
 */
public class Test {
    public static void printBytes(byte[] a) {
        for (int i = 0; i < a.length; ++i) {
            if (i > 0) {
                System.out.printf(" ");
            }
            System.out.printf("%02x", a[i]);
        }
    }
    
    public static boolean test(byte[] plaintext, byte[] key, int mode, boolean print) {
        BlockCipher bc = new BlockCipher();
        bc.mode = mode;
        
        if (print) {
            System.out.printf("Index      : ");
            for (int i = 0; i < 32; ++i) {
                if (i > 0) {
                    System.out.printf(" ");
                }
                System.out.printf("%2d", i);
            }
            System.out.println();

            System.out.printf("Key        : ");
            printBytes(key);
            System.out.println();

            System.out.printf("Plain Text : ");
            printBytes(plaintext);
            System.out.println();
        }
        
        byte[] ciphertext = bc.encrypt(plaintext, key);
        
        if (print) {
            System.out.printf("Cipher Text: ");
            printBytes(ciphertext);
            System.out.println();
        }
        
        byte[] decrypted = bc.decrypt(ciphertext, key);
        
        if (print) {
            System.out.printf("Decrypted  : ");
            printBytes(decrypted);
            System.out.println();
        
            System.out.println();
        }
        
        // check if it is the same
        if (decrypted.length != plaintext.length) {
            return false;
        }
        for (int i = 0; i < plaintext.length; ++i) {
            if (plaintext[i] != decrypted[i]) {
                return false;
            }
        }
//        for (int i = plaintext.length; i < decrypted.length; ++i) {
//            if (decrypted[i] != 0) {
//                return false;
//            }
//        }
        return true;
    }
    
    private static void frequencyAnalysis(byte[] a) {
        System.out.printf("length = %d\n", a.length);
        int[] occ = new int[256];
        
        for (int i = 0; i < 256; ++i) {
            occ[i] = 0;
        }
        for (int i = 0; i < a.length; ++i) {
            occ[a[i] & 0xFF]++;
        }
        
        int max = occ[0], min = occ[0];
        double std = 0;
        double avg = (double)a.length / 256.;
        for (int i = 0; i < 256; ++i) {
            System.out.printf("%d\n",  occ[i]);
            max = Math.max(max, occ[i]);
            min = Math.min(min, occ[i]);
            std += (avg - occ[i]) * (avg - occ[i]);
        }
        
        std /= 256.;
        std = Math.sqrt(std);
        
        System.out.printf("max = %d, min = %d, std = %g\n", max, min, std);
    }
    
    public static void main(String args[]) {
        
        byte[] plaintext = new byte[32];
        byte[] key = new byte[32];
        
        if (true) {
            plaintext = new byte[1048570];
            Random random = new Random(System.currentTimeMillis());
            
            for (int type = 0; type < 2; ++type) {
                for (int mode = 0; mode < 3; ++mode) {
                    long totalTime = 0;
                    for (int testcase = 0; testcase < 2; ++testcase) {
                        random.nextBytes(key);
                        random.nextBytes(plaintext);
                        BlockCipher bc = new BlockCipher();
                        bc.mode = mode;
                        
                        boolean result = test(plaintext, key, mode, false);
                        
                        System.out.println("Type = " + type + ", Mode = " + mode + ", TC = " + testcase + ", Result = " + result);

//                        long startTime = System.currentTimeMillis();
//                        bc.encrypt(plaintext, key);
//                        long finishTime = System.currentTimeMillis();

        //                System.out.printf("Test %2d, Mode %d: %4d ms\n", testcase, mode, finishTime - startTime);
//                        totalTime += finishTime - startTime;
                    }
//                    System.out.printf("Type = %s, Mode = %d, Total time = %d ms\n", type == 0 ? "Encryption" : "Decryption", mode, totalTime);
                }
            }
            return;
        }
        
        if (false) {
            String filename;
            System.out.printf("Masukkan nama berkas: ");
            Scanner in = new Scanner(System.in);
            filename = in.nextLine();
            
            try {
                plaintext = Files.readAllBytes(Paths.get(filename));
            } catch (Exception e) {

            }
            
            System.out.println("Plaintext:");
            frequencyAnalysis(plaintext);
            System.out.println();
            
            (new Random(System.currentTimeMillis())).nextBytes(key);
            byte[] ciphertext = (new BlockCipher()).encrypt(plaintext, key);
            
            System.out.println("Ciphertext:");
            frequencyAnalysis(ciphertext);
            System.out.println();
            
            return;
        }
        
        String filename;
        System.out.printf("Masukkan nama berkas: ");
        Scanner in = new Scanner(System.in);
        filename = in.nextLine();
        
        
        String keyString;
        System.out.printf("Masukkan kunci: ");
        keyString = in.nextLine();
        for (int i = 0; i < 32; ++i) {
            if (i < keyString.length()) {
                key[i] = (byte)keyString.charAt(i);
            } else {
                key[i] = 0;
            }
        }
        
        BlockCipher bc = new BlockCipher();
        byte[] ciphertext;
        
        boolean encrypt = false;
        if (encrypt) {
            try {
                plaintext = Files.readAllBytes(Paths.get(filename));
            } catch (Exception e) {

            }

            bc.mode = BlockCipher.MODE_ECB;
            ciphertext = bc.encrypt(plaintext, key);
            try {
                Files.write(Paths.get(filename + ".ecb"), ciphertext);
            } catch (Exception e) {

            }

            bc.mode = BlockCipher.MODE_CBC;
            ciphertext = bc.encrypt(plaintext, key);
            try {
                Files.write(Paths.get(filename + ".cbc"), ciphertext);
            } catch (Exception e) {

            }

            bc.mode = BlockCipher.MODE_CFB;
            ciphertext = bc.encrypt(plaintext, key);
            try {
                Files.write(Paths.get(filename + ".cfb"), ciphertext);
            } catch (Exception e) {

            }
        } else {
            try {
                plaintext = Files.readAllBytes(Paths.get(filename + ".ecb"));
            } catch (Exception e) {

            }

            bc.mode = BlockCipher.MODE_ECB;
            ciphertext = bc.decrypt(plaintext, key);
            try {
                Files.write(Paths.get("ecb." + filename), ciphertext);
            } catch (Exception e) {

            }
            
            try {
                plaintext = Files.readAllBytes(Paths.get(filename + ".cbc"));
            } catch (Exception e) {

            }

            bc.mode = BlockCipher.MODE_CBC;
            ciphertext = bc.decrypt(plaintext, key);
            try {
                Files.write(Paths.get("cbc." + filename), ciphertext);
            } catch (Exception e) {

            }
            
            try {
                plaintext = Files.readAllBytes(Paths.get(filename + ".cfb"));
            } catch (Exception e) {

            }

            bc.mode = BlockCipher.MODE_CFB;
            ciphertext = bc.decrypt(plaintext, key);
            try {
                Files.write(Paths.get("cfb." + filename), ciphertext);
            } catch (Exception e) {

            }

        }
        
//        random.nextBytes(key);
//        random.nextBytes(plaintext);
        
//        boolean result = test(plaintext, key, BlockCipher.MODE_ECB, true);
        
//        for (int mode = 0; mode < 3; ++mode) {
//            for (int testcase = 0; testcase < 10; ++testcase) {
//                random.nextBytes(key);
//                random.nextBytes(plaintext);
//
//                long startTime = System.currentTimeMillis();
//                boolean result = test(plaintext, key, mode, false);
//                long finishTime = System.currentTimeMillis();
//
//                System.out.printf("Test %2d, Mode %d (%4d ms): %s\n", testcase, mode, finishTime - startTime, result ? "SUCCESS" : "FAILURE");
//            }
//        }
    }

}
