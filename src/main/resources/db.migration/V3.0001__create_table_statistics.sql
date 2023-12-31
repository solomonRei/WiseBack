CREATE TABLE IF NOT EXISTS "statistics"
(
    id                 VARCHAR(255) PRIMARY KEY,
    youtube_channel_id VARCHAR(255) UNIQUE NOT NULL,
    view_count         INTEGER DEFAULT 0   NOT NULL,
    video_count        INTEGER DEFAULT 0   NOT NULL,
    subscriber_count   INTEGER DEFAULT 0   NOT NULL,
    created_at         TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at         TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    user_id            VARCHAR(255)        NOT NULL REFERENCES users (id)
);

COMMENT ON COLUMN statistics.id IS 'This is the ID of the statistics so that the statistics can reference it';
COMMENT ON COLUMN statistics.youtube_channel_id IS 'This is the ID of the youtube channel so that the statistics can reference it';
COMMENT ON COLUMN statistics.view_count IS 'This is the general view count of the youtube channel';
COMMENT ON COLUMN statistics.video_count IS 'This is the general video count of the youtube channel';
COMMENT ON COLUMN statistics.subscriber_count IS 'This is the general subscriber count of the youtube channel';