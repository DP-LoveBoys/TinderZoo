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


CREATE TABLE `user_data` (
  `id` int NOT NULL AUTO_INCREMENT,
  `specie` varchar(50) NOT NULL,
  `breed` varchar(50) NOT NULL,
  `age` int NOT NULL,
  `country` varchar(50) NOT NULL,
  `city` varchar(50) NOT NULL,
  `height` int NOT NULL,
  `gender` VARCHAR(1) NOT NULL,
  `eye_color` VARCHAR(10) NOT NULL,
  `description` VARCHAR(1000) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `photos` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `image` longblob NOT NULL,
  `image_type` varchar(15) NOT NULL,
  `profile` tinyint NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `credentials` (
  `id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(50) NOT NULL,
  `name` varchar(50) NOT NULL,
  `password` varchar(256) NOT NULL,
  `authentication_provider` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE interests(
`id` int NOT NULL AUTO_INCREMENT,
`user_id` int NOT NULL,
`interest_tag` VARCHAR(25) NOT NULL,
PRIMARY KEY (`id`)
);

CREATE TABLE `preferences` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `close_age` tinyint NOT NULL,
  `same_breed` tinyint NOT NULL,
  `nearby` tinyint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id_UNIQUE` (`user_id`),
  KEY `user_fk_idx` (`user_id`),
  CONSTRAINT `user_fk` FOREIGN KEY (`user_id`) REFERENCES `credentials` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
