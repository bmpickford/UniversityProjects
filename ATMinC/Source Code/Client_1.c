#include <stdio.h> 
#include <stdlib.h> 
#include <errno.h> 
#include <string.h> 
#include <netdb.h> 
#include <sys/types.h> 
#include <netinet/in.h> 
#include <sys/socket.h> 
#include <unistd.h>

	#define MAXDATASIZE 100 /* max number of bytes we can get at once */

	#define ARRAY_SIZE 30

	#define RETURNED_ERROR -1
	
        #define MAX_NAME_SIZE 30


void Receive_Array_Int_Data(int socket_identifier, int accArr[3]) {
    int i=0;
    uint16_t statistics;

    for (i=0; i < 3; i++) {
            recv(socket_identifier, &statistics, sizeof(uint16_t), 0);		
            accArr[i] = ntohs(statistics);
    }
}

void Receive_Transaction_Data(int socket_identifier, int transArr[], int arrCount) {
    int i=0;
    uint16_t statistics;

    for (i=0; i < arrCount; i++) {
            recv(socket_identifier, &statistics, sizeof(uint16_t), 0);		
            transArr[i] = ntohs(statistics);
            printf("\n\nacc %d", transArr[i]); 

    }
}


void Receive_Transaction_Data1(int socket_identifier, char *transArr[], int arrCount) {
    int i=0;
    for (i=0; i < arrCount; i++) {
        recv(socket_identifier,transArr[i], ARRAY_SIZE, 0);		
    }
}
void Send_Data(int socket_id, char *myString) {
	int i=0;
	uint16_t statistics;  
	while(myString[i] != '\0'){
		statistics = htons(myString[i]);
		send(socket_id, &statistics, sizeof(uint16_t), 0);
                i++;
	}
}

void Send_User_Pass(int sockfd){
    	char username[MAX_NAME_SIZE];
        char password[MAX_NAME_SIZE];
	
	printf("Enter your username--> ");
	fgets(username, MAX_NAME_SIZE, stdin);
        printf("Enter your password--> ");
        fgets(password, MAX_NAME_SIZE, stdin);
        
        send(sockfd, username, ARRAY_SIZE, 0);
        send(sockfd, password, ARRAY_SIZE, 0);
}


void selection(char *select, char *clientNo){
    printf("\nPlease enter a selection");
    printf("\n\n<1> Account Balance\n<2> Withdrawal\n<3> Deposit\n<4> Transfer\n<5> Transaction Listing\n<6> EXIT\n\n");
    printf("Select an option 1-6 ->");
    
    
    
    fgets(select, 8, stdin);
    
    //strcpy(select, "1");
        
    if(atoi(select) <= 0 || atoi(select) > 6){
        printf("invalid selection, please select an option from the menu\n");
        selection(select, clientNo);
    }
     
        
}

void recieveTransfer(int socket_id, char *withAccNo, char *depAccNo, char *withAmount, char *depAmount){
    recv(socket_id, withAccNo, 30 , 0);
    recv(socket_id, depAccNo, 30 , 0);
    recv(socket_id, withAmount, 30 , 0);
    recv(socket_id, depAmount, 30 , 0);
}
void displayAccounts(int accArr[3], char *select){
        if (accArr[0] == 1){
            printf("\n\n1. Savings Account");
        }
        if (accArr[1] == 1){
            printf("\n2. Credit Account");
        }
        if (accArr[2] == 1){
            printf("\n3. Loan Account\n\n");
        }
        printf("\nEnter your selection (e/E to exit) - ");
        fgets(select, MAX_NAME_SIZE, stdin); //Reassigns the new selection to select
        
        if (strcmp(select, "e") == 0 || strcmp(select, "E") == 0){
            exit(0);
        } else if (atoi(select) > 3 || atoi(select) < 1){
            printf("\nInavlid input, re-enter your selection (e/E to exit) - ");
            displayAccounts(accArr, select);
        }
}


int main(int argc, char *argv[]) {
	int sockfd, numbytes, i=0;  
	char buf[MAXDATASIZE];
	struct hostent *he;
	struct sockaddr_in their_addr; /* connector's address information */
	int port_no = 12345;

        if (argc == 3) {
            port_no = atoi(argv[2]);    
	} else if (argc == 2){
            port_no = 12345; //DEFAULT PORT
        } else {
            fprintf(stderr,"usage: client_hostname port_number\n");
            exit(1);
	}

	if ((he=gethostbyname(argv[1])) == NULL) {  /* get the host info */
		herror("gethostbyname");
		exit(1);
	}

	if ((sockfd = socket(AF_INET, SOCK_STREAM, 0)) == -1) {
		perror("socket");
		exit(1);
	}


	their_addr.sin_family = AF_INET;      /* host byte order */
	their_addr.sin_port = htons(port_no);    /* short, network byte order */
	their_addr.sin_addr = *((struct in_addr *)he->h_addr);
	bzero(&(their_addr.sin_zero), 8);     /* zero the rest of the struct */

	if (connect(sockfd, (struct sockaddr *)&their_addr, \
	sizeof(struct sockaddr)) == -1) {
		perror("connect");
		exit(1);
	}

	
	Send_User_Pass(sockfd);
	/* Receive data from server */
	//Receive_Array_Int_Data(sockfd, ARRAY_SIZE);


		/* Receive message back from server */
        char *error = malloc(sizeof(char)*4);
        char *fname = malloc(sizeof(char)*ARRAY_SIZE);
        char *sname = malloc(sizeof(char)*ARRAY_SIZE);
        char *clientNo = malloc(sizeof(char)*ARRAY_SIZE);
        char *accountBal = malloc(sizeof(char)*ARRAY_SIZE);
        char *depBal = malloc(sizeof(char)*ARRAY_SIZE);
        char *withBal = malloc(sizeof(char)*ARRAY_SIZE);
        char *depAccNo = malloc(sizeof(char)*ARRAY_SIZE);
        char *withAccNo = malloc(sizeof(char)*ARRAY_SIZE);
        char *tmpGet = malloc(sizeof(char)*ARRAY_SIZE);
        char *select = (char * ) malloc(4 * sizeof(char));        
        char *actionSelect = (char * ) malloc(4 * sizeof(char));
        char *transSelect = malloc(sizeof(char)*ARRAY_SIZE);
        int accArr[3] = {0, 0, 0};
        char *withdrawalAmount = malloc(10*sizeof(char));
        char *depositAmount = malloc(10*sizeof(char));
        int errorMSG = 0;
        uint16_t recvStat;


        if ((numbytes=recv(sockfd, error, sizeof(char)*4, 0)) == -1) {
            perror("recv");
	}
	if(atoi(error) == -1){
            printf("You entered incorrect details, terminating application\n\n");
            exit(1);
        }
        if ((numbytes=recv(sockfd, fname, 30, 0)) == -1) {
            perror("recv");
	}

        if ((numbytes=recv(sockfd, sname, 30, 0)) == -1) {
            perror("recv");
	}
	
        if ((numbytes=recv(sockfd, clientNo, 30, 0)) == -1) {
            perror("recv");
	}
	

        int exiter = 0;
        
        
        while(exiter == 0){
            printf("\n\nYou are currently logged in as %s %s", fname, sname);
            printf("\nClient Number - %s\n\n", clientNo);
            
            //Selects action after login (1-6) and sends
            selection(actionSelect, clientNo);
            send(sockfd, actionSelect, ARRAY_SIZE, 0);
            
            //Recieves arraydata to determine what accounts to display
            Receive_Array_Int_Data(sockfd, accArr);
            
            if (atoi(actionSelect) == 4){
                printf("\n\nSelect an account to transfer to: ");
                displayAccounts(accArr, select);
                send(sockfd, select, ARRAY_SIZE, 0);

                for (int i = 0; i < 3; i++){
                    accArr[i] = 1;
                }
                displayAccounts(accArr, transSelect);
                send(sockfd, transSelect, ARRAY_SIZE, 0);

                
            } else {
                displayAccounts(accArr, select);
                send(sockfd, select, ARRAY_SIZE, 0);
            }
            
            
            printf("\n\n\nAccount Name: %s %s\n", fname, sname);

            if (atoi(actionSelect) == 1){
                recv(sockfd, accountBal, 30, 0);
                printf("\nYour balance is: %.02f\n\n\n", atof(accountBal));
                printf("\n\n\nPress enter to continue...\n");
                fgets(tmpGet, ARRAY_SIZE, stdin);
                accountBal = "";

                //exiter = 1;
            } else if (atoi(actionSelect) == 2){
                printf("\nEnter Amount To Withdraw (e/E to exit): ");
                fgets(withdrawalAmount, ARRAY_SIZE, stdin);
                send(sockfd, withdrawalAmount, ARRAY_SIZE, 0);
                
                recv(sockfd, &recvStat, sizeof(uint16_t), 0);		
                errorMSG = ntohs(recvStat);

                if (errorMSG != 0){
                    printf("You have insufficient funds\n\n");
                    printf("\n\n\nPress enter to continue...\n");
                    fgets(tmpGet, ARRAY_SIZE, stdin);
                    break;
                    //exit(-1);
                }
                recv(sockfd, accountBal, 30, 0);
                printf("Withdrawal Successfuly, your closing balance is: %.02f\n\n", atof(accountBal));
                printf("\n\n\nPress enter to continue...\n");
                fgets(tmpGet, ARRAY_SIZE, stdin);
                //exiter = 1;
            } else if (atoi(actionSelect) == 3){
                printf("\nEnter Amount To Deposit (e/E to exit): ");
                fgets(depositAmount, ARRAY_SIZE, stdin);
                send(sockfd, depositAmount, ARRAY_SIZE, 0);
                
                recv(sockfd, &recvStat, sizeof(uint16_t), 0);		
                errorMSG = ntohs(recvStat);

                if (errorMSG != 0){
                    printf("You cannot deposit more than $1000.00 in a single transaction\n\n");
                    printf("\n\n\nPress enter to continue...\n");
                    fgets(tmpGet, ARRAY_SIZE, stdin);
                    break;
                    //exit(-1);
                }
                
                recv(sockfd, accountBal, 30, 0);
                printf("Deposit Successfuly, your closing balance is: %.02f\n\n", atof(accountBal));
                printf("\n\n\nPress enter to continue...\n");
                fgets(tmpGet, ARRAY_SIZE, stdin);
                //exiter = 1;

            } else if (atoi(actionSelect) == 4){
                printf("\nEnter Amount To Transfer (e/E to exit): ");
                fgets(depositAmount, ARRAY_SIZE, stdin);
                send(sockfd, depositAmount, ARRAY_SIZE, 0);
                recieveTransfer(sockfd, withAccNo, depAccNo, withBal, depBal);
                
                printf("\n\nDeducted $%s From: Account %s - Closing Balance - $%.02f", depositAmount, withAccNo, atof(withBal));
                printf("\nTransfer $%s Dest: Account %s - Closing Balance - $%.02f\n\n", depositAmount, depAccNo, atof(depBal));
                printf("\n\n\nPress enter to continue...\n");
                fgets(tmpGet, ARRAY_SIZE, stdin);
                //exiter = 1;

            } else if (atoi(actionSelect) == 5){
                recv(sockfd, &recvStat, sizeof(uint16_t), 0);		
                int numTrans = ntohs(recvStat);
                
                if (numTrans == 0){
                    printf("There are no transactions to display\n\n");
                    printf("\n\n\nPress enter to continue...\n");
                    fgets(tmpGet, ARRAY_SIZE, stdin);
                    break;
                } 
                int accData[numTrans];
                char **amountData = (char**)malloc(numTrans*sizeof(char*));
                for (int i = 0; i < numTrans; i++){
                    amountData[i] = (char*)malloc(ARRAY_SIZE*sizeof(char));
                }
    
                Receive_Transaction_Data(sockfd, accData, numTrans);
                Receive_Transaction_Data1(sockfd, amountData, numTrans);
  
                printf("\n\nTransaction\tTransfer Type\tAmount\n");
                for(int i = 0; i < numTrans; i++){
                    printf("%-15d %-15d %.02f\n", i, accData[i], atof(amountData[i]));
                }
                printf("\n\n\nPress enter to continue...\n");
                fgets(tmpGet, ARRAY_SIZE, stdin);
                //exiter = 1;
            } else if (atoi(actionSelect) == 6){
                exiter = 1;
            }
        }
        
        
        
        //char *tmp = malloc(sizeof(char)*50);
        
        //fgets(tmp, MAX_NAME_SIZE, stdin);

	buf[numbytes] = '\0';
	

	//printf("Received: %s", buf);

	close(sockfd);

	return 0;
}
