CREATE DATABASE tinderzoo_dev;

CREATE USER 'tinderzoo_dev_user'@'localhost' IDENTIFIED BY 'password';
CREATE USER 'tinderzoo_dev_user'@'%' IDENTIFIED BY 'password';



GRANT SELECT ON tinderzoo_dev.* to 'tinderzoo_dev_user'@'localhost';
GRANT INSERT ON tinderzoo_dev.* to 'tinderzoo_dev_user'@'localhost';
GRANT DELETE ON tinderzoo_dev.* to 'tinderzoo_dev_user'@'localhost';
GRANT UPDATE ON tinderzoo_dev.* to 'tinderzoo_dev_user'@'localhost';
GRANT SELECT ON tinderzoo_dev.* to 'tinderzoo_dev_user'@'%';
GRANT INSERT ON tinderzoo_dev.* to 'tinderzoo_dev_user'@'%';
GRANT DELETE ON tinderzoo_dev.* to 'tinderzoo_dev_user'@'%';
GRANT UPDATE ON tinderzoo_dev.* to 'tinderzoo_dev_user'@'%';


CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `specie` varchar(50) NOT NULL,
  `breed` varchar(50) NOT NULL,
  `age` int NOT NULL,
  `country` varchar(50) NOT NULL,
  `city` varchar(50) NOT NULL,
  `height` int NOT NULL,
  `gender` VARCHAR(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `profile_pictures` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int UNIQUE NOT NULL,
  `image` longblob NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
