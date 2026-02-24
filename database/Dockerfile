# Database image
FROM mysql:8.4

# Set environment variables for database container
ENV MYSQL_ROOT_PASSWORD=rootpassword123
ENV MYSQL_DATABASE=speedrun_project
ENV MYSQL_USER=speedrun_db
ENV MYSQL_PASSWORD=notmyrootpassword321

# Add SQL scripts to database container, these run if the database does not exist
COPY ./sql/create_database.sql /docker-entrypoint-initdb.d/
COPY ./sql/insert_data.sql /docker-entrypoint-initdb.d/