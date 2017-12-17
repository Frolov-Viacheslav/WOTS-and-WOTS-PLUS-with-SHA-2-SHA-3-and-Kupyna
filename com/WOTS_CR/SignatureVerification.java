package com.WOTS_CR;
import com.Config.*;
public class SignatureVerification {
    HashFunction hash = new HashFunction();
    SignatureGeneration sigGen = new SignatureGeneration();
    public String sig = "";

    public boolean verifySignature(String SIGNATURE, String Message, Integer s, Integer w, Integer choose) {
        Integer[]b = sigGen.messagePlusCheckSum(Message, s, w);
        //System.out.println("Full Array = " + Arrays.toString(sigGen.messagePlusCheckSum(Message, s, w)));
        String SIGNATUREi = "";
        for (int i = 0; i < KeyPairGeneration.t; i++) {
            SIGNATUREi = SIGNATURE.substring(i * s, i * s + s); // нахождение подстроки с длиной в s символ
            sig += calculateSignatureI(SIGNATUREi,b[i],w, choose, s);
        }
        sig = hash.HashMessageHex(sig, s, choose);
        if(KeyPairGeneration.Y.compareTo(sig) == 0)
            return true;
        else
            return false;
    }
    private String calculateSignatureI(String sigi, Integer bi, Integer w, Integer choose, Integer s) {
        for (int i = 1; i <= Math.pow(2, w) - 1 - bi; i++) {
            sigi = hash.HashMessageBin(sigi, s, choose);
        }
        return sigi;
    }


}
