DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`(
                     `id` INT PRIMARY KEY,
                     `email` VARCHAR(30),
                     `name` VARCHAR(30),
                     `tel` VARCHAR(30)
);

DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
                         `no` VARCHAR(5),
                         `name` VARCHAR(50)
);