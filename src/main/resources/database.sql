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



INSERT INTO users VALUES (1, 'admin', '$2a$11$uSXS6rLJ91WjgOHhEGDx..VGs7MkKZV68Lv5r1uwFu7HgtRn3dcXG', 'Admin', TRUE);

INSERT INTO roles VALUES (1, 'ROLE_USER');
INSERT INTO roles VALUES (2, 'ROLE_ADMIN');

INSERT INTO user_roles VALUES (1, 2);

CREATE TABLE images (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  url VARCHAR(255) ,
  urlOriginal VARCHAR(255)
);

CREATE TABLE instructions(
  instructionsId INT  AUTO_INCREMENT PRIMARY KEY,
  heading VARCHAR(200)default NULL ,
  content VARCHAR(10000)default NULL,
  owner_id INT,
  rating DOUBLE
);

CREATE TABLE instTags(
  instructionsId INT,
  tagsId INT
);

CREATE TABLE step(
  id INT AUTO_INCREMENT PRIMARY KEY,
  heading VARCHAR(200),
  content VARCHAR(10000),
  instructions_id INT,
  number INT
);
INSERT INTO instructions VALUES(1,'Head','Content',1);

CREATE TABLE rating(
  id INT AUTO_INCREMENT PRIMARY KEY,
  instr_id INT,
  user_id INT,
  mark INT
);

CREATE TABLE likes(
  id INT AUTO_INCREMENT PRIMARY KEY,
  comment_id INT,
  user_id INT,
);

CREATE TABLE tags(
  id INT AUTO_INCREMENT PRIMARY KEY,
  tag VARCHAR(100)
);
INSERT INTO tags VALUES (1, 'Transport');
INSERT INTO tags VALUES (2, 'Equipment');
INSERT INTO tags VALUES (3, 'Cooking');
INSERT INTO tags VALUES (4, 'Animals');
INSERT INTO tags VALUES (5, 'Mine');
INSERT INTO tags VALUES (6, 'Business')