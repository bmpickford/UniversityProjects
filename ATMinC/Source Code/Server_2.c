/*
*  Materials downloaded from the web. See relevant web sites listed on OLT
*  Collected and modified for teaching purpose only by Jinglan Zhang, Aug. 2006
*/


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


	#define ARRAY_SIZE 30  /* Size of array to receive */
	
        #define MAX_DATA_SIZE 100

	#define BACKLOG 10     /* how many pending connections queue will hold */

	#define RETURNED_ERROR -1
	
        #define NUMBER_OF_ACCOUNTS 10
        
struct account_details {
    long int account_number;
    float openBalance;
    float closeBalance;
};

typedef struct account_details account_map;
account_map *account_t[NUMBER_OF_ACCOUNTS];

char *trimwhitespace(char *str) {
  char *end;

  // Trim leading space
  while(isspace((unsigned char)*str)) str++;

  if(*str == 0)  // All spaces?
    return str;

  // Trim trailing space
  end = str + strlen(str) - 1;
  while(end > str && isspace((unsigned char)*end)) end--;

  // Write new null terminator
  *(end+1) = 0;

  return str;
}


/**
 * Function designed to split the accounts string found in the file,
 * into the n seperate accounts
 * - n being the number of accounts
 **/
int splitAccounts(char *accounts, char *account1, char *account2, char *account3){
    int count = 0;
    const char del[2] = ",";
    char *token = malloc(sizeof(char)*ARRAY_SIZE);
    char accountstmp[30];
    strcpy(accountstmp, accounts);
    
    token = strtok(accountstmp, del);
    strcpy(account1, token);
    count++;
    
   
    if (token != NULL){
        token = strtok(NULL, del);
        count++;
        strcpy(account2, token);
    } 
    if (token != NULL){
        token = strtok(NULL, del);
        count++;
        strcpy(account3, token);
    }    
   
    return count;
}

/**
 * Function to determine what account is which (savings/credit/loan)
 */
void determineAccounts(int accountsArr[3], char *accounts[], int numAccounts){
    for (int i = 0; i < numAccounts; i++){
        if(atoi(accounts[i]) % 11 == 0){
            accountsArr[0] = 1;
        }
        if(atoi(accounts[i]) % 12 == 0){
            accountsArr[1] = 1;
        }
        if(atoi(accounts[i]) % 13 == 0){
            accountsArr[2] = 1;
        }
    }
}

/**
 * helper function for sendAccArray used to send send the account array to the client
 */
void sendAccount(int accountArr[3], int socket_id){
    for (int i = 0; i < 3; i++) {
        uint16_t statistics;
        statistics = htons(accountArr[i]);
        send(socket_id, &statistics, sizeof(uint16_t), 0);
    }   
}

void sendAccArray(int accountArr[3], int socket_id, int type){
    if (type == 0){ //Viewing all accounts
        sendAccount(accountArr, socket_id);
    } else if (type == 1){ //No loan account shown
        accountArr[2] = 0;
        sendAccount(accountArr, socket_id);
    }
}

void getMoney(int acc, char *allAccounts[], char *accBalance){
    FILE *file;
    char *buff;

    buff = (char * ) malloc(255 * sizeof(char));
    
    file = fopen("Accounts.txt", "r+");      
    while(fscanf(file, "%c", buff) == 1){
            fscanf(file, "%s", buff);
            if (strcmp(buff, allAccounts[acc]) == 0){
                fscanf(file, "%s", buff);
                fscanf(file, "%s", accBalance);
                return;
            } else { 
                fscanf(file, "%s", buff);
                fscanf(file, "%s", buff);
            }
    }
   
}
     


int withdraw(int accType, char *accountBal, char *withdAmount, char *finalAmount){    
    double tmpBalance = 0;
    tmpBalance =  atof(accountBal) - atof(withdAmount);
    
    if (accType == 1){
        if (tmpBalance < -5000){
            printf("\n\nwithdraw fail");
            return -1;
        } else {            
            sprintf(finalAmount, "%lf", tmpBalance);
            return 0;
        }
    } else {
        if (tmpBalance < 0){
            printf("\n\nwithdraw fail");
            return -1;
        } else {
            sprintf(finalAmount, "%lf", tmpBalance);
            return 0;
        }
    }
}

int deposit(char *accountBal, char *depAmount, char *finalAmount){    
    double tmpBalance = 0;
    tmpBalance =  atof(depAmount);

        if (tmpBalance > 1000){
            printf("\n\ndeposit fail");
            return -1;
        } else {            
            sprintf(finalAmount, "%lf", tmpBalance + atof(accountBal));
            return 0;
        }
}
void updateAccountFile(char *amount, char *finalAmount, char *fromAccount, char *toAccount, int tranType){
    FILE *file;
    FILE *file_1;
    FILE *file_2;

    char *buff;
    char *buff2;

    char *tmp = malloc(sizeof(char) * 30);
    char *tmp2 = malloc(sizeof(char) * 100);
    int counter = 0;
    int counterTmp = 0;
   
    buff = (char * ) malloc(255 * sizeof(char));
    buff2 = (char * ) malloc(255 * sizeof(char));

    file = fopen("Accounts.txt", "r+");
    file_1 = fopen("Transactions.txt", "a+");
    file_2 = fopen("Accounts.txt", "r+");

    if (tranType == 3){
        fprintf(file_1, "\n%-16s %-15s %-12d %.02f", fromAccount, toAccount, tranType, atof(amount));
    } else {
         fprintf(file_1, "\n%-16s %-15s %-11d -%.02f", fromAccount, toAccount, tranType, atof(amount));
    }

    while(fscanf(file, "%c", buff) == 1){
            counterTmp++;
            fscanf(file, "%s", buff); //ACCOUNT
            
            if (strcmp(buff, fromAccount) == 0){
                fscanf(file, "%s", tmp); //OPENING BAL
                strcat(tmp2, fromAccount);
                strcat(tmp2, tmp);
                strcat(tmp2, finalAmount);

                printf("\n\nFILE CHANGED\n\n");
                fscanf(file, "%s", buff); //OPENING BAL
                fscanf(file, "%s", buff); //CLOSING BAL
                counter = counterTmp;
                break;
            }
            fscanf(file, "%s", buff); //OPENING BAL
            fscanf(file, "%s", buff); //CLOSING BAL
    }
    counter = counter - 1;
    for (int i = 0; i < counter; i++){
        fscanf(file_2, "%s", buff2);
        fscanf(file_2, "%s", buff2);
        fscanf(file_2, "%s", buff2);
    }

    fprintf(file_2, "\n%-15s %-14s %.02f", fromAccount, tmp, atof(finalAmount));

    fflush(file);
    fflush(file_2);
    fclose(file);
    fclose(file_1);
    fclose(file_2);
}

int transfer(int accType, char *depAcc, char *withAcc, char *amount, char *finalDep, char *finalWith){
    deposit(depAcc, amount, finalDep);
    return (withdraw(accType, withAcc, amount, finalWith));
}

void sendTransferData(int socket_id, char *withAccNo, char *depAccNo, char *withAmount, char *depAmount){
    send(socket_id, withAccNo, 30 , 0);
    send(socket_id, depAccNo, 30 , 0);
    send(socket_id, withAmount, 30 , 0);
    send(socket_id, depAmount, 30 , 0);
}

void sendTransaction(int arr[], char *arr2[], int socket_id, int arrCount){
    uint16_t statistics;
    
    statistics = htons(arrCount);

    send(socket_id, &statistics, sizeof(uint16_t), 0); //Send SIZE OF ARRAY
    
    for (int i = 0; i < arrCount; i++) {
        statistics = htons(arr[i]);
        send(socket_id, &statistics, sizeof(uint16_t), 0); //Send ARRAY DATA
    }
    for (int i = 0; i < arrCount; i++) {
        send(socket_id, arr2[i], ARRAY_SIZE, 0); //Send ARRAY DATA
    }  
}

void getTransactionData(int socket_id, char *account){
    int transNo = 0;
    char *type = malloc(sizeof(char)*ARRAY_SIZE);
    char *amount = malloc(sizeof(char)*ARRAY_SIZE);
    FILE *file;
    FILE *file2;

    char *buff;

    int counter = 0;
    
    buff = (char * ) malloc(255 * sizeof(char));
    file = fopen("Transactions.txt", "r+");
    file2 = fopen("Transactions.txt", "r+");
    
    while(fscanf(file, "%c", buff) == 1){
        fscanf(file, "%s", buff); //ACC FROM
        if (strcmp(buff, account) == 0){
            transNo = transNo + 1;
            fscanf(file, "%s", buff); //ACC TO

            //break;
        }
        else {
            fscanf(file, "%s", buff); //ACC TO
            if (strcmp(buff, account) == 0){
                transNo = transNo + 1;
            }
        }

            //break;
       
        fscanf(file, "%s", type);
        fscanf(file, "%s", amount);
    }

    int typeArr[transNo];
    char **amountArr = (char**)malloc(transNo*sizeof(char*));
    for (int i = 0; i < transNo; i++){
        amountArr[i] = (char*)malloc(ARRAY_SIZE*sizeof(char));
    }
    
    char *tmp = malloc(sizeof(char)*ARRAY_SIZE);
    int tracker = 0;
    
    
    while(fscanf(file2, "%c", buff) == 1){
        fscanf(file2, "%s", buff); //ACC FROM
        if (strcmp(buff, account) == 0){
            fscanf(file2, "%s", buff);
            fscanf(file2, "%s", type);
            fscanf(file2, "%s", tmp);
            typeArr[counter] = atoi(type);
            strcpy(amountArr[counter], tmp);
            counter = counter + 1;
            tracker = 1;
        }
        else {
            fscanf(file2, "%s", buff);
            fscanf(file2, "%s", type);
            fscanf(file2, "%s", tmp);
            if (strcmp(buff, account) == 0 && tracker == 0){
                typeArr[counter] = atoi(type);
                strcpy(amountArr[counter], tmp);
                counter = counter + 1;
            }
        }
        tracker = 0;

    }
    sendTransaction(typeArr, amountArr, socket_id, transNo);

    fclose(file);
    fclose(file2);

    return;
    
}

void* Send_Array_Data(void* param) {
    int socket_id = (int)(intptr_t)param;
    
    char *username= malloc(sizeof(char)*ARRAY_SIZE);
    char *password= malloc(sizeof(char)*ARRAY_SIZE);
    char *clientNo= malloc(sizeof(char)*ARRAY_SIZE);
    char *fname = malloc(sizeof(char)*ARRAY_SIZE);
    char *sname = malloc(sizeof(char)*ARRAY_SIZE);
    char *errorStatus = malloc(sizeof(char)*4);
    char *accounts = malloc(sizeof(char)*ARRAY_SIZE);
    char *account1 = malloc(sizeof(char)*ARRAY_SIZE);
    char *account2 = malloc(sizeof(char)*ARRAY_SIZE);
    char *account3 = malloc(sizeof(char)*ARRAY_SIZE);
    char *select = malloc(sizeof(char)*ARRAY_SIZE);
    char *selectT = malloc(sizeof(char)*ARRAY_SIZE); //Used as an extra select for transfers
    char *accBalance = malloc(sizeof(char)*ARRAY_SIZE);
    char *accDepBalance = malloc(sizeof(char)*ARRAY_SIZE);
    char *accWithBalance = malloc(sizeof(char)*ARRAY_SIZE);
    char *withdAmount = malloc(sizeof(char)*10);
    char *depAmount = malloc(sizeof(char)*10);
    char *amount = malloc(sizeof(char)*10);
    char *finalAmount = malloc(sizeof(char)*10);
    char *finalAmountT = malloc(sizeof(char)*10);




    recv(socket_id, username, ARRAY_SIZE, 0);
    recv(socket_id, password, ARRAY_SIZE, 0);
    

    username = trimwhitespace(username);
    password = trimwhitespace(password);
    
    
    if(authenticate(clientNo, username, password) == RETURNED_ERROR){
        errorStatus = "-1";
        send(socket_id, errorStatus, sizeof(char)*4, 0);
    } else {
        errorStatus = "1";
        send(socket_id, errorStatus, sizeof(char)*4, 0);
    }
    
    //Gets account details from clientNo (name, accounts)
    getDetails(fname, sname, clientNo, accounts);

    int numAccounts = splitAccounts(accounts, account1, account2, account3);

    char *allAccounts[] = {
        account1,
        account2,
        account3,
    };
    
    int accountsArr[3] = {0, 0, 0}; //1 = saving, 2 = credit, 3 = loan. 1 for yes, 0 for no;
    
    determineAccounts(accountsArr, allAccounts, numAccounts);
    
    send(socket_id, fname, 30 , 0);
    send(socket_id, sname, 30 , 0);
    send(socket_id, clientNo, 30 , 0);
    int exiter = 0;
    
    while (exiter == 0){
        recv(socket_id, select, 30, 0);
        
        
        int sel = 0;
        switch(atoi(select)) {
                case 1: //Account Balance
                    sel = 1;
                    sendAccArray(accountsArr, socket_id, 0);
                    //displayAccounts(clientNo, allAccounts);
                    break;
                    
                case 2: //Withdrawal
                    sel = 2;
                    sendAccArray(accountsArr, socket_id, 1);
                    //displayAccounts(clientNo, allAccounts);
    //Do not show loan account though
                    break;
                    
                case 3: //Deposit
                    sel = 3;
                    sendAccArray(accountsArr, socket_id, 0);
                    //displayAccounts(clientNo, allAccounts); //Do not show loan account
                    break;
                    
                case 4: //Transfer
                    sel = 4;
                    sendAccArray(accountsArr, socket_id, 1);
                    recv(socket_id, selectT, 30, 0);


                    //transferAccounts(clientNo); //Will need input for to and from accounts
                    break;
                
                case 5: //Transaction Listing
                    sel = 5;
                    sendAccArray(accountsArr, socket_id, 0);

                    //displayAccounts(clientNo, allAccounts);
                    break;
                    
                case 6: //EXIT
                    exit(0);
                    break;
                    
            }
            
            recv(socket_id, select, 30, 0);
            
        // printf("\nsel: %s", select);
            uint16_t sendStat;

                
            int errorMSG = 0;
            if (sel == 1){
                switch(atoi(select)) {
                    case 1:
                        getMoney(0, allAccounts, accBalance);
                        break;
                    case 2:
                        getMoney(1, allAccounts, accBalance);
                        break;
                    case 3:
                        getMoney(2, allAccounts, accBalance);
                        break;
                }
                send(socket_id, accBalance, 30 , 0);
            } else if (sel == 2){ //WITHDRAW
                recv(socket_id, withdAmount, 30, 0);

                switch(atoi(select)){
                    case 1:
                        getMoney(0, allAccounts, accBalance);
                        errorMSG = withdraw(0, accBalance, withdAmount, finalAmount);
                        if (errorMSG != -1){
                            updateAccountFile(withdAmount, finalAmount, allAccounts[0], allAccounts[0], 2);
                        }
                        //Withdraw Savings
                        break;
                    case 2:
                        //withdraw credit
                        getMoney(1, allAccounts, accBalance);
                        errorMSG = withdraw(1, accBalance, withdAmount, finalAmount);
                        if (errorMSG != -1){
                            updateAccountFile(withdAmount, finalAmount, allAccounts[1], allAccounts[1], 2);
                        }
                        break;
                }
                //printf("\n\nerr: %d\n", errorMSG);
                sendStat = htons(errorMSG);
                send(socket_id, &sendStat, sizeof(uint16_t), 0);
                send(socket_id, finalAmount, 30 , 0);

            } else if (sel == 3){ //DEPOSIT
                recv(socket_id, depAmount, 30, 0);
                
                switch(atoi(select)){
                    case 1://Deposit Savings
                        getMoney(0, allAccounts, accBalance);
                        errorMSG = deposit(accBalance, depAmount, finalAmount);
                        //printf("err %d", errorMSG);
                        if (errorMSG != -1){
                            updateAccountFile(depAmount, finalAmount, allAccounts[0], allAccounts[0], 3);
                        }
                        break;
                    case 2:
                        //deposit credit
                        getMoney(1, allAccounts, accBalance);
                        errorMSG = deposit(accBalance, depAmount, finalAmount);
                        if (errorMSG != -1){
                            updateAccountFile(depAmount, finalAmount, allAccounts[1], allAccounts[1], 3);
                        }
                        break;
                    case 3:
                        //deposit loan
                        getMoney(2, allAccounts, accBalance);
                        errorMSG = deposit(accBalance, depAmount, finalAmount);
                        if (errorMSG != -1){
                            updateAccountFile(depAmount, finalAmount, allAccounts[2], allAccounts[2], 3);
                        }
                        break;
                }
                sendStat = htons(errorMSG);
                send(socket_id, &sendStat, sizeof(uint16_t), 0);
                send(socket_id, finalAmount, 30 , 0);
            } else if (sel == 4){ //TRANSFER
                int depAcc = atoi(selectT) - 1;
                int withAcc = atoi(select) - 1;
                recv(socket_id, amount, 30, 0);

                getMoney(depAcc, allAccounts, accDepBalance);
                getMoney(withAcc, allAccounts, accWithBalance);
                int err;
                if (withAcc == 1 || withAcc == 2){
                    err = transfer(1, accDepBalance, accWithBalance, amount, finalAmount, finalAmountT);
                } else {
                    err = transfer(0, accDepBalance, accWithBalance, amount, finalAmount, finalAmountT);
                }
                if (err != -1){
                    updateAccountFile(amount, finalAmount, allAccounts[withAcc], allAccounts[depAcc], 4);
                }
                
                sendTransferData(socket_id, allAccounts[withAcc], allAccounts[depAcc], finalAmountT, finalAmount);
            } else if (sel == 5){
                switch(atoi(select)) {
                    case 1:
                        getTransactionData(socket_id, allAccounts[0]);
                        break;
                        
                    case 2:
                        getTransactionData(socket_id, allAccounts[1]);
                        break;
                        
                    case 3:
                        getTransactionData(socket_id, allAccounts[2]);
                        break;
                }
            } else if (sel == 5){
                exiter = 1;
            }
    }
        
    return NULL;
}


int main(int argc, char *argv[]) {

	/* Thread and thread attributes */
	pthread_t client_thread;
	pthread_attr_t attr;


	int sockfd, new_fd;  /* listen on sock_fd, new connection on new_fd */
	struct sockaddr_in my_addr;    /* my address information */
	struct sockaddr_in their_addr; /* connector's address information */
	socklen_t sin_size;
	int i=0;
        int port_no;

	/* Get port number for server to listen on */
	if (argc == 2) {
            port_no = atoi(argv[1]);    
	} else if (argc == 1){
            port_no = 12345; //DEFAULT PORT
        } else {
            fprintf(stderr,"usage: port_number\n");
            exit(1);
        }

	/* generate the socket */
	if ((sockfd = socket(AF_INET, SOCK_STREAM, 0)) == -1) {
		perror("socket");
		exit(1);
	}

	/* generate the end point */
	my_addr.sin_family = AF_INET;         /* host byte order */
	my_addr.sin_port = htons(port_no);     /* short, network byte order */
	my_addr.sin_addr.s_addr = INADDR_ANY; /* auto-fill with my IP */
		/* bzero(&(my_addr.sin_zero), 8);   ZJL*/     /* zero the rest of the struct */

	/* bind the socket to the end point */
	if (bind(sockfd, (struct sockaddr *)&my_addr, sizeof(struct sockaddr)) \
	== -1) {
		perror("bind");
		exit(1);
	}

	/* start listnening */
	if (listen(sockfd, BACKLOG) == -1) {
		perror("listen");
		exit(1);
	}

	printf("server starts listnening ...\n");

	/* repeat: accept, send, close the connection */
	/* for every accepted connection, use a sepetate process or thread to serve it */
	while(1) {  /* main accept() loop */
		sin_size = sizeof(struct sockaddr_in);
		if ((new_fd = accept(sockfd, (struct sockaddr *)&their_addr, \
		&sin_size)) == -1) {
			perror("accept");
			continue;
		}
		printf("server: got connection from %s\n", \
			inet_ntoa(their_addr.sin_addr));

		//Create a thread to accept client
				
		pthread_attr_t attr;
		pthread_attr_init(&attr);
		pthread_create(&client_thread, &attr, Send_Array_Data, (void*)(intptr_t)new_fd);

		pthread_join(client_thread,NULL);

		if (send(new_fd, "All of array data sent by server\n\n\n", 60 , 0) == -1)
				perror("send");

	}

	close(new_fd);  
}
