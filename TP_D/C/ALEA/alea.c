// -*- coding: utf-8 -*-

#include <time.h>
#include <gmp.h>

int main(void){
  gmp_randstate_t alea;
  gmp_randinit_default(alea);
  gmp_randseed_ui(alea, time(NULL));
  mpz_t x;
  mpz_init(x);
  mpz_urandomb(x, alea, 128);
  gmp_printf("Valeur de x : %Zd\n", x);
}

/*
  $ gcc -o alea -I/usr/local/include -I/usr/include alea.c -L/usr/local/lib
  -L/usr/lib -lgmp -std=c99 -Wall
  $ ./alea 
  Valeur de x : 109155632809072358652877831555722227384
  $ ./alea 
  Valeur de x : 217788013589942587043293462948907129709
  $ ./alea 
  Valeur de x : 12975201395112266227997425492544723545
  $ ./alea 
  Valeur de x : 59810744179216515528734482725229368450
*/

