USE c_cs108_pfarejow;
  
SET @@auto_increment_increment=1;

CREATE TABLE IF NOT EXISTS users (
	user_id INT UNSIGNED AUTO_INCREMENT,
    email VARCHAR(255),
    password VARCHAR(255),
    salt VARCHAR(255),
    name VARCHAR(255),
    admin_privilege BOOL,
    PRIMARY KEY (user_id)
);

CREATE TABLE IF NOT EXISTS quizzes (
	quiz_id INT UNSIGNED AUTO_INCREMENT,
    name VARCHAR(255),
    description VARCHAR(255),
    author_id INT,
    random_order BOOL,
    multiple_pages BOOL,
    immediate_correction BOOL,
    date_time DATETIME,
    points INT,
    PRIMARY KEY (quiz_id)
);

CREATE TABLE IF NOT EXISTS questions (
	question_id INT UNSIGNED AUTO_INCREMENT,
	quiz_id INT,
	question_type enum('QuestionResponse','FillInTheBlank','MultipleChoice','PictureResponse'),
	metadata LONGBLOB,
	PRIMARY KEY (question_id)
);

CREATE TABLE IF NOT EXISTS quiz_history (
	quiz_history_id INT UNSIGNED AUTO_INCREMENT,
	quiz_id INT,
	user_id INT,
	score INT,
	rating INT UNSIGNED,
	review VARCHAR(255),
	name VARCHAR(255),
	PRIMARY KEY (quiz_history_id)
);

CREATE TABLE IF NOT EXISTS friends (
	user_id1 INT UNSIGNED,
    user_id2 INT UNSIGNED,
	PRIMARY KEY (user_id1)
);

CREATE TABLE IF NOT EXISTS friend_requests (
	friend_request_id INT UNSIGNED AUTO_INCREMENT,
	from_user_id INT UNSIGNED,
	to_user_id INT UNSIGNED,
	PRIMARY KEY (friend_request_id)
);

CREATE TABLE IF NOT EXISTS messages (
	message_id INT UNSIGNED AUTO_INCREMENT,
	sender_id INT UNSIGNED,
	receiver_id INT UNSIGNED,
	data VARCHAR(255),
	PRIMARY KEY (message_id)
);

CREATE TABLE IF NOT EXISTS announcements (
	announcement_id INT UNSIGNED AUTO_INCREMENT,
	description VARCHAR(255),
	PRIMARY KEY (announcement_id)
);

CREATE TABLE IF NOT EXISTS achievements (
	achievement_id INT UNSIGNED AUTO_INCREMENT,
	user_id INT UNSIGNED,
	description VARCHAR(255),
	PRIMARY KEY (achievement_id)
);