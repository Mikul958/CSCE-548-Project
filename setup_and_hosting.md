# CSCE 548 - Speedrun App Setup and Deployment

## Environment Setup

### IDE and Git
* Download an IDE of your choice to work with the project. This project was originally developed using Visual Studio Code, but the steps to set up and run the project are not affected significantly by the chosen IDE.
* Download and install Git and create a GitHub account.

### Database Setup
* Download and install MySQL and MySQL Workbench to allow for work on a local database.
* In MySQL workbench, create and open a new database connection
  * Run the `create_database.sql` script located in `<project root>/database/sql` to create the database and all of its tables.
  * Optionally, run the `insert_data.sql` script to create mock data that can be used for testing.

### Java and Maven
* Download and install JDK 21 for your operating system.
  * Verify Java has been successfully installed using `java --version`
* Download and install Maven 3.8.xx or higher.
  * Verify Maven has been successfully installed using `mvn -v`
  * Locate your JDK 21 installation and ensure your JAVA_HOME environment variable is set to the same path as your JDK’s root folder.
* Install dependencies for the API by navigating to `<project root>/api` and running `mvn install`, or by any other method your IDE of choice permits.
* The project can now be run locally using any method that your IDE of choice permits.
  * If your IDE does not support this, the API can be built with `mvn clean install`, and the resulting JAR can be found in `<project-root>/api/target`.

### Node and Angular
* Install Node 24, including npm in the installation options.
  * Verify Node has been successfully installed using `node -v`
  * Verify npm has been successfully installed using `npm -v`
* Install dependencies for the website by navigating to `<project root>/frontend` and running `npm install`.
* The project can now be run locally by navigating to `<project root>/frontend` and running `ng serve`.

## Deployment
This project is intended to be deployed through Fly.io:
* Before starting, create a Fly.io account and link billing information.
* Install flyctl, Fly’s command line tool.
  * Upon deploying for the first time, flyctl will ask you to log into your Fly account from the terminal.

### Database
The database container requires the use of Fly volumes for persistent storage, and so the container setup is slightly longer compared to the API and website.
* Navigate to `<project root>/database` to ensure you are using the database Dockerfile and fly.toml configuration.
* Create the database container with `fly apps create speedrun-mysql`.
* Create a container volume with `fly volumes create mysql_data -r iad -n 1`.
* Deploy the database container by running `fly deploy`.
  * Database creation scripts should run automatically during this stage -- if you find the database ends up empty you can use `fly ssh console` to SSH into the container and run these scripts manually in SQL (located in docker-entrypoint-initdb-d, root pass=rootpassword123).
* The app’s status can be checked by using `fly apps list` and/or `fly status`.

### API
* Navigate to `<project root>/api` to ensure you are using the Spring Boot Dockerfile and fly.toml configuration.
* Create the API container with `fly apps create speedrun-csce548`.
* Deploy the API container by running `fly deploy`.
  * Use `fly logs` to examine the logs of the API as it starts up and runs.
  * Optionally, visit the URL `{{fly.io app url}}/health`; if the server is running, it should return a `200 OK`.

### Website
* Navigate to `<project root>`/frontend to ensure you are using the Angular Dockerfile and fly.toml configuration.
* Create the website container with `fly apps create speedrun-csce548-fe`.
* Deploy the website container by running `fly deploy`.
  * The website can be visited at https://speedrun-csce548-fe.fly.dev.

Following code changes to the API or frontend, the updated app can be deployed to Fly immediately by simply navigating to that app’s project folder and running `fly deploy` again. Any changes made to the database schema will have to be performed directly on the Fly server.
