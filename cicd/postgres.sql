create database USERS_SERVICE;
create database FRAUD_SERVICE;
create user USERS_SERVICE with password 'dev';
create user FRAUD_SERVICE with password 'dev';
grant all privileges on database USERS_SERVICE to USERS_SERVICE;
grant all privileges on database FRAUD_SERVICE to FRAUD_SERVICE;
grant all privileges on database USERS_SERVICE to dev;
grant all privileges on database FRAUD_SERVICE to dev;