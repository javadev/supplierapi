CREATE TABLE "supplier"
(
    "id"          SERIAL PRIMARY KEY,
    "name"        varchar NOT NULL,
    "password"    varchar NOT NULL,
    "is_active"   boolean NOT NULL,
    "last_update" timestamp DEFAULT (current_timestamp),
    UNIQUE ("name")
);

CREATE TABLE "supplier_role"
(
    "id"          SERIAL PRIMARY KEY,
    "supplier_id" int     NOT NULL,
    "role_name"   varchar NOT NULL,
    "last_update" timestamp DEFAULT (current_timestamp),
    UNIQUE ("supplier_id", "role_name")
);

ALTER TABLE "supplier_role"
    ADD FOREIGN KEY ("supplier_id") REFERENCES "supplier" ("id");
