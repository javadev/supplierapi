ALTER TABLE property_info DROP COLUMN exists_code;

ALTER TABLE property_info ADD COLUMN is_exist boolean;
