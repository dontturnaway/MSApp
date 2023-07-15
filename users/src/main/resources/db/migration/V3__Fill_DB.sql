ALTER TABLE dev.person
ADD COLUMN date_created timestamp;

UPDATE dev.person
SET date_created=Current_timestamp
WHERE date_created IS NULL;
