// -*- coding: utf-8 -*-

import java.math.BigInteger;
import java.util.Random;

public class Clefs {
    private static BigInteger code, codeChiffré, codeDéchiffré ;
    private static BigInteger n ;      // Le module de la clef publique
    private static BigInteger e ;      // L'exposant de la clef publique
    private static BigInteger d ;      // L'exposant de la clef privée
    
    static void fabrique() {           // Fabrique d'une paire de clefs RSA (A MODIFIER)
        BigInteger p, q;
        Random alea = new Random();

        p = new BigInteger(1024, 50, alea);
        System.out.println("p trouvé : " + p + "\n");

        do { 
            q = new BigInteger(1024, 50, alea);
        } while(p.equals(q));
        System.out.println("q trouvé : " + q + "\n");

        n = p.multiply(q);
        System.out.println("n trouvé : " + n + "\n");

        BigInteger w = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        System.out.println("w trouvé : " + w + "\n");

        do {
            d = new BigInteger(1024, alea).mod(w);
        } while(!(d.gcd(w).equals(BigInteger.ONE)));
        System.out.println("d trouvé : " + d + "\n");

        e = d.modInverse(w);
        System.out.println("e trouvé : " + e + "\n");

    }

    static void fabrique2() {           // Fabrique d'une paire de clefs RSA (A MODIFIER)
        BigInteger p, q, w;
        Random alea = new Random();

        e = new BigInteger("65537");
        System.out.println("e : " + e + "\n");

        do {
            p = new BigInteger(1024, 50, alea);
        } while (!e.gcd(p.subtract(BigInteger.ONE)).equals(BigInteger.ONE));

        do { 
            q = new BigInteger(1024, 50, alea);
        } while(p.equals(q) || (!e.gcd(q.subtract(BigInteger.ONE)).equals(BigInteger.ONE)));
        
        n = p.multiply(q);

        w = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));


        d = e.modInverse(w);    
        
        System.out.println("p trouvé : " + p + "\n");
        System.out.println("q trouvé : " + q + "\n");
        System.out.println("n trouvé : " + n + "\n");
        System.out.println("w trouvé : " + w + "\n");
        System.out.println("d trouvé : " + d + "\n");

    }

    static String os2ip() {

    }


    public static void main(String[] args) {  
        /*
          Affectation du code clair (Le message sous-jacent est "KYOTO")
          $ echo -n "KYOTO" | od -t x1
          0000000    4b  59  4f  54  4f                                            
          0000005
          $
        */
        code = new BigInteger("4b594f544f", 16);

        /* Affichage du code clair */
        System.out.println("Code clair        : " + code);
    
        fabrique2(); 

        /* Affichage des clefs utilisées */
        System.out.println("Clef publique (n) : " + n);
        System.out.println("Clef publique (e) : " + e);
        System.out.println("Clef privée (d)   : " + d);

        /* On effectue d'abord le chiffrement RSA du code clair avec la clef publique */
        codeChiffré = code.modPow(e, n);
        System.out.println("Code chiffré      : " + codeChiffré);

        /* On déchiffre ensuite avec la clef privée */
        codeDéchiffré = codeChiffré.modPow(d, n);
        System.out.println("Code déchiffré    : " + codeDéchiffré);

        if(codeDéchiffré.equals(code)) System.out.println("Code déchiffré égal au code clair.");
    }
}
