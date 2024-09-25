DROP TABLE IF EXISTS MEMBERS;
CREATE TABLE MEMBERS
(
    member_id    INT          NOT NULL AUTO_INCREMENT,
    name         VARCHAR(100) NOT NULL,
    address      VARCHAR(100) NOT NULL,
    phone_number VARCHAR(100) NOT NULL,
    PRIMARY KEY (member_id)
);

DROP TABLE IF EXISTS PRODUCTS;
CREATE TABLE PRODUCTS
(
    product_id  INT          NOT NULL AUTO_INCREMENT,
    name        VARCHAR(100) NOT NULL,
    description VARCHAR(200) NULL,
    quantity    INT          NOT NULL,
    PRIMARY KEY (product_id)
);

DROP TABLE IF EXISTS ORDERS;
CREATE TABLE ORDERS
(
    order_id   INT      NOT NULL AUTO_INCREMENT,
    member_id  INT      NOT NULL,
    order_date DATETIME NOT NULL,
    PRIMARY KEY (order_id)
);

DROP TABLE IF EXISTS ORDER_ITEMS;
CREATE TABLE ORDER_ITEMS
(
    order_item_id  INT NOT NULL AUTO_INCREMENT,
    product_id     INT NOT NULL,
    order_id       INT NOT NULL,
    order_quantity INT NOT NULL,
    PRIMARY KEY (order_item_id),
    FOREIGN KEY (order_id) REFERENCES orders (order_id)
);

