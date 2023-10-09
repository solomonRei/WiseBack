CREATE TABLE IF NOT EXISTS "statistics_country"
(
    id           VARCHAR(255) PRIMARY KEY,
    country_code VARCHAR(255) CHECK
        (country_code IN ('OTHERS', 'RU', 'MD', 'US', 'RO', 'UA', 'UK', 'DE', 'PL')) DEFAULT 'OTHERS'          NOT NULL,
    views        INTEGER                                                             DEFAULT 0                 NOT NULL,
    comments     INTEGER                                                             DEFAULT 0                 NOT NULL,
    likes        INTEGER                                                             DEFAULT 0                 NOT NULL,
    start_date   TIMESTAMP WITH TIME ZONE                                                                      NOT NULL,
    end_date     TIMESTAMP WITH TIME ZONE                                                                      NOT NULL,
    created_at   TIMESTAMP WITH TIME ZONE                                            DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at   TIMESTAMP WITH TIME ZONE                                            DEFAULT CURRENT_TIMESTAMP NOT NULL,
    statistic_id VARCHAR(255)                                                                                  NOT NULL REFERENCES statistics (id)
);

COMMENT ON COLUMN statistics_country.id IS 'This is the ID of the statistics country so that the statistics can reference it';
COMMENT ON COLUMN statistics_country.country_code IS 'This is the country code';
COMMENT ON COLUMN statistics_country.views IS 'This is the views count to each country';
COMMENT ON COLUMN statistics_country.comments IS 'This is the comments count to each country';
COMMENT ON COLUMN statistics_country.likes IS 'This is the likes count to each country';
COMMENT ON COLUMN statistics_country.start_date IS 'From this date was obtained statistics by country';
COMMENT ON COLUMN statistics_country.end_date IS 'To this date was obtained statistics by country';