// -*- coding: utf-8 -*-

#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <gmp.h>

mpz_t code, code_chiffre, code_dechiffre, e, n, d;           // Variables globales 

void fabrique(void){             // Fabrique d'une paire de clefs RSA (A MODIFIER)
  mpz_set_str(n, "196520034100071057065009920573", 10);
  mpz_set_str(e, "7", 10);
  mpz_set_str(d, "56148581171448620129544540223", 10);
}                                                

int main(void){
  mpz_inits(code, code_chiffre, code_dechiffre, (void *)NULL) ;
  mpz_init(n);                  // Le module de la clef publique
  mpz_init(e);                  // L'exposant de la clef publique
  mpz_init(d);                  // L'exposant de la clef privée  
  /*
    Affectation du code clair (Le message sous-jacent est "KYOTO")
    $ echo -n "KYOTO" | od -t x1
    0000000    4b  59  4f  54  4f                                            
    0000005
    $
  */
  mpz_set_str(code, "4b594f544f", 16);

  /* Affichage du code clair à l'aide de la fonction gmp_printf() */
  gmp_printf("Code clair        : %Zd\n", code);

  fabrique();

  /* Affichage des clefs utilisées à l'aide de la fonction gmp_printf() */
  gmp_printf("Clef publique (n) : %Zd\n", n);
  gmp_printf("Clef publique (e) : %Zd\n", e);
  gmp_printf("Clef privée (d)   : %Zd\n", d);

  /* On effectue d'abord le chiffrement RSA du code clair avec la clef publique */
  mpz_powm(code_chiffre, code, e, n);                    // Calcul du code chiffré
  gmp_printf("Code chiffré      : %Zd\n", code_chiffre);  

  /* On déchiffre ensuite avec la clef privée */
  mpz_powm(code_dechiffre, code_chiffre, d, n);        // Calcul du code déchiffré
  gmp_printf("Code déchiffré    : %Zd\n", code_dechiffre);

  mpz_clears(code, code_chiffre, code_dechiffre, n, e, d, (void *)NULL) ;
  exit(EXIT_SUCCESS);
}  

/*
  $ make
  gcc -o clefs -I/usr/local/include -I/usr/include clefs.c -L/usr/local/lib -L/usr/lib -lgmp -std=c99 -Wall
  $ ./clefs 
  Code clair        : 323620918351
  Clef publique (n) : 196520034100071057065009920573
  Clef publique (e) : 7
  Clef privée (d)   : 56148581171448620129544540223
  Code chiffré      : 34528838076398914398154896650
  Code déchiffré    : 323620918351
 */
