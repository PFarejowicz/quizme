USE c_cs108_pfarejow;
  
SET @@auto_increment_increment=1;

DROP TABLE IF EXISTS users;
CREATE TABLE users (
	user_id INT UNSIGNED AUTO_INCREMENT,
    email VARCHAR(255),
    password VARCHAR(255),
    salt VARCHAR(255),
    name VARCHAR(255),
    admin_privilege BOOL,
    PRIMARY KEY (user_id)
);

DROP TABLE IF EXISTS quizzes;
CREATE TABLE quizzes (
	quiz_id INT UNSIGNED AUTO_INCREMENT,
    name VARCHAR(255),
    description VARCHAR(255),
    author_id INT,
    random_order BOOL,
    multiple_pages BOOL,
    immediate_correction BOOL,
    PRIMARY KEY (quiz_id)
);

DROP TABLE IF EXISTS questions;
CREATE TABLE questions (
  question_id INT UNSIGNED AUTO_INCREMENT,
  quiz_id INT,
  question_type enum('QuestionResponse','FillInTheBlank','MultipleChoice','PictureResponse'),
  metadata LONGBLOB,
  PRIMARY KEY (question_id)
);

DROP TABLE IF EXISTS quiz_history;
CREATE TABLE quiz_history (
	quiz_id INT,
	user_id INT,
	score INT
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
	data VARCHAR(255)
);

DROP TABLE IF EXISTS announcements;
CREATE TABLE announcements (
	announcement_id INT UNSIGNED AUTO_INCREMENT,
	description VARCHAR(255),
	PRIMARY KEY (announcement_id)
);