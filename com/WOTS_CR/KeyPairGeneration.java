package com.WOTS_CR;
import com.Config.*;
public class KeyPairGeneration {
    Binarylog bl = new Binarylog();
    PRG prg = new PRG();
    HashFunction hash = new HashFunction();
    public static int t;
    public static int t1;
    public static int t2;
    public static String X = "";
    public static String Y = "";

    public void calculateLengths(Integer s, Integer w){
        t1 = (int)Math.ceil(new Double(s) / new Double(w));
        t2 = (int)Math.ceil((double)((int)Math.ceil(bl.binlog((double) t1)) + 1 + w)/(double)w);
        t = t1 + t2;
    }


    public void generatePairKey(Integer s, Integer w, Integer choose) {
        calculateLengths(s, w);
        String Xi = "";
        String Yi = "";

        for(int i = 1; i <= t; i++){
            Xi = prg.Random(s);
            X += Xi;
            Yi = calculateYi(Xi, Yi, w, choose, s);
            Y += Yi;
        }
        Y = hash.HashMessageHex(Y, s, choose);
    }

    private String calculateYi(String Xi, String Yi, Integer w, Integer choose, Integer s){
        Yi = Xi;
        for(int i = 1; i <= Math.pow(2, w) - 1; i++){
            Yi = hash.HashMessageBin(Yi, s, choose);
        }
        return Yi;
    }
}
