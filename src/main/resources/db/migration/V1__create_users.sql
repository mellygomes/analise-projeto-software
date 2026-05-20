CREATE SEQUENCE IF NOT EXISTS primary_key_seq
START WITH 1
INCREMENT BY 1;

CREATE TABLE public.users
(
    id BIGINT PRIMARY KEY DEFAULT nextval('primary_key_seq'),
    reference_id VARCHAR(255) UNIQUE NOT NULL,
    created_by BIGINT,
    updated_by BIGINT,
    created_at TIMESTAMP(6) WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP(6) WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    email VARCHAR(255) UNIQUE,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    password VARCHAR(255),
    username VARCHAR(255) UNIQUE,
    bio VARCHAR(255),
    enabled BOOLEAN NOT NULL DEFAULT FALSE,
    last_login TIMESTAMP(6) WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    profile_picture BYTEA
);
