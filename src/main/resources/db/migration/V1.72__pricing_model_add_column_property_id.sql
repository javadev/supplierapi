ALTER TABLE pricing_model ADD COLUMN property_id int NOT NULL;

ALTER TABLE "pricing_model"
    ADD FOREIGN KEY ("property_id") REFERENCES "property" ("id");
