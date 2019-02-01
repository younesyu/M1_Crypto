CC = gcc

INCLUDE = -I/usr/local/include -I/usr/include
LIB = -L/usr/local/lib -L/usr/lib -lgmp -std=c99 -Wall

all: alea

alea: alea.c
	$(CC) -o alea $(INCLUDE) alea.c $(LIB)

clean:
	-rm *.o
	-rm alea
