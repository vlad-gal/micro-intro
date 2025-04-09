CREATE TABLE IF NOT EXISTS SONG (
    id BIGINT PRIMARY KEY,
    name VARCHAR(255),
    artist VARCHAR(255),
    album VARCHAR(255),
    duration VARCHAR(50),
    year VARCHAR(10)
);