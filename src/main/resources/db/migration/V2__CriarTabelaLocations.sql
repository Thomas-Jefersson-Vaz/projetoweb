ALTER TABLE users ADD COLUMN IF NOT EXISTS cidade VARCHAR;
ALTER TABLE users ADD COLUMN IF NOT EXISTS bio TEXT;
ALTER TABLE users ADD COLUMN IF NOT EXISTS assento VARCHAR;
ALTER TABLE users ADD COLUMN IF NOT EXISTS comida VARCHAR;
ALTER TABLE users ADD COLUMN IF NOT EXISTS classe VARCHAR;
ALTER TABLE users ADD COLUMN IF NOT EXISTS moeda VARCHAR;

CREATE TABLE IF NOT EXISTS locations (
    id              SERIAL      PRIMARY KEY,
    name            VARCHAR     NOT NULL,
    country         VARCHAR     NOT NULL,
    continent       VARCHAR     NOT NULL,
    description     TEXT,
    price           NUMERIC(10,2),
    image_url       VARCHAR,
    start_date      DATE,
    end_date        DATE
);

CREATE TABLE IF NOT EXISTS bookings (
    id              SERIAL      PRIMARY KEY,
    user_id         INT         NOT NULL REFERENCES users(id),
    location_id     INT         NOT NULL REFERENCES locations(id),
    booked_at       TIMESTAMP   DEFAULT NOW()
);
