# rate-limited-client
#### simulates number of clients with parallel requests to specific server
### 1. run rate limited server: https://github.com/shabshay/rate-limited-service
### 2. usage: rate limited clients simulator 
#### -a,--address <arg>   server address (default value: http://localhost:8080/?clientId=)
 -c,--clients <arg>   number of clients to simulate
### 3. press any key to exit

#####  example: 
 java rateLimitedClient.Main -c 5
