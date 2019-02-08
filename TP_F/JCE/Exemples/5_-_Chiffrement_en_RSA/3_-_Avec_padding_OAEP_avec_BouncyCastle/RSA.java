// -*- coding: utf-8 -*-

import java.*;
import java.math.BigInteger;
import java.util.Formatter;
import java.security.*;
import javax.crypto.*;
import java.security.spec.*;
import java.security.interfaces.*;

public class RSA {
    public static void main(String[] args) throws Exception {
        BigInteger n = new BigInteger("00af7958cb96d7af4c2e6448089362"+
                                      "31cc56e011f340c730b582a7704e55"+
                                      "9e3d797c2b697c4eec07ca5a903983"+
                                      "4c0566064d11121f1586829ef6900d"+
                                      "003ef414487ec492af7a12c34332e5"+
                                      "20fa7a0d79bf4566266bcf77c2e007"+
                                      "2a491dbafa7f93175aa9edbf3a7442"+
                                      "f83a75d78da5422baa4921e2e0df1c"+
                                      "50d6ab2ae44140af2b", 16);
        System.out.println("N: " + n);
        BigInteger e = BigInteger.valueOf(0x10001);
        System.out.println("E: " + e);
        BigInteger d = new BigInteger("35c854adf9eadbc0d6cb47c4d11f9c"+
                                      "b1cbc2dbdd99f2337cbeb2015b1124"+
                                      "f224a5294d289babfe6b483cc253fa"+
                                      "de00ba57aeaec6363bc7175fed20fe"+
                                      "fd4ca4565e0f185ca684bb72c12746"+
                                      "96079cded2e006d577cad2458a5015"+
                                      "0c18a32f343051e8023b8cedd49598"+
                                      "73abef69574dc9049a18821e606b0d"+
                                      "0d611894eb434a59", 16);
        System.out.println("D: " + d);

        System.out.println("Texte clair: \"Alfred\"");
        byte[] message = "Alfred".getBytes();
        System.out.println("Message clair (en hexadécimal): \"" + toHex(message) + "\"");

        //------------------------------------------------------------------
        //  Etape 1.   Récupérer un objet qui chiffre et déchiffre en RSA
        //             avec bourrage (mais sans mode opératoire : None)
        //------------------------------------------------------------------
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        Cipher chiffreur = Cipher.getInstance("RSA/None/OAEPWithSHA1AndMGF1Padding", "BC");
        //------------------------------------------------------------------
        //  Etape 2.   Fabriquer la paire de clefs à partir des BigInteger
        //------------------------------------------------------------------
        KeyFactory usine = KeyFactory.getInstance("RSA");
        RSAPublicKeySpec specClefPublique = new RSAPublicKeySpec(n,e);
        RSAPublicKey clefPublique = (RSAPublicKey) usine.generatePublic(specClefPublique);
        RSAPrivateKeySpec specClefPrivée = new RSAPrivateKeySpec(n,d);
        RSAPrivateKey clefPrivée = (RSAPrivateKey) usine.generatePrivate(specClefPrivée);    
        //------------------------------------------------------------------
        //  Etape 3.   Chiffrer et afficher le message chiffré
        //------------------------------------------------------------------
        chiffreur.init(Cipher.ENCRYPT_MODE, clefPublique);
        byte[] messageChiffré = chiffreur.doFinal(message);
        System.out.println("Message chiffré (en hexadécimal): \n" + toHex(messageChiffré));
        //------------------------------------------------------------------
        //  Etape 4.   Déchiffrer en guise de vérification
        //------------------------------------------------------------------
        chiffreur.init(Cipher.DECRYPT_MODE, clefPrivée);
        byte[] messageDéchiffré = chiffreur.doFinal(messageChiffré);
        System.out.println("Message déchiffré: \"" + new String(messageDéchiffré) +"\"");
    }

    public static String toHex(byte[] données) {
        StringBuffer sb = new StringBuffer();        
        for(byte k: données) {
            sb.append(String.format("%02X", k));
        }        
        return sb.toString();
    }
}

/* Avec padding OAEP, le chiffrement est non déterministe!
   $ make
   javac *.java 
   $ java RSA
   N: 12322204109610601400220276184439907358900550072909516638719564678287989...299
   E: 65537
   D: 37767385438721355925084255873299726737298831090000070299519339562807398...209
   Texte clair: "Alfred"
   Message clair (en hexadécimal): "416C66726564"
   Message chiffré (en hexadécimal): 
   886D00B9AA410CA1E17E59713870EA6807C30A909DB26619A750BE0D5324A51FF9765053A1...6F5
   Message déchiffré: "Alfred"
   $ java RSA
   N: 12322204109610601400220276184439907358900550072909516638719564678287989...299
   E: 65537
   D: 37767385438721355925084255873299726737298831090000070299519339562807398...209
   Texte clair: "Alfred"
   Message clair (en hexadécimal): "416C66726564"
   Message chiffré (en hexadécimal): 
   A74098D9D88A4BF2CCB0714E57D6F161D1B342C1D3F2E5B8327648FE43835F4F95B029C4BD...611
   Message déchiffré: "Alfred"
*/

