#include <arpa/inet.h>
#include <stdio.h> 
#include <stdlib.h> 
#include <errno.h> 
#include <string.h> 
#include <sys/types.h> 
#include <netinet/in.h> 
#include <sys/socket.h> 
#include <sys/wait.h> 
#include <unistd.h>
#include <errno.h>
#include <pthread.h>
#include <stdint.h>
#include <ctype.h>
#include "account_balance.h"
#include "account_details.h"


void getDetails(char *fname, char *sname, char *clientno, char *accounts){
        FILE *file;
        char *buff;
        
        buff = (char * ) malloc(255 * sizeof(char));
        
        char *tmpclient;

        tmpclient = (char * ) malloc(50 * sizeof(char));

        
        file = fopen("Client_Details.txt", "r+");
        
         while(fscanf(file, "%c", buff) == 1){
            fscanf(file, "%s", fname);
            
            fscanf(file, "%s", sname);
            
            fscanf(file, "%s", tmpclient);
            if (strcmp(tmpclient, clientno) == 0){  
                fscanf(file, "%s", accounts);
                return;
            }
            fscanf(file, "%s", accounts);
        }
        printf("Can't find client details");
        fclose(file);
        return;
}
