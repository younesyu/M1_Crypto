// -*- coding: utf-8 -*-

import java.io.*;
import java.security.*;
import javax.crypto.*;
import java.security.spec.*;
import java.security.interfaces.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Tampon {

    private static void printUsage()throws Exception {
        System.out.println("Usage: java Tampon -signer clefprive document appendice");
        System.out.println("Usage: java Tampon -verifier clefpublique document appendice");
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 4 ) {
            printUsage();
            return;
        }
        String options = args[0];
        String clef = args[1];
        String document = args[2];
        String appendice = args[3];

        /* On se prépare à récupérer la clef (privée ou publique) */
        FileInputStream fis = new FileInputStream(clef);
        ObjectInputStream ois = new ObjectInputStream(fis);
      
        /* On initialise un objet de Signature avec la clef */
        Signature signeur = Signature.getInstance("MD5withRSA");
        if (options.equals("-signer")) {
            PrivateKey clefPrivée = (PrivateKey) ois.readObject();
            signeur.initSign(clefPrivée);	
        } else  if (options.equals("-verifier")) {
            PublicKey clefPublique = (PublicKey) ois.readObject();
            signeur.initVerify(clefPublique);
        } else { printUsage(); return; }
        ois.close();


        /* Quelle que soir l'option, on calcule le résumé du document */
        fis = new FileInputStream(document);
        byte[] buffer = new byte[1024];
        int nbOctetsLus;
        while ((nbOctetsLus = fis.read(buffer)) != -1) {
            signeur.update(buffer, 0, nbOctetsLus);
        }
        fis.close();
        /* Le résumé obtenu est stocké par l'objet "signeur" */

        if (options.equals("-signer")) {
            byte[] tampon = signeur.sign(); // Déchiffrement du résumé...
            FileOutputStream fos = new FileOutputStream(appendice);
            fos.write(tampon);         // Ecriture de l'appendice d'un coup
            fos.close();
        } else  if (options.equals("-verifier")) {
            byte[] tampon = Files.readAllBytes(Paths.get(appendice));
            if (signeur.verify(tampon)) // Chiffrement et comparaison
                System.out.println("La signature est correcte.");
            else
                System.out.println("La signature est fausse!");
        }
    }
}

/* 
   $ ls -al
   drwxr--r--  11 remi  staff     374  2 fév 22:17 .
   drwxr-xr-x  11 remi  staff     374  2 fév 21:46 ..
   -rwxr--r--   1 remi  staff      44 22 mar  2013 Makefile
   -rwxr--r--   1 remi  staff    2894 25 oct  2016 Sauver_des_clefs.java
   -rw-r--r--   1 remi  staff    3144  2 fév 22:13 Tampon.java
   -rw-r--r--@  1 remi  staff  467796  2 fév 22:15 butokuden.jpg
   $ make
   javac *.java 
   $ java Sauver_des_clefs
   Module N: 123222041096106014002202761844399073589005500729095166387...299
   Exposant public E: 65537
   Exposant privé D: 3776738543872135592508425587329972673729883109000...209
   $ ls -al
   drwxr--r--  11 remi  staff     374  2 fév 22:17 .
   drwxr-xr-x  11 remi  staff     374  2 fév 21:46 ..
   -rwxr--r--   1 remi  staff      44 22 mar  2013 Makefile
   -rw-r--r--   1 remi  staff    2491  2 fév 22:17 Sauver_des_clefs.class
   -rwxr--r--   1 remi  staff    2894 25 oct  2016 Sauver_des_clefs.java
   -rw-r--r--   1 remi  staff    2095  2 fév 22:17 Tampon.class
   -rw-r--r--   1 remi  staff    3144  2 fév 22:13 Tampon.java
   -rw-r--r--@  1 remi  staff  467796  2 fév 22:15 butokuden.jpg
   -rw-r--r--   1 remi  staff     573  2 fév 22:17 privee.cle
   -rw-r--r--   1 remi  staff     419  2 fév 22:17 publique.cle
   $ java Tampon -signer privee.cle butokuden.jpg monAppendice
   $ java Tampon -verifier publique.cle butokuden.jpg monAppendice 
   La signature est correcte.
   $ java Tampon -verifier publique.cle Makefile monAppendice 
   La signature est fausse!
   $ 
*/

