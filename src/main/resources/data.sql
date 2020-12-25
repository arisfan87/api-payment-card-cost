DROP TABLE IF EXISTS card_cost;

CREATE TABLE card_cost (
country VARCHAR(2) PRIMARY KEY NOT NULL,
cost INTEGER NOT NULL
);

INSERT INTO card_cost (country, cost) VALUES
('US', 5),
('GR', 15)