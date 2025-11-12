--- Local development only ---

-- Databases
CREATE DATABASE course_db;
CREATE DATABASE identity_db;

-- Users
CREATE USER course_service WITH PASSWORD 'password';
GRANT ALL PRIVILEGES ON DATABASE course_db TO course_service;

CREATE USER identity_service WITH PASSWORD 'password';
GRANT ALL PRIVILEGES ON DATABASE identity_db TO identity_service;
