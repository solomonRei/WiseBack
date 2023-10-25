CREATE TABLE IF NOT EXISTS "videos"
(
    id            VARCHAR(255) PRIMARY KEY,
    video_id      VARCHAR(255) UNIQUE                                NOT NULL,
    possible_idea VARCHAR(4000)            DEFAULT NULL,
    summary       VARCHAR(4000)            DEFAULT NULL,
    tags          JSONB                                              NOT NULL,
    possible_tags JSONB                                              NOT NULL,
    created_at    TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at    TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL
);

COMMENT ON COLUMN "videos".video_id IS 'Video id scratched from youtube';
COMMENT ON COLUMN "videos".possible_idea IS 'Possible idea suggested by youtube';
COMMENT ON COLUMN "videos".summary IS 'Summary of the video';
COMMENT ON COLUMN "videos".tags IS 'Tags of the video';
COMMENT ON COLUMN "videos".possible_tags IS 'Possible tags';