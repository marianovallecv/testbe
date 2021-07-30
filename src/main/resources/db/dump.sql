/*----------------------------------------------------------------------------------------------------------------------------------
DDL
----------------------------------------------------------------------------------------------------------------------------------*/
CREATE DATABASE `test` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

CREATE TABLE `user_roles` (
  `id` int NOT NULL AUTO_INCREMENT,
  `role_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `password` varchar(60) NOT NULL,
  `upd_pass` bit(1) NOT NULL,
  `username` varchar(15) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_r43af9ap4edm43mmtq01oddj6` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `users_roles` (
  `users_id` int NOT NULL,
  `roles_id` int NOT NULL,
  PRIMARY KEY (`users_id`,`roles_id`),
  KEY `FKr7n16jrocdrem7nca7dasjf12` (`roles_id`),
  CONSTRAINT `FKml90kef4w2jy7oxyqv742tsfc` FOREIGN KEY (`users_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKr7n16jrocdrem7nca7dasjf12` FOREIGN KEY (`roles_id`) REFERENCES `user_roles` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `candidates` (
  `id` int NOT NULL AUTO_INCREMENT,
  `address` varchar(50) DEFAULT NULL,
  `birth` datetime(6) NOT NULL,
  `document` int NOT NULL,
  `email` varchar(100) DEFAULT NULL,
  `full_name` varchar(20) NOT NULL,
  `phone` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_nklvmx5udv7ld8d38eq94kqy` (`document`),
  UNIQUE KEY `UK_nm2ss73jii2hdupmpphl6agry` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*----------------------------------------------------------------------------------------------------------------------------------
DDL
----------------------------------------------------------------------------------------------------------------------------------*/



/*----------------------------------------------------------------------------------------------------------------------------------
DML
----------------------------------------------------------------------------------------------------------------------------------*/
-- ROLES
INSERT INTO `test`.user_roles (`id`, `role_name`)
VALUES
(1, 'ROLE_ADMIN'),
(2, 'ROLE_USER');

-- USERS
INSERT INTO `test`.`users` (`password`, `upd_pass`, `username`) 
VALUES
('$2a$10$wNM60OnTdhokGKygfnIuC.MsTEIxRdG8dLK1HJXQN4Qk08VSptqvu', 0, 'admin'),
('$2a$10$wNM60OnTdhokGKygfnIuC.MsTEIxRdG8dLK1HJXQN4Qk08VSptqvu', 0, 'user'),
('$2a$10$wNM60OnTdhokGKygfnIuC.MsTEIxRdG8dLK1HJXQN4Qk08VSptqvu', 0, 'guest');

-- USERS_ROLES
INSERT INTO `test`.`users_roles` (`users_id`, `roles_id`) 
VALUES
(1, 1),
(2, 2);

-- CANDIDATES
INSERT INTO `test`.`candidates`
(`address`, `birth`, `document`, `email`, `full_name`, `phone`)
VALUES
('Dirección 1', CURDATE(), '11111111', 'candidato1@mail.com','candidato UNO', '111111'),
('Dirección 2', CURDATE(), '22222222', 'candidato2@mail.com','candidato DOS', '222222');

/*----------------------------------------------------------------------------------------------------------------------------------
DML
----------------------------------------------------------------------------------------------------------------------------------*/