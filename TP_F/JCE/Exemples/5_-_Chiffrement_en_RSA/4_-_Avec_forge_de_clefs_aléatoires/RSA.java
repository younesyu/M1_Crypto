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
        System.out.println("Texte clair: \"Alfred\"");
        byte[] message = "Alfred".getBytes();
        System.out.println("Message clair (en hexadécimal): \"" + toHex(message) + "\"");

        //------------------------------------------------------------------
        //  Etape 1.   Récupérer un objet qui chiffre et déchiffre en RSA
        //             avec bourrage (mais sans mode opératoire : ECB)
        //------------------------------------------------------------------
        Cipher chiffreur = Cipher.getInstance("RSA/ECB/PKCS1Padding");

        //------------------------------------------------------------------
        //  Etape 2.   Fabriquer une paire de clefs aléatoire
        //------------------------------------------------------------------
        SecureRandom alea = new SecureRandom();
        KeyPairGenerator forge = KeyPairGenerator.getInstance("RSA");
        forge.initialize(1024, alea);                   // Des clefs de taille 1024, SVP
        KeyPair paireDeClefs = forge.generateKeyPair();
        Key clefPublique = paireDeClefs.getPublic();
        Key clefPrivée = paireDeClefs.getPrivate();

        KeyFactory usine = KeyFactory.getInstance("RSA");
        RSAPublicKeySpec specif = usine.getKeySpec(clefPublique, RSAPublicKeySpec.class);
        System.out.println("N = " + specif.getModulus());
        System.out.println("E = " + specif.getPublicExponent());
        RSAPrivateKeySpec specifPrivée = usine.getKeySpec(clefPrivée, RSAPrivateKeySpec.class);
        System.out.println("D = " + specifPrivée.getPrivateExponent());

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

/* Sans surprise, l'exposant public E est identique quelle que soit la clef...
   $ make
   javac *.java 
   $ java RSA
   Texte clair: "Alfred"
   Message clair (en hexadécimal): "416C66726564"
   N = 107371999646301734929295944792697543052043077893662075089936965609639...007
   E = 65537
   D = 416335391460063733161918999812629501508845185991690289788284201301883...649
   Message chiffré (en hexadécimal): 
   8837DBC6A23E481F2BD9F54104FF034E1E71DF4657E4B8B6A08D57C5D320C0785ACB4988A...286
   Message déchiffré: "Alfred"
   $ java RSA
   Texte clair: "Alfred"
   Message clair (en hexadécimal): "416C66726564"
   N = 135488784422373481944959328951337678426586026223140950443807322975322...881
   E = 65537
   D = 111949786612996420660107918001188437439584660665109870874202516844480...509
   Message chiffré (en hexadécimal): 
   AD189D760DAC2E899310267F52035D8FD9A1D13FBAC1D98BEA759C2B91E097E4F63F62662...C18
   Message déchiffré: "Alfred"
   $ java RSA
   Texte clair: "Alfred"
   Message clair (en hexadécimal): "416C66726564"
   N = 107614936391654596799739837750713476577098564073479169086596351055516...317
   E = 65537
   D = 711828050197327734145402134136965257734901315682030300426316709378920...453
   Message chiffré (en hexadécimal): 
   42506969FBA9454F8F145B8D3B1630F310BDFA75E41908077F035527A5F252E3D690D02F1...CE5
   Message déchiffré: "Alfred"
*/

