ALTER TABLE product RENAME TO basket;

ALTER TABLE product_x_description RENAME TO basket_x_description;

ALTER TABLE product_x_sellable_unit RENAME TO basket_x_sellable_unit;


ALTER TABLE basket ALTER COLUMN last_update SET DEFAULT (current_timestamp);
ALTER TABLE basket_x_description ALTER COLUMN last_update SET DEFAULT (current_timestamp);


ALTER TABLE basket_x_description RENAME COLUMN "product_id" TO "basket_id";
ALTER TABLE basket_x_sellable_unit RENAME COLUMN "product_id" TO "basket_id";
ALTER TABLE smart_contract RENAME COLUMN "product_id" TO "basket_id";
