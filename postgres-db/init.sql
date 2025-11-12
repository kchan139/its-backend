-- init.sql
CREATE DATABASE content_db;
CREATE DATABASE auth_db;
CREATE DATABASE user_db;

CREATE USER content_service WITH PASSWORD 'password';
GRANT ALL PRIVILEGES ON DATABASE content_db TO content_service;

CREATE USER auth_service WITH PASSWORD 'password';
GRANT ALL PRIVILEGES ON DATABASE auth_db TO auth_service;