INSERT INTO product (id, name, stock_quantity, price, version, last_updated) VALUES (1, 'Premium Widget', 100, 29.99, 0, CURRENT_TIMESTAMP) ON CONFLICT (id) DO NOTHING;
INSERT INTO product (id, name, stock_quantity, price, version, last_updated) VALUES (2, 'Super Gadget', 50, 49.99, 0, CURRENT_TIMESTAMP) ON CONFLICT (id) DO NOTHING;
