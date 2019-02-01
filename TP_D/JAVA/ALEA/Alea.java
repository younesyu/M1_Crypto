// -*- coding: utf-8 -*-

import java.math.BigInteger;
import java.util.Random;

public class Alea
{	
    public static void main(String[] args)
    {
        Random alea = new Random();
        BigInteger x = new BigInteger(128, alea);
        System.out.println ("Valeur de x : " + x);
    }
}

/*
  $ make
  javac *.java 
  $ java Alea
  Valeur de x : 83298061311752912319685844512597155212
  $ java Alea
  Valeur de x : 285871252058034402763697089368152008986
  $ java Alea
  Valeur de x : 197542704085834536850620075483034476692
  $ java Alea
  Valeur de x : 118481379124562405626844429693602702101
  $
*/

