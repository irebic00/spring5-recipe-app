## Use to run mysql db docker image, optional if you're not using a local mysqldb
# docker run -p 3306:3306 -e MYSQL_ALLOW_EMPTY_PASSWORD=yes -d mysql

# connect to mysql and run as root user
# Create Databases
CREATE DATABASE development;
CREATE DATABASE production;

# Create database service accounts
# Local MySQL
CREATE USER 'development_user'@'localhost' IDENTIFIED BY 'password';
CREATE USER 'production_user'@'localhost' IDENTIFIED BY 'password';
# MySQL in Docker container
CREATE USER 'development_user'@'%' IDENTIFIED BY 'password';
CREATE USER 'production_user'@'%' IDENTIFIED BY 'password';

# Database grants for local MySQL
GRANT SELECT ON development.* to 'development_user'@'localhost';
GRANT INSERT ON development.* to 'development_user'@'localhost';
GRANT DELETE ON development.* to 'development_user'@'localhost';
GRANT UPDATE ON development.* to 'development_user'@'localhost';
GRANT SELECT ON production.* to 'production_user'@'localhost';
GRANT INSERT ON production.* to 'production_user'@'localhost';
GRANT DELETE ON production.* to 'production_user'@'localhost';
GRANT UPDATE ON production.* to 'production_user'@'localhost';
# Database grants for local MySQL
GRANT SELECT ON development.* to 'development_user'@'%';
GRANT INSERT ON development.* to 'development_user'@'%';
GRANT DELETE ON development.* to 'development_user'@'%';
GRANT UPDATE ON development.* to 'development_user'@'%';
GRANT SELECT ON production.* to 'production_user'@'%';
GRANT INSERT ON production.* to 'production_user'@'%';
GRANT DELETE ON production.* to 'production_user'@'%';
GRANT UPDATE ON production.* to 'production_user'@'%';