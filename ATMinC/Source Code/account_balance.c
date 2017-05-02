
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


int authenticate(char *clientNo, char *username, char *pin){
        FILE *file;
        char *buff;
        
        buff = (char * ) malloc(255 * sizeof(char));
        
        char *fileUsername, *filePin;
        fileUsername = (char * ) malloc(50 * sizeof(char));
        filePin = (char * ) malloc(50 * sizeof(char));;
        
	file = fopen("Authentication.txt", "r+");

        /* While loop to read through the file and check
         * username/pin against each line.
         * Assigns the clientno to the storedClientNo variable
         * if pin and username match */

        while(fscanf(file, "%c", buff) == 1){
            fscanf(file, "%s", fileUsername);
            fscanf(file, "%s", filePin);
            fscanf(file, "%s", clientNo);

            
            if (strcmp(username, fileUsername) == 0 && strcmp(pin, filePin) == 0){
                printf("\n\nAUTH SUCCESS\n\n");
                //printf("Username: %s\n", fileUsername);       //Here for debugging purposes
                //printf("Pin : %s\n",filePin );
                //printf("ClientNo : %s\n", clientNo);      
                free(buff);
                fclose(file);
        
                return 1;
            }
            
        }
        
        printf("\n\nAUTH FAILED\n\n");
        free(buff);
        free(username);
        free(pin);
       
	fclose(file);
        
        return -1;
}