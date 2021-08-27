CREATE TABLE "phone"
(
    "id"            SERIAL PRIMARY KEY,
    "phone_number"  varchar NOT NULL,
    "extension"     varchar,
    "phone_type_id" int,
    "last_update"   timestamp DEFAULT (current_timestamp)
);

CREATE TABLE "phone_type"
(
    "id"          SERIAL PRIMARY KEY,
    "code"        varchar,
    "name"        varchar,
    "last_update" timestamp DEFAULT (current_timestamp)
);

CREATE TABLE "email"
(
    "id"            SERIAL PRIMARY KEY,
    "email"         varchar NOT NULL,
    "email_type_id" int,
    "last_update"   timestamp DEFAULT (current_timestamp)
);

CREATE TABLE "email_type"
(
    "id"          SERIAL PRIMARY KEY,
    "code"        varchar,
    "name"        varchar,
    "last_update" timestamp DEFAULT (current_timestamp)
);

CREATE TABLE "property_x_email"
(
    "property_id" int NOT NULL,
    "email_id"    int NOT NULL,
    "last_update" timestamp DEFAULT (current_timestamp)
);

CREATE TABLE "property_x_phone"
(
    "property_id" int NOT NULL,
    "phone_id"    int NOT NULL,
    "last_update" timestamp DEFAULT (current_timestamp)
);

ALTER TABLE "phone"
    ADD FOREIGN KEY ("phone_type_id") REFERENCES "phone_type" ("id");

ALTER TABLE "email"
    ADD FOREIGN KEY ("email_type_id") REFERENCES "email_type" ("id");

ALTER TABLE "property_x_phone"
    ADD FOREIGN KEY ("phone_id") REFERENCES "phone" ("id");

ALTER TABLE "property_x_email"
    ADD FOREIGN KEY ("email_id") REFERENCES "email" ("id");

ALTER TABLE "property_x_phone"
    ADD FOREIGN KEY ("property_id") REFERENCES "property" ("id");

ALTER TABLE "property_x_email"
    ADD FOREIGN KEY ("property_id") REFERENCES "property" ("id");
