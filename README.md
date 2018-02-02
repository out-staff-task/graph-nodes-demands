# graph-nodes-demands
Repository for test task for routine the nodes of non-cyclic connected graph

## Running the application

To run the Application you will sure with: 

1. Installed Java JRE 1.8, the $PATH variable contains $JAVA_HOME path with /bin folder
2. Installed Maven (at least ver. 3.3.9) and appended to you $PATH variable
3. Installed PostgreSQL v9.6.6 server and running on 5432 port, user with ('db_user', 'password') will be active.
4. Open the file `create-ddl.sql` script from /resource and execute in PostgreSQL console. 

To run the application you should perform next:

1. Open CMD windows (Windows) or Terminal (Linux) in the source folder
2. Build the application using `gradle clean build`
3. In the same window run: `java -jar target/graph-nodes-demands-0.1-SNAPSHOT.jar` and wait for it to start up
