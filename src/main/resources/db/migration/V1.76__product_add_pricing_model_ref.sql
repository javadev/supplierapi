CREATE TABLE "product_x_time_range"
(
    "product_id"    int NOT NULL,
    "time_range_id" int NOT NULL,
    "last_update"   timestamp DEFAULT (current_timestamp)
);

ALTER TABLE "product"
    ADD FOREIGN KEY ("pricing_model_id") REFERENCES "pricing_model" ("id");

ALTER TABLE "product_x_time_range"
    ADD FOREIGN KEY ("time_range_id") REFERENCES "time_range" ("id");

ALTER TABLE "product_x_time_range"
    ADD FOREIGN KEY ("product_id") REFERENCES "product" ("id");
