CREATE TABLE IF NOT EXISTS "user_videos"
(
    id       VARCHAR(255) PRIMARY KEY,
    user_id  VARCHAR(255) PRIMARY KEY REFERENCES users (id),
    video_id VARCHAR(255) PRIMARY KEY REFERENCES videos (id)

);

COMMENT ON COLUMN "user_videos".user_id IS 'Id of the user table';
COMMENT ON COLUMN "user_videos".video_id IS 'Id of the video table';