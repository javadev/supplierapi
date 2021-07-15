CREATE TABLE "currency"
(
    "id"           SERIAL PRIMARY KEY,
    "code"         varchar NOT NULL,
    "name"         varchar NOT NULL,
    "numeric_code" int     NOT NULL,
    "minor_unit"   smallint,
    "last_update"  timestamp DEFAULT (current_timestamp)
);

ALTER TABLE "property"
    ADD FOREIGN KEY ("home_currency_id") REFERENCES "currency" ("id");
