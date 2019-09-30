# Rate limited clients simulator
#### Simulates number of clients by executing parallel requests to specific server.  
1. Run rate limited server: https://github.com/shabshay/rate-limited-service  
2. Usage: rate limited clients simulator   
-a,--address <arg>   server address (default value: http://localhost:8080/?clientId=)  
-c,--clients <arg>   number of clients to simulate

#####  Example: 
 java rateLimitedClient.Main -c 5  
 
 3. Press any key to exit
