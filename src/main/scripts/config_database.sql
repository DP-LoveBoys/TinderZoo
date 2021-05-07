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