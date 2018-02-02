# graph-nodes-demands
Repository for test task for routine the nodes of non-cyclic connected graph

## Running the application

To run the Application you will sure with: 

1. Installed Java JRE 1.8, the $PATH variable contains $JAVA_HOME path with /bin folder
2. Installed Maven (at least ver. 3.3.9) and appended to you $PATH variable
3. Installed PostgreSQL v9.6.6 server and running on 5432 port, user with ('db_user', 'password') will be active.

To run the application you should perform next:

1. Open CMD windows (Windows) or Terminal (MacOS, Linux) in the source folder
2. Build the application using `mvn clean package`
3. In the same window run: `java -jar target/graph-nodes-demands-0.1.jar` and wait for it to start up
