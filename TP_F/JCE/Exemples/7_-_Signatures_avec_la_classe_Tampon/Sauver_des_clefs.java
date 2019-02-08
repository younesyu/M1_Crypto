// -*- coding: utf-8 -*-

import java.*;
import java.math.BigInteger;

import java.security.*;
import javax.crypto.*;
import java.security.spec.*;
import java.security.interfaces.*;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class Sauver_des_clefs {
    public static void main(String[] args) throws Exception {
        BigInteger n = new BigInteger(
                                      "00af7958cb96d7af4c2e6448089362"+
                                      "31cc56e011f340c730b582a7704e55"+
                                      "9e3d797c2b697c4eec07ca5a903983"+
                                      "4c0566064d11121f1586829ef6900d"+
                                      "003ef414487ec492af7a12c34332e5"+
                                      "20fa7a0d79bf4566266bcf77c2e007"+
                                      "2a491dbafa7f93175aa9edbf3a7442"+
                                      "f83a75d78da5422baa4921e2e0df1c"+
                                      "50d6ab2ae44140af2b", 16);
        System.out.println("Module N: " + n);
        BigInteger e = BigInteger.valueOf(0x10001);
        System.out.println("Exposant public E: " + e);
        BigInteger d = new BigInteger(
                                      "35c854adf9eadbc0d6cb47c4d11f9c"+
                                      "b1cbc2dbdd99f2337cbeb2015b1124"+
                                      "f224a5294d289babfe6b483cc253fa"+
                                      "de00ba57aeaec6363bc7175fed20fe"+
                                      "fd4ca4565e0f185ca684bb72c12746"+
                                      "96079cded2e006d577cad2458a5015"+
                                      "0c18a32f343051e8023b8cedd49598"+
                                      "73abef69574dc9049a18821e606b0d"+
                                      "0d611894eb434a59", 16);
        System.out.println("Exposant privé D: " + d);

        Cipher chiffrement = Cipher.getInstance("RSA");
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        RSAPublicKeySpec specClefPublique = new RSAPublicKeySpec(n,e);
        RSAPrivateKeySpec specClefPrivee = new RSAPrivateKeySpec(n,d);
        RSAPublicKey clefPublique = (RSAPublicKey) keyFactory.generatePublic(specClefPublique);
        RSAPrivateKey clefPrivee = (RSAPrivateKey) keyFactory.generatePrivate(specClefPrivee);    

        /* On sauvegarde la clef publique */
        FileOutputStream fos = new FileOutputStream("publique.cle");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(clefPublique);
        oos.close();
        /* On sauvegarde la clef privee */
        fos = new FileOutputStream("privee.cle");
        oos = new ObjectOutputStream(fos);
        oos.writeObject(clefPrivee);
        oos.close();
    }
}

/*
  $ javac Sauver_des_clefs.java
  $ java Sauver_des_clefs
  Module N: 123222041096106014002202761844399073589005500729095166387...299
  Exposant public E: 65537
  Exposant privé D: 3776738543872135592508425587329972673729883109000...209
*/
