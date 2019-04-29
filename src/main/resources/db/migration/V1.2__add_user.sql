CREATE TABLE users (username VARCHAR(100) NOT NULL PRIMARY KEY, encoded_password VARCHAR(255));
INSERT INTO users (username, encoded_password) VALUES ('user1', '$2a$10$ALcXPgrpOQKXoIyrgS90huCbgR906LtWrH1dOsZmHtBZdSB19n9Bi' /*sonomirai*/);
INSERT INTO users (username, encoded_password) VALUES ('user2', '$2a$10$ALcXPgrpOQKXoIyrgS90huCbgR906LtWrH1dOsZmHtBZdSB19n9Bi' /*sonomirai*/);
ALTER TABLE articles ADD username VARCHAR(100) NOT NULL DEFAULT 'user1';
ALTER TABLE articles ADD CONSTRAINT FK_ARTICLES_USERNAME FOREIGN KEY (username) REFERENCES users;
