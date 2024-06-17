# UsersBalanceApp

# Instruction
1) Run [docker-compose.yml](/docker-compose.yml)
2) Check what host postgres got through terminal. Type docker inspect (postgres container id / name) and find IPAddress (it is your host for url).
3) Open [application.properties](/src/main/resources/application.properties) and replace spring.datasource.url, spring.datasource.username, spring.datasource.password with your values.
4) Run application (port defined as 8090) and execute post request to /user/set-users-balance.
5) For check on 1 million just open [appropriate test](/src/test/java/org/solidgate/usersbalanceapp/UserAccountControllerTest.java), comment @Disabled and @AfterEach annotations and run it.

# Possible improvements

 Common:
 - Try to find the most efficient database configuration, such as optimizing batch processing and adjusting the maximum number of connections in the connection pool to handle access without overloading the database.
 - Add caching to reduce the number of repeated query requests to the database.
 - Depending on future requirements, choose either vertical scaling (if a single request might contain >100k payload) or horizontal scaling (if there will be frequent requests).

 Refactor:
 - Migrate to a reactive approach for better optimization of concurrent processing.
 - Add an in-memory cache like Redis to reduce frequent access to the database. Possible scenarios: 1) Save or update new data to Redis. When a read request is made, first check the Redis cache. If the data is not found in Redis, then check the database. 2) Implement a scheduler that with some delay checks and updates the database with information from Redis.
