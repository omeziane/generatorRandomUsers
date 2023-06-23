DROP TABLE IF EXISTS usr;
CREATE TABLE usr (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(250) NOT NULL,
  login VARCHAR(250) NOT NULL,
  password VARCHAR(250) NOT NULL
);

DROP TABLE IF EXISTS usr_users;
CREATE TABLE usr_users (
                      id INT AUTO_INCREMENT PRIMARY KEY,
                      firstName VARCHAR(255),
                      lastName VARCHAR(255),
                      birthDate TIMESTAMP,
                      city VARCHAR(255),
                      country VARCHAR(255),
                      avatar VARCHAR(255),
                      company VARCHAR(255),
                      jobPosition VARCHAR(255),
                      mobile VARCHAR(255),
                      username VARCHAR(255),
                      email VARCHAR(255),
                      password VARCHAR(255),
                      role VARCHAR(255)
);

-- Password 123 (md5)
INSERT INTO usr (name, login, password) VALUES ('name', 'user', '202cb962ac59075b964b07152d234b70');
