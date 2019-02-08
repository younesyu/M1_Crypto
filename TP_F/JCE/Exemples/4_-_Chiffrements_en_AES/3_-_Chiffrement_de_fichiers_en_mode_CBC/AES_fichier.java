// -*- coding: utf-8 -*-

import java.io.*;
import java.security.*;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class AES_fichier {
    private static final byte[] clefBrute = { // 16 octets
        (byte) 0x2b, (byte) 0x7e, (byte) 0x15, (byte) 0x16,
        (byte) 0x28, (byte) 0xae, (byte) 0xd2, (byte) 0xa6,
        (byte) 0xab, (byte) 0xf7, (byte) 0x15, (byte) 0x88,
        (byte) 0x09, (byte) 0xcf, (byte) 0x4f, (byte) 0x3c };	  
    private static Cipher chiffreur;
    private static SecretKeySpec clefSecrète;

    private static byte[] buffer = new byte[1024];
    private static int nbOctetsLus; 
    private static FileInputStream fis;
    private static FileOutputStream fos;
    private static CipherInputStream cis;
    
    public static void main(String[] args) {
        try {
            fis = new FileInputStream(args[0]); 
            fos = new FileOutputStream(args[1]);
        }
        catch (Exception e) { System.out.println("Fichier inexistant.");}	
        System.out.println("Clef utilisée: 0x" + toHex(clefBrute));
        //------------------------------------------------------------------
        //  Etape 1.   Récupérer un objet qui chiffre ou déchiffre en AES
        //             dans le mode CBC (non-déterministe) avec bourrage standard.
        //------------------------------------------------------------------
        try {
            chiffreur = Cipher.getInstance("AES/CBC/PKCS5Padding"); 
        }
        catch (Exception e) { System.out.println("AES n'est pas disponible.");}	
        //------------------------------------------------------------------
        //  Etape 2.   Fabriquer la clé AES de 128 bits correspondante et
        //             préparer le vecteur d'initialisation.
        //------------------------------------------------------------------
        clefSecrète = new SecretKeySpec(clefBrute, "AES");
        byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        IvParameterSpec ivspec = new IvParameterSpec(iv);
        //------------------------------------------------------------------
        //  Etape 3.   Chiffrer le fichier et sauvegarder le résultat
        //------------------------------------------------------------------
        try {
            chiffreur.init(Cipher.ENCRYPT_MODE, clefSecrète, ivspec);
            cis = new CipherInputStream(fis, chiffreur);
            while ( ( nbOctetsLus = cis.read(buffer) ) != -1 ) {
                fos.write(buffer, 0, nbOctetsLus);
            }
            fos.close();
            cis.close();
            fis.close();
        } catch (Exception e) { System.out.println("Chiffrement impossible:" + e.getMessage());}	
        //------------------------------------------------------------------
        //  Etape 4.   Déchiffrer en guise de test
        //------------------------------------------------------------------
        try{
            fis = new FileInputStream(args[1]); 
            fos = new FileOutputStream(args[2]);
        }
        catch (Exception e) { System.out.println("Fichier inexistant:"+ e.getMessage());}	
        try {
            chiffreur.init(Cipher.DECRYPT_MODE, clefSecrète, ivspec);
            cis = new CipherInputStream(fis, chiffreur);
            while ( ( nbOctetsLus = cis.read(buffer) ) != -1 ) {
                fos.write(buffer, 0, nbOctetsLus);
            }
            fos.close();
            cis.close();
            fis.close();
        } catch (Exception e) { System.out.println("Déchiffrement impossible:"+ e.getMessage());}	
    }

    public static String toHex(byte[] données) {
        StringBuffer sb = new StringBuffer();        
        for(byte k: données) {
            sb.append(String.format("%02X", k));
        }        
        return sb.toString();
    }
}

/* 
   $ make
   javac *.java 
   $ java AES_fichier butokuden.jpg chiffré.jpg déchiffré.jpg
   Clef utilisée: 0x2B7E151628AED2A6ABF7158809CF4F3C
   $ diff butokuden.jpg déchiffré.jpg 
   $ md5 chiffré.jpg 
   MD5 (chiffré.jpg) = 97f143de8af5bc84616329f9faaef78d
*/

/*
  Notez que butokuden-chiffre.jpg fait 12 octets de plus, à cause du
  bourrage et que sa taille est bien un multiple de 16.
*/

