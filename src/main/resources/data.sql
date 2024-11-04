-- members
INSERT INTO MEMBERS(member_id, username, password, address, phone_number, active)
VALUES (1, 'qwerty', '$2a$10$NKps6HDOWCQ3AO6S6tTXoO9epU6J34ZLxnxlimEWGLcpJhJZQXdSy', 'Seoul',
        '010-1111-1111', true);
INSERT INTO MEMBERS(member_id, username, password, address, phone_number, active)
VALUES (2, 'asdf', '$2a$10$DNKX.ZXw6DqmKRTOVDW1CenrWZ7gMHFVB0/sacEBrP28N3IHV8tz6', 'Seoul',
        '010-2222-2222', true);

-- products
INSERT INTO PRODUCTS(product_id, name, description, quantity)
VALUES (1, 'Red velvet cake', 'Android 11', 100);
INSERT INTO PRODUCTS(product_id, name, description, quantity)
VALUES (2, 'Snow cone', 'Android 12', 100);
INSERT INTO PRODUCTS(product_id, name, description, quantity)
VALUES (3, 'Tiramisu', 'Android 13', 100);
INSERT INTO PRODUCTS(product_id, name, description, quantity)
VALUES (4, 'Upside down cake', 'Android 14', 100);
INSERT INTO PRODUCTS(product_id, name, description, quantity)
VALUES (5, 'Vanilla ice cream', 'Android 15', 100);