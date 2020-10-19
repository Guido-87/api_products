DROP TABLE IF EXISTS Products;

CREATE TABLE IF NOT EXISTS Products (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(250) NOT NULL,
	category VARCHAR(250) NOT NULL,
	retail_price DOUBLE NOT NULL,
	discounted_price DOUBLE NOT NULL,
	availability BOOLEAN NOT NULL
	);
	
--INSERT INTO Products (id, name, category, retail_price, discounted_price, availability) VALUES
--	(0, 'Papas', 'Comida', '150', '10', true),
--	(1, 'Coca', 'Gaseosa', '200', '20', false),
--	(2, 'Torta', 'Comida', '150', '10', true),
--	(3, 'Pepsi', 'Gaseosa', '150', '10', false),
--	(4, 'Sprite', 'Gaseosa', '100', '10', false);