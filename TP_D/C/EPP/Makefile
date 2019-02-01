CC = gcc

INCLUDE = -I/usr/local/include -I/usr/include
LIB = -L/usr/local/lib -L/usr/lib -lgmp

all: epp

epp: epp.c
	$(CC) -o epp $(INCLUDE) epp.c $(LIB)

clean:
	-rm *.o
	-rm epp
	-rm *~


