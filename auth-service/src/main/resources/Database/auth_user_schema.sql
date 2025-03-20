-- Drop the existing auth_user table if it exists, and all dependencies (like default values)
DROP TABLE IF EXISTS auth_user CASCADE;

-- Drop sequence if it exists
DROP SEQUENCE IF EXISTS auth_user_sequence CASCADE;

-- Create the sequence for generating user IDs (starting with 1000)
CREATE SEQUENCE auth_user_sequence
    START WITH 1000
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

-- Create the auth_user table with required columns
CREATE TABLE auth_user (
    id INT DEFAULT nextval('auth_user_sequence') PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);