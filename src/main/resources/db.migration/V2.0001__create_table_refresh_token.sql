CREATE TABLE IF NOT EXISTS "refresh_token"
(
    id          VARCHAR(255) PRIMARY KEY,
    token       VARCHAR(1024) UNIQUE NOT NULL,
    created_at  TIMESTAMP            NOT NULL,
    expiry_date TIMESTAMP            NOT NULL,
    user_id     VARCHAR(255)         NOT NULL
);