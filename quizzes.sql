USE c_cs108_pfarejow;
  
DROP TABLE IF EXISTS users;
 -- remove table if it already exists and start from scratch

CREATE TABLE users (
	user_id INT UNSIGNED AUTO_INCREMENT,
    email VARCHAR(255),
    password VARCHAR(255),
    salt VARCHAR(255),
    name VARCHAR(255),
    admin_privilege BOOL,
    PRIMARY KEY (user_id)
);

DROP TABLE IF EXISTS admins;

CREATE TABLE admins (
	user_id INT UNSIGNED AUTO_INCREMENT,
    email VARCHAR(255),
    password VARCHAR(255),
    salt VARCHAR(255)
    name VARCHAR(255),
    admin_privilege BOOL,
    PRIMARY KEY (user_id)
);

DROP TABLE IF EXISTS quizzes;

CREATE TABLE quizzes (
	quiz_id INT UNSIGNED AUTO_INCREMENT,
    name VARCHAR(255),
    description VARCHAR(255),
    user_id INT,
    random_order BOOL,
    multiple_pages BOOL,
    auto_correction BOOL,
    PRIMARY KEY (quiz_id)
);

DROP TABLE IF EXISTS friends;

CREATE TABLE friends (
	user_id_1 INT UNSIGNED,
	user_id_2 INT UNSIGNED
);

DROP TABLE IF EXISTS friend_requests;

CREATE TABLE friend_requests (
	from_user_id INT UNSIGNED,
	to_user_id INT UNSIGNED
);

DROP TABLE IF EXISTS messages;

CREATE TABLE messages (
	sender_id INT UNSIGNED,
	receiver_id INT UNSIGNED,
	data VARCHAR(255),
);