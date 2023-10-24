CREATE TABLE IF NOT EXISTS "user_profiles"
(
    id                          VARCHAR(255) PRIMARY KEY,
    profile_picture_url         VARCHAR(1024),
    youtube_channel_id          VARCHAR(255) UNIQUE                                 NOT NULL,
    youtube_channel_name        VARCHAR(255)                                        NOT NULL,
    youtube_first_video_date    TIMESTAMP WITH TIME ZONE,
    youtube_registration_date   TIMESTAMP WITH TIME ZONE,
    youtube_handle              VARCHAR(255) UNIQUE,
    created_at                  TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP  NOT NULL,
    updated_at                  TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP  NOT NULL,
    user_id                     VARCHAR(255)                                        NOT NULL REFERENCES users (id)
);

COMMENT ON COLUMN "user_profiles".id IS 'Unique id for a user profile';
COMMENT ON COLUMN "user_profiles".profile_picture_url IS 'URL to the user''s profile picture';
COMMENT ON COLUMN "user_profiles".youtube_channel_id IS 'Unique identifier for the user''s YouTube channel';
COMMENT ON COLUMN "user_profiles".youtube_channel_name IS 'Name of the user''s YouTube channel';
COMMENT ON COLUMN "user_profiles".youtube_first_video_date IS 'Date the user published their first video on YouTube';
COMMENT ON COLUMN "user_profiles".youtube_registration_date IS 'Date the user registered their YouTube channel';
COMMENT ON COLUMN "user_profiles".youtube_handle IS 'Custom tag associated with the user profile';
