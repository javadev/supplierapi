ALTER TABLE product ADD COLUMN property_id int NOT NULL;

ALTER TABLE "product"
    ADD FOREIGN KEY ("property_id") REFERENCES "property" ("id");
