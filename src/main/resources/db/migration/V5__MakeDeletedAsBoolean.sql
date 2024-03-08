ALTER TABLE category
    DROP COLUMN is_deleted;

ALTER TABLE category
    ADD is_deleted BIT(1) NOT NULL;

ALTER TABLE category
    MODIFY is_deleted BIT(1) NOT NULL;

ALTER TABLE product
    DROP COLUMN is_deleted;

ALTER TABLE product
    ADD is_deleted BIT(1) NOT NULL;

ALTER TABLE product
    MODIFY is_deleted BIT(1) NOT NULL;