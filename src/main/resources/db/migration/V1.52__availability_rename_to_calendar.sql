ALTER TABLE availability RENAME TO calendar;

ALTER TABLE calendar ADD COLUMN "price" decimal;
ALTER TABLE calendar ADD COLUMN "min_los" int;
ALTER TABLE calendar ADD COLUMN "max_los" int;
ALTER TABLE calendar ADD COLUMN "closed_for_sale" boolean;
ALTER TABLE calendar ADD COLUMN "closed_for_arrival" boolean;
ALTER TABLE calendar ADD COLUMN "closed_for_departure" boolean;
