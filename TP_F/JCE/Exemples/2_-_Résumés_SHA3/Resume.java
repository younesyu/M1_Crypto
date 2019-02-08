// -*- coding: utf-8 -*-

import java.io.*;
import java.security.*;

import org.bouncycastle.jcajce.provider.digest.*;

public class Resume
{
    public static void main(String[] args)
    {
        try {
            File fichier = new File("butokuden.jpg");
            FileInputStream fis = new FileInputStream(fichier);

            SHA3.DigestSHA3 hacheur = new SHA3.DigestSHA3(256);  

            byte[] buffer = new byte[1024];
            int nbOctetsLus;
            while ( ( nbOctetsLus = fis.read(buffer) ) != -1) {
                hacheur.update(buffer, 0, nbOctetsLus); 
            }
            byte[] resume = hacheur.digest();
            
            System.out.print("Le résumé SHA3-256 du fichier \"butokuden.jpg\" vaut: 0x");
            System.out.println(toHex(resume));
            fis.close();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static String toHex(byte[] resume) {
        StringBuffer sb = new StringBuffer();        
        for(byte k: resume) {
            sb.append(String.format("%02X", k));
        }        
        return sb.toString();
    }
}

/* 
   $ javac -cp ./:./bcprov-jdk15on-153.jar Resume.java
   $ java -cp ./:./bcprov-jdk15on-153.jar Resume
   Le résumé SHA3-256 du fichier "butokuden.jpg" vaut: 0x973BC78FEE694C0FF00BF10A0...6B7
*/

