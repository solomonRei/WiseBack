CREATE TABLE IF NOT EXISTS "users" (
    id UUID PRIMARY KEY ,
    username VARCHAR(100) UNIQUE NOT NULL,
    email VARCHAR(320) UNIQUE NOT NULL,
    access_token VARCHAR(100) NULL,
    refresh_token VARCHAR(100) NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    CONSTRAINT username_length CHECK (LENGTH(name) <= 100),
    );