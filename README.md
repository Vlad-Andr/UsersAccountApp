# UsersBalanceApp

# Instruction
1) Run [docker-compose.yml](/docker-compose.yml)
2) Check what host postgres got through terminal. Type docker inspect (postgres container id / name) and find IPAddress (it is your host for url).
3) Open [application.properties](/src/main/resources/application.properties) and replace spring.datasource.url, spring.datasource.username, spring.datasource.password with your values.
4) Run application (port defined as 8090) and execute post request to /user/set-users-balance.
5) For check on 1 million just open [appropriate test](/src/test/java/org/solidgate/usersbalanceapp/UserAccountControllerTest.java), comment @Disabled and @AfterEach annotations and run it.

# Possible improvements

 Common:
 - Try to find the most efficient database configuration like optimal batch processing and adjusting amount of max connections in connection pool for handle access without db overloading
 - Add caching for reducing amount of repeated query requests to db
 - Depends on future requirements choose either vertical (if probably one request will contain >100k payload) or horizontal (if it is will be frequent requests) scaling

 Refactor:
 - Migrate to reactive approach for better concurrent processing optimization
 - Add in-memory cache like Redis for reduce frequent access to db. Possible scenario: 1) Save or update new data to Redis and when it is request to read firstly check Redis cache and if nothing were found check db. 2) Adjust scheduler that will check and update db`s data with Redis info with some delay. 
