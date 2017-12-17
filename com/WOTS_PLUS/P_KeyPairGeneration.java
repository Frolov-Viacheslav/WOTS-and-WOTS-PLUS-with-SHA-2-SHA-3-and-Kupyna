package com.WOTS_PLUS;
import com.Config.*;
public class P_KeyPairGeneration {
    Binarylog bl = new Binarylog();
    PRG prg = new PRG();
    HashFunction hash = new HashFunction();
    public static int l;
    public static int l1;
    public static int l2;
    public static String X = "";
    public static String Y = "";
    public static String r = "";

    private void calculateLengths(Integer s, Integer w){
        l1 = (int)Math.ceil(new Double(s) / (int)Math.ceil(bl.binlog((double) w)));
        l2 = (int)Math.ceil((new Double((int)Math.ceil(bl.binlog((double) (l1 * (w - 1))))) / new Double((int)Math.ceil(bl.binlog((double) w)))) + 1);
        l = l1 + l2;
    }

    public void calculateSK(Integer s, Integer w) {
        calculateLengths(s, w);
        String Xi = "";
        String ri = "";
        for (int i = 1; i <= l + w - 1; i++) {
            if (i <= l) {
                Xi = prg.Random(s);
                X += Xi;
            } else {
                ri = prg.Random(s);
                r += ri;
            }
        }
    }

    public void calculatePK(Integer s, Integer w, Integer choose) {
        String Xi = "";
        for (int i = 0; i < l; i++) {
            Xi = X.substring(i * s, i * s + s); // нахождение подстроки с длиной в s символ
            Y += calculateYi(Xi, s, w, choose);
        }
        Y = hash.HashMessageHex(Y, s, choose);
    }

    private String calculateYi(String Xi, Integer s, Integer w, Integer choose) {
        String Yi = "";
        String ri = "";
        Yi = Xi;
        for (int i = 0; i < w - 1; i++) {
            ri = r.substring(i * s, i * s + s); // нахождение подстроки с длиной в s символ
            Yi = hash.HashMessageBin(xor(Yi, ri, s), s, choose);
        }
        return Yi;
    }

    private String xor(String Yi, String ri, Integer s) {
        String newYi = "";
        for (int i = 0; i < s; i++) {
            newYi += Yi.charAt(i) ^ ri.charAt(i);
        }
        return newYi;
    }
}
