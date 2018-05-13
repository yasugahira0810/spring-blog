CREATE TABLE IF NOT EXISTS articles (id SERIAL, post_date TIMESTAMP, title VARCHAR(50), body_text VARCHAR(20000));
INSERT INTO articles(post_date, title, body_text) VALUES('2017-05-07T15:57:18.434', 'ほげほげ試してみた', 'ほげほげ');
INSERT INTO articles(post_date, title, body_text) VALUES('2010-05-07T15:57:18.434', 'ふがふが参加レポート', 'ふがふが');
INSERT INTO articles(post_date, title, body_text) VALUES(current_timestamp, 'バーバーツール', 'バーバー');