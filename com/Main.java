package com;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import com.Config.*;
import com.WOTS_CR.*;
import com.WOTS_PLUS.*;

public class Main {//mvn exec:java -Dexec.mainClass="com.Main"
    public static void main(String[] args) throws NoSuchAlgorithmException {
        HashFunction hash = new HashFunction();
        String Message = "";
        Scanner inMessage = new Scanner(System.in);
        System.out.printf("Input your message:\n");
        Message = inMessage.nextLine();

        Scanner inW = new Scanner(System.in);
        System.out.printf("Input parametr w:\n");
        int w = Integer.parseInt(inW.nextLine());

        Scanner inChoose = new Scanner(System.in);
        System.out.printf("Choose algoritm (1 - WOTS, 2 - WOTS+):\n");
        int chooseAlgoritm = Integer.parseInt(inChoose.nextLine());


       // int s = 0;
       // Message = md5b.md5Custom(MessageOrigin);
        //s = Message.length();
        Scanner inS = new Scanner(System.in);
        System.out.printf("Choose length of hash function (1 - 256, 2 - 512):\n"); //128
        int chS = Integer.parseInt(inS.nextLine());
        int S = 0;
        if(chS == 1)
            S = 256;
        if(chS == 2)
            S = 512;
        Scanner inchoose = new Scanner(System.in);
        System.out.printf("Choose hash function (1 - SHA-2, 2 - SHA-3, 3 - Kupyna):\n"); //16
        int choose = Integer.parseInt(inchoose.nextLine());
        Binarylog bl = new Binarylog();

        Message = hash.HashMessageBin(Message, S, choose);
        if(chooseAlgoritm > 2){
            System.out.println("Wrong input!");
        }
        if(chooseAlgoritm == 1) {
            KeyPairGeneration kpg = new KeyPairGeneration();
            SignatureGeneration sg = new SignatureGeneration();
            SignatureVerification sv = new SignatureVerification();
            long start = System.currentTimeMillis(); // STAR TIMER
            kpg.generatePairKey(S, w, choose);
            System.out.println("X - Private key; Y - Public key; Y` - Validation string.\n");
            System.out.println("X : " + kpg.X + "\nY : " + kpg.Y);
            sg.generateSignature(Message, S, w, choose);
            System.out.println("Signature: " + sg.SIGNATURE);

            boolean equalSignature = false;
            if (sv.verifySignature(sg.SIGNATURE, Message, S, w, choose)) {
                equalSignature = true;
            }
            long finish = System.currentTimeMillis(); // FINISH TIMER
            System.out.println("Y`: " + sv.sig);
            long timeConsumedMillis = finish - start;
            //Statistic
            System.out.println("\n" + "Statictic:");
            System.out.println("Length of Private key: " + KeyPairGeneration.X.length() + " bits");
            System.out.println("Length of Signature: " + sg.SIGNATURE.length() + " bits");
            System.out.println("Time: " + timeConsumedMillis + "ms");
            //Statistic

            if (equalSignature == true) {
                System.out.println("\nThe signature is valid!");
            } else {
                System.out.println("The signature is NOT valid!");
            }
        }
        if(chooseAlgoritm == 2) {
            P_KeyPairGeneration p_kpg = new P_KeyPairGeneration();
            P_SignatureGeneration p_sg = new P_SignatureGeneration();
            P_SignatureVerification p_sv = new P_SignatureVerification();
            long start = System.currentTimeMillis(); // STAR TIMER
            p_kpg.calculateSK(S,w);
            System.out.println("r: " + P_KeyPairGeneration.r.length());
            p_kpg.calculatePK(S,w, choose);
            System.out.println("X: " + P_KeyPairGeneration.X);
            System.out.println("Y: " + P_KeyPairGeneration.Y);
            p_sg.generateSignature(Message, S, w, choose);
            boolean equalSignature = false;
            if (p_sv.verifySignature(p_sg.SIGNATURE, Message, S, w, choose)) {
                equalSignature = true;
            }
            long finish = System.currentTimeMillis(); // FINISH TIMER
            long timeConsumedMillis = finish - start;
            System.out.println("SIGNATURE: " + p_sg.SIGNATURE);
            System.out.println("sig: " + p_sv.sig);
            //Statistic
            System.out.println("\n" + "Statictic:");
            System.out.println("Length of Private key: " + (P_KeyPairGeneration.X.length() + P_KeyPairGeneration.r.length()) + " bits");
            System.out.println("Length of Signature: " + p_sg.SIGNATURE.length() + " bits");
            System.out.println("Time: " + timeConsumedMillis + "ms");
            //Statistic
            if (equalSignature == true) {
                System.out.println("\nThe signature is valid!");
            } else {
                System.out.println("The signature is NOT valid!");
            }
        }
        Scanner inExit = new Scanner(System.in);
        System.out.printf("Exit or not? (0 - Exit, 1 - Start):\n");
        int exitOrNot = Integer.parseInt(inExit.nextLine());
        if(exitOrNot == 1){
            Reboot();
            main(args);
        }
    }

    public static void Reboot(){ // Reboot global parametrs
        SignatureGeneration sg = new SignatureGeneration();
        SignatureVerification sv = new SignatureVerification();
        KeyPairGeneration.t = 0;
        KeyPairGeneration.t1 = 0;
        KeyPairGeneration.t2 = 0;
        KeyPairGeneration.X = "";
        KeyPairGeneration.Y = "";
        sg.CBinary = "";
        sg.SIGNATURE = "";
        sv.sig = "";
        P_SignatureGeneration p_sg = new P_SignatureGeneration();
        P_SignatureVerification p_sv = new P_SignatureVerification();
        P_KeyPairGeneration.l = 0;
        P_KeyPairGeneration.l1 = 0;
        P_KeyPairGeneration.l2 = 0;
        P_KeyPairGeneration.X = "";
        P_KeyPairGeneration.Y = "";
        P_KeyPairGeneration.r = "";
        p_sg.CBinary = "";
        p_sg.SIGNATURE = "";
        p_sv.sig = "";
    }
}
