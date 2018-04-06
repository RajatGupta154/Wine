DROP DATABASE IF EXISTS winegalery;
CREATE DATABASE winegalery;
USE winegalery;

DROP TABLE IF EXISTS wine;
CREATE TABLE wine (
  wineid int NOT NULL AUTO_INCREMENT,
  name varchar(255) NOT NULL,
  brand varchar(255) NOT NULL,
  stock int DEFAULT 0,
  price double NOT NULL,
  PRIMARY KEY (wineid)
);

INSERT INTO wine
(name, brand, stock, price)
VALUES
  ('Concha y toro', 'Frontera', 34, 100.45),
  ('Ice wine', 'Peller', 12, 245.78),
  ('Riesling', 'LDR', 5, 945.78);

ALTER TABLE wine
  ADD type varchar(255);

ALTER TABLE wine
  ADD countryOfOrigin varchar(255);

ALTER TABLE wine
  ADD yearOfProduction int(11);

ALTER TABLE wine
  ADD volume double;

ALTER TABLE wine
  ADD description varchar(255);

ALTER TABLE wine
  ADD recommendations varchar(255);


ALTER TABLE wine
  ADD image varchar(255);

DROP TABLE IF EXISTS `winegalery`.`users`;
CREATE TABLE `winegalery`.`users` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  `password` VARCHAR(45) NULL,
  `email` VARCHAR(45) NULL,
  `phoneNumber` BIGINT NULL,
  `isActive`  TINYINT( 3 ) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
);


INSERT INTO `winegalery`.`users` (`name`, `password`, `email`, `phoneNumber`) VALUES ('user1', 'pass1', 'email1@gmail.com', '0740123456');
INSERT INTO `winegalery`.`users` (`name`, `password`, `email`, `phoneNumber`) VALUES ('user2', 'pass2', 'email2@gmail.com', '0740123123');
INSERT INTO `winegalery`.`users` (`name`, `password`, `email`, `phoneNumber`) VALUES ('user3', 'pass3', 'email3@gmail.com', '0740456123');

UPDATE `winegalery`.`wine` SET `type`='red', `countryOfOrigin`='Romania', `yearOfProduction`='2003', `volume`='1.2', `description`='des1', `recommendations`='rec1', `image`='img1' WHERE `wineid`='1';
UPDATE `winegalery`.`wine` SET `type`='rose', `countryOfOrigin`='Italy', `yearOfProduction`='1996', `volume`='1', `description`='des2', `recommendations`='rec2', `image`='img3' WHERE `wineid`='2';
UPDATE `winegalery`.`wine` SET `type`='red', `countryOfOrigin`='Spain', `yearOfProduction`='1568', `volume`='1.1', `description`='des2', `recommendations`='rec3', `image`='img2' WHERE `wineid`='3';


CREATE TABLE categories (
  id int NOT NULL AUTO_INCREMENT,
  name varchar(255) NOT NULL,
  PRIMARY KEY (id)
);

INSERT INTO categories
(id, name)
VALUES
  (1, "Basic"),
  (2, "Premium");

ALTER TABLE wine
  ADD category int(11);


UPDATE `winegalery`.`wine` SET `category`= 1 WHERE `wineid`='1';
UPDATE `winegalery`.`wine` SET `category`= 1 WHERE `wineid`='2';
UPDATE `winegalery`.`wine` SET `category`= 2 WHERE `wineid`='3';

DROP TABLE IF EXISTS cart;
CREATE TABLE cart (
  cartId INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  totalCost DOUBLE
);

DROP TABLE IF EXISTS cartEntry;
CREATE TABLE cartEntry (
  entryId INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  cartId INT,
  wineId INT,
  FOREIGN KEY (cartId)
  REFERENCES cart(cartId)
    ON DELETE CASCADE,
  FOREIGN KEY wine(wineId)
  REFERENCES wine(wineId)
    ON DELETE CASCADE,
  quantity INT
);

ALTER TABLE wine
  DROP COLUMN image;

ALTER TABLE wine
  ADD picture varchar(255);

UPDATE `winegalery`.`wine` SET `picture`='D:/upload-directory-pictures/aff61dec-ebda-4b34-a470-e1828f0d1590.jpg' WHERE `wineid`='1';
UPDATE `winegalery`.`wine` SET `picture`='D:/upload-directory-pictures/aff61dec-ebda-4b34-a470-e1828f0d1590.jpg' WHERE `wineid`='2';
UPDATE `winegalery`.`wine` SET `picture`='D:/upload-directory-pictures/aff61dec-ebda-4b34-a470-e1828f0d1590.jpg' WHERE `wineid`='3';

CREATE TABLE reviews (
	id INT NOT NULL AUTO_INCREMENT,
    productId INT NOT NULL,
    rating INT,
    review varchar(255),
    PRIMARY KEY (id)
);

INSERT INTO reviews (productId, rating, review) VALUES (1, 3, "foarte foarte bun");