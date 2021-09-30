CREATE TABLE "product"
(
    "id"          SERIAL PRIMARY KEY,
    "name"        varchar,
    "property_id" int NOT NULL,
    "is_visible"  boolean,
    "last_update" timestamp
);

CREATE TABLE "product_x_description"
(
    "product_id"     int NOT NULL,
    "description_id" int NOT NULL,
    "last_update"    timestamp
);

CREATE TABLE "product_x_sellable_unit"
(
    "id"                    SERIAL PRIMARY KEY,
    "product_id"            int NOT NULL,
    "sellable_unit_id"      int NOT NULL,
    "quantity"              int,
    "consecutive_over_time" boolean,
    "last_update"           timestamp DEFAULT (current_timestamp)
);

CREATE TABLE "smart_contract"
(
    "id"               SERIAL PRIMARY KEY,
    "product_id"       int,
    "rate_plan_id"     int,
    "pricing_model_id" int,
    "last_update"      timestamp
);

ALTER TABLE "product_x_sellable_unit"
    ADD FOREIGN KEY ("sellable_unit_id") REFERENCES "sellable_unit" ("id");

ALTER TABLE "product_x_sellable_unit"
    ADD FOREIGN KEY ("product_id") REFERENCES "product" ("id");

ALTER TABLE "product"
    ADD FOREIGN KEY ("property_id") REFERENCES "property" ("id");

ALTER TABLE "product_x_description"
    ADD FOREIGN KEY ("product_id") REFERENCES "product" ("id");

ALTER TABLE "product_x_description"
    ADD FOREIGN KEY ("description_id") REFERENCES "description" ("id");

ALTER TABLE "smart_contract"
    ADD FOREIGN KEY ("product_id") REFERENCES "product" ("id");
