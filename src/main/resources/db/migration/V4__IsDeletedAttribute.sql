ALTER TABLE category
    ADD is_deleted VARCHAR(255) NULL;

ALTER TABLE product
    ADD is_deleted VARCHAR(255) NULL;