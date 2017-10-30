
CREATE TABLE users (
  id       INT          AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(255),
  password VARCHAR(255),
  name VARCHAR(255),
  enabled BOOLEAN
)
  ENGINE = InnoDB;


CREATE TABLE roles (
  id   INT           AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100)
)
  ENGINE = InnoDB;


CREATE TABLE user_roles (
  user_id INT,
  role_id INT,

  FOREIGN KEY (user_id) REFERENCES users (id),
  FOREIGN KEY (role_id) REFERENCES roles (id),

  UNIQUE (user_id, role_id)
)
  ENGINE = InnoDB;



INSERT INTO users VALUES (1, 'Admim', '$2a$11$uSXS6rLJ91WjgOHhEGDx..VGs7MkKZV68Lv5r1uwFu7HgtRn3dcXG', 'Admin', TRUE );

INSERT INTO roles VALUES (1, 'ROLE_USER');
INSERT INTO roles VALUES (2, 'ROLE_ADMIN');

INSERT INTO user_roles VALUES (1, 2);

CREATE TABLE images (
  id       INT           AUTO_INCREMENT PRIMARY KEY,
  url VARCHAR(255) ,
  urlOriginal VARCHAR(255) NOT NULL
)
  ENGINE = InnoDB;
CREATE TABLE instructions(
  id  INT           AUTO_INCREMENT PRIMARY KEY,
  content VARCHAR(10000)
);



CREATE TABLE users (
  id INT  AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(255) ,
  password VARCHAR(255) ,
  name VARCHAR(255) ,
  enabled BOOLEAN
)
  ENGINE = InnoDB;


CREATE TABLE roles (
  id INT  AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100)
)
  ENGINE = InnoDB;


CREATE TABLE user_roles (
  user_id INT ,
  role_id INT ,

  FOREIGN KEY (user_id) REFERENCES users (id),
  FOREIGN KEY (role_id) REFERENCES roles (id),

  UNIQUE (user_id, role_id)
)
  ENGINE = InnoDB;



INSERT INTO users VALUES (1, 'admin', '$2a$11$uSXS6rLJ91WjgOHhEGDx..VGs7MkKZV68Lv5r1uwFu7HgtRn3dcXG', 'Emil', TRUE );

INSERT INTO roles VALUES (1, 'ROLE_USER');
INSERT INTO roles VALUES (2, 'ROLE_ADMIN');

INSERT INTO user_roles VALUES (1, 2);

CREATE TABLE images (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  url VARCHAR(255) ,
  urlOriginal VARCHAR(255)
);

CREATE TABLE instructions(
  id INT  AUTO_INCREMENT PRIMARY KEY,
  content VARCHAR(10000)
);
INSERT INTO instructions VALUES(1,'kjdsgsjkgfkjds');