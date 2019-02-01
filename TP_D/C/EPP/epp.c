// -*- coding: utf-8 -*-

#include <stdlib.h>
#include <stdio.h>
#include "gmp.h"

int est_probablement_premier(mpz_t n)
{
  /*
    Modifiez cette fonction afin qu'elle retourne si oui (1)
    ou non (0), l'entier n est un nombre premier, avec un
    taux d'erreur inférieur à 1/1000 000 000 000 000 000.
  */  
  return 0;			     
}
    
int main(void)
{
  mpz_t n;                          // Déclaration de l'entier GMP n
  mpz_init(n);                      // Initialisation de l'entier GMP n
  mpz_set_str(n, "170141183460469231731687303715884105727", 10);
  gmp_printf("Le nombre %Zd", n);   // Affichage de l'entier GMP n, en décimal
  if (est_probablement_premier(n))
    printf(" est très probablement premier!\n");
  else
    printf(" n'est absolument pas premier!\n");
  mpz_clear(n);                      // Libération de la mémoire allouée à n
  exit(EXIT_SUCCESS);
}


/*
  $ make
  gcc -o epp -I/usr/local/include -I/usr/include epp.c -L/usr/local/lib -L/usr/lib -lgmp
  $./epp
  Le nombre 170141183460469231731687303715884105727 ...
*/
