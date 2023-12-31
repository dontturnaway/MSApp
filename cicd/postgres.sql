create user USERS_SERVICE with password 'dev';
create user ANTIFRAUD_SERVICE with password 'dev';
create user NOTIFICATION_SERVICE with password 'dev';

create database USERS_SERVICE;
create database ANTIFRAUD_SERVICE;
create database NOTIFICATION_SERVICE;

grant all privileges on database USERS_SERVICE to USERS_SERVICE;
grant all privileges on database ANTIFRAUD_SERVICE to ANTIFRAUD_SERVICE;
grant all privileges on database NOTIFICATION_SERVICE to NOTIFICATION_SERVICE;
grant all privileges on database USERS_SERVICE to dev;
grant all privileges on database ANTIFRAUD_SERVICE to dev;
grant all privileges on database NOTIFICATION_SERVICE to dev;

\connect USERS_SERVICE
CREATE SCHEMA IF NOT EXISTS USERS_SERVICE;
GRANT ALL PRIVILEGES ON SCHEMA USERS_SERVICE TO DEV;
GRANT ALL PRIVILEGES ON SCHEMA USERS_SERVICE TO USERS_SERVICE;

\connect ANTIFRAUD_SERVICE
CREATE SCHEMA IF NOT EXISTS ANTIFRAUD_SERVICE;
GRANT ALL PRIVILEGES ON SCHEMA ANTIFRAUD_SERVICE TO DEV;
GRANT ALL PRIVILEGES ON SCHEMA ANTIFRAUD_SERVICE TO ANTIFRAUD_SERVICE;

\connect NOTIFICATION_SERVICE
CREATE SCHEMA IF NOT EXISTS NOTIFICATION_SERVICE;
GRANT ALL PRIVILEGES ON SCHEMA NOTIFICATION_SERVICE TO DEV;
GRANT ALL PRIVILEGES ON SCHEMA NOTIFICATION_SERVICE TO NOTIFICATION_SERVICE;

