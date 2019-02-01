CC = gcc

INCLUDE = -I/usr/local/include -I/usr/include
LIB = -L/usr/local/lib -L/usr/lib -lgmp -std=c99 -Wall

all: clefs

clefs: clefs.c
	$(CC) -o clefs $(INCLUDE) clefs.c $(LIB)

clean:
	-rm *.o
	-rm clefs
