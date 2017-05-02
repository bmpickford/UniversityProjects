CC = gcc
CFLAGS = -Wall # Show all reasonable warnings
LDFLAGS = -lpthread

Server: Server_2.o account_balance.o account_details.o
	$(CC) -o Server Server_2.o account_balance.o account_details.o $(LDFLAGS)
	
	
.PHONY: clean

clean: rm -f Server *.o

