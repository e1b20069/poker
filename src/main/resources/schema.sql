CREATE TABLE player (
  id IDENTITY,
  playername VARCHAR NOT NULL,
  handname VARCHAR,
  score INT
);
CREATE TABLE poker4 (
    id IDENTITY,
    num INT NOT NULL,
    suit VARCHAR NOT NULL
);

CREATE TABLE score (
    id IDENTITY,
    hand VARCHAR NOT NULL,
    score INT NOT NULL
);

CREATE TABLE sdeck (
    id IDENTITY,
    num INT NOT NULL
);
