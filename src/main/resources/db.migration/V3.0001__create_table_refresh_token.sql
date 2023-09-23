CREATE TABLE IF NOT EXISTS "refresh_token"
(
    id UUID PRIMARY KEY,
    token      VARCHAR(256) UNIQUE NOT NULL,
    created_at TIMESTAMP           NOT NULL,
    expiry_date TIMESTAMP           NOT NULL,
    user_id UUID REFERENCES users(id)
);

ALTER TABLE "refresh_token"
    ALTER COLUMN id SET DATA TYPE UUID;