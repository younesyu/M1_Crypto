// -*- coding: utf-8 -*-
import java.math.BigInteger;
import java.util.Random;

public class EPP
{	
    public static void main(String[] args)
    {
        BigInteger n;
        int tentatives = 0;
        long startTime = System.nanoTime();
        do {
            n = new BigInteger(512, new Random());
            tentatives++;
        } while (!est_probablement_premier(n));
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000;
        System.out.println("Le nombre trouvé est " + n + ".");
        System.out.println("Trouvé en " + tentatives + " tentatives!");
        System.out.println("Temps de calcul : " + duration + " ms.");
    }

    static boolean est_probablement_premier(BigInteger n)
    {
        return n.isProbablePrime(50);			     
    }
}

/*
  $ make
  javac *.java 
  $ java EPP
  Le nombre 170141183460469231731687303715884105727 ...
*/

