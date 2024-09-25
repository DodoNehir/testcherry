DROP TABLE IF EXISTS members;
CREATE TABLE members
(
    member_id    INT          NOT NULL AUTO_INCREMENT,
    name         VARCHAR(100) NOT NULL,
    address      VARCHAR(100) NOT NULL,
    phone_number VARCHAR(100) NOT NULL,
    PRIMARY KEY (member_id)
);

DROP TABLE IF EXISTS products;
CREATE TABLE products
(
    product_id  INT          NOT NULL AUTO_INCREMENT,
    name        VARCHAR(100) NOT NULL,
    description VARCHAR(200) NULL,
    quantity    INT          NOT NULL,
    PRIMARY KEY (product_id)
);

DROP TABLE IF EXISTS orders;
CREATE TABLE orders
(
    order_id   INT      NOT NULL AUTO_INCREMENT,
    member_id  INT      NOT NULL,
    order_date DATETIME NOT NULL,
    PRIMARY KEY (order_id)
);

DROP TABLE IF EXISTS order_items;
CREATE TABLE order_items
(
    order_item_id  INT NOT NULL AUTO_INCREMENT,
    product_id     INT NOT NULL,
    order_id       INT NOT NULL,
    order_quantity INT NOT NULL,
    PRIMARY KEY (order_item_id),
    FOREIGN KEY (order_id) REFERENCES orders (order_id)
);

