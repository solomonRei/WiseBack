CREATE TABLE IF NOT EXISTS "users"
(
    id         VARCHAR(255) PRIMARY KEY,
    username   VARCHAR(100) UNIQUE                                NOT NULL,
    email      VARCHAR(320) UNIQUE                                NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL
);

COMMENT ON COLUMN "users".id IS 'Unique identifier for a user table';
COMMENT ON COLUMN "users".username IS 'Username of the name of user authenticated from google';
COMMENT ON COLUMN "users".email IS 'Email of the user authenticated from google';
