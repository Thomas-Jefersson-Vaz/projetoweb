CREATE DATABASE projetoweb;
USE projetoweb;

CREATE TABLE IF NOT EXISTS users (
    -- essentials
    id              SERIAL PRIMARY KEY,
    email           VARCHAR(100) UNIQUE NOT NULL,
    password        VARCHAR(255) NOT NULL,
    name            VARCHAR(100),
    role            VARCHAR(50),

    -- more
    data_nascimento DATE,
    nacionalidade   VARCHAR(100),
    num_telefone    VARCHAR(20),
    cidade          VARCHAR(100),
    bio             TEXT,

    -- preferences
    assento         VARCHAR(50),
    comida          VARCHAR(50),
    classe          VARCHAR(50),
    moeda           VARCHAR(10)
);


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

GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA public TO projetoweb;

GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA public TO projetoweb;

INSERT INTO users (name, email, password, role) 
VALUES ('Admin', 'admin@admin.com', 'admin', 'admin');