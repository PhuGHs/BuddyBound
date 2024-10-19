-- liquibase formatted sql

-- changeset ASUS:1729354289620-1
CREATE TABLE images (id BIGINT AUTO_INCREMENT NOT NULL, image_url VARCHAR(255) NULL, message_id BIGINT NULL, CONSTRAINT PK_IMAGES PRIMARY KEY (id));

-- changeset ASUS:1729354289620-2
CREATE TABLE location_histories (id BIGINT AUTO_INCREMENT NOT NULL, created_at datetime NULL, latitude DOUBLE NULL, longtitude DOUBLE NULL, user_id BIGINT NULL, CONSTRAINT PK_LOCATION_HISTORIES PRIMARY KEY (id));

-- changeset ASUS:1729354289620-3
CREATE TABLE members (id BIGINT AUTO_INCREMENT NOT NULL, join_date datetime NULL, nick_name VARCHAR(255) NULL, group_id BIGINT NULL, user_id BIGINT NULL, CONSTRAINT PK_MEMBERS PRIMARY KEY (id));

-- changeset ASUS:1729354289620-4
CREATE TABLE messages (id BIGINT AUTO_INCREMENT NOT NULL, created_at datetime NOT NULL, created_by VARCHAR(255) NOT NULL, updated_at datetime NULL, updated_by VARCHAR(255) NULL, content VARCHAR(255) NULL, group_id BIGINT NULL, member_id BIGINT NULL, CONSTRAINT PK_MESSAGES PRIMARY KEY (id));

-- changeset ASUS:1729354289620-5
CREATE TABLE notifications (id BIGINT AUTO_INCREMENT NOT NULL, created_at datetime NOT NULL, created_by VARCHAR(255) NOT NULL, updated_at datetime NULL, updated_by VARCHAR(255) NULL, message VARCHAR(255) NULL, notification_type VARCHAR(255) NULL, receiver_id BIGINT NULL, sender_id BIGINT NULL, CONSTRAINT PK_NOTIFICATIONS PRIMARY KEY (id));

-- changeset ASUS:1729354289620-6
CREATE TABLE relationship_privacies (id BIGINT AUTO_INCREMENT NOT NULL, share_contacts BIT(1) NULL, share_locations BIT(1) NULL, share_safe_zones BIT(1) NULL, relationship_id BIGINT NULL, CONSTRAINT PK_RELATIONSHIP_PRIVACIES PRIMARY KEY (id), UNIQUE (relationship_id));

-- changeset ASUS:1729354289620-7
CREATE TABLE relationship_types (id BIGINT AUTO_INCREMENT NOT NULL, category VARCHAR(255) NULL, `description` VARCHAR(255) NULL, sub_category VARCHAR(255) NULL, CONSTRAINT PK_RELATIONSHIP_TYPES PRIMARY KEY (id));

-- changeset ASUS:1729354289620-8
CREATE TABLE relationships (id BIGINT AUTO_INCREMENT NOT NULL, created_at datetime NOT NULL, created_by VARCHAR(255) NOT NULL, updated_at datetime NULL, updated_by VARCHAR(255) NULL, content VARCHAR(255) NULL, is_pending BIT(1) NULL, receiver_id BIGINT NULL, relationship_type_id BIGINT NULL, sender_id BIGINT NULL, CONSTRAINT PK_RELATIONSHIPS PRIMARY KEY (id));

-- changeset ASUS:1729354289620-9
CREATE TABLE `role` (id BIGINT AUTO_INCREMENT NOT NULL, `description` VARCHAR(255) NULL, role_name VARCHAR(255) NULL, CONSTRAINT PK_ROLE PRIMARY KEY (id));

-- changeset ASUS:1729354289620-10
CREATE TABLE safe_zones (id BIGINT AUTO_INCREMENT NOT NULL, latitude DOUBLE NOT NULL, longitude DOUBLE NOT NULL, radius FLOAT(12) NULL, CONSTRAINT PK_SAFE_ZONES PRIMARY KEY (id));

-- changeset ASUS:1729354289620-11
CREATE TABLE user_images (id BIGINT AUTO_INCREMENT NOT NULL, is_main_avatar BIT(1) NULL, image_id BIGINT NULL, user_id BIGINT NULL, CONSTRAINT PK_USER_IMAGES PRIMARY KEY (id));

-- changeset ASUS:1729354289620-12
CREATE TABLE user_safe_zones (id BIGINT AUTO_INCREMENT NOT NULL, created_at datetime NOT NULL, created_by VARCHAR(255) NOT NULL, updated_at datetime NULL, updated_by VARCHAR(255) NULL, safe_zone_id BIGINT NULL, user_id BIGINT NULL, CONSTRAINT PK_USER_SAFE_ZONES PRIMARY KEY (id));

-- changeset ASUS:1729354289620-13
CREATE TABLE users (id BIGINT AUTO_INCREMENT NOT NULL, email VARCHAR(255) NOT NULL, full_name VARCHAR(255) NOT NULL, hash_password VARCHAR(255) NOT NULL, phone_number VARCHAR(255) NULL, role_id BIGINT NULL, CONSTRAINT PK_USERS PRIMARY KEY (id));

-- changeset ASUS:1729354289620-14
CREATE INDEX FK13vcnq3ukas06ho1yrbc5lrb5 ON notifications(sender_id);

-- changeset ASUS:1729354289620-15
CREATE INDEX FK2ocnpmahu5o1cyysukrfbso93 ON relationships(sender_id);

-- changeset ASUS:1729354289620-16
CREATE INDEX FK4qu1gr772nnf6ve5af002rwya ON users(role_id);

-- changeset ASUS:1729354289620-17
CREATE INDEX FK5gbcx5cu8uri4eq4tef1rmpei ON messages(member_id);

-- changeset ASUS:1729354289620-18
CREATE INDEX FK9kxl0whvhifo6gw4tjq36v53k ON notifications(receiver_id);

-- changeset ASUS:1729354289620-19
CREATE INDEX FKf4q004l9bqjfr7hcbbm2wudhk ON relationships(receiver_id);

-- changeset ASUS:1729354289620-20
CREATE INDEX FKg8djilh0qmkfhdmpd3nntximm ON user_safe_zones(safe_zone_id);

-- changeset ASUS:1729354289620-21
CREATE INDEX FKgusy2l5y05c6llp3brbp1n5xs ON relationships(relationship_type_id);

-- changeset ASUS:1729354289620-22
CREATE INDEX FKihpxxo1uv7lsaclidpx4oo7sb ON location_histories(user_id);

-- changeset ASUS:1729354289620-23
CREATE INDEX FKl1lf9kxrd8ybmovqsxcuxhw42 ON user_images(user_id);

-- changeset ASUS:1729354289620-24
CREATE INDEX FKoftbrf5xfwneoc9gpnvveoirn ON user_images(image_id);

-- changeset ASUS:1729354289620-25
CREATE INDEX FKot5e998av6x19q9yb8n9qku03 ON user_safe_zones(user_id);

-- changeset ASUS:1729354289620-26
CREATE INDEX FKpj3n6wh5muoeakc485whgs3x5 ON members(user_id);

-- changeset ASUS:1729354289620-27
CREATE INDEX FKx9db470cm10vi4ctmk3wxxys ON images(message_id);

-- changeset ASUS:1729354289620-28
ALTER TABLE notifications ADD CONSTRAINT FK13vcnq3ukas06ho1yrbc5lrb5 FOREIGN KEY (sender_id) REFERENCES users (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

-- changeset ASUS:1729354289620-29
ALTER TABLE relationships ADD CONSTRAINT FK2ocnpmahu5o1cyysukrfbso93 FOREIGN KEY (sender_id) REFERENCES users (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

-- changeset ASUS:1729354289620-30
ALTER TABLE users ADD CONSTRAINT FK4qu1gr772nnf6ve5af002rwya FOREIGN KEY (role_id) REFERENCES `role` (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

-- changeset ASUS:1729354289620-31
ALTER TABLE messages ADD CONSTRAINT FK5gbcx5cu8uri4eq4tef1rmpei FOREIGN KEY (member_id) REFERENCES members (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

-- changeset ASUS:1729354289620-32
ALTER TABLE notifications ADD CONSTRAINT FK9kxl0whvhifo6gw4tjq36v53k FOREIGN KEY (receiver_id) REFERENCES users (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

-- changeset ASUS:1729354289620-33
ALTER TABLE relationships ADD CONSTRAINT FKf4q004l9bqjfr7hcbbm2wudhk FOREIGN KEY (receiver_id) REFERENCES users (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

-- changeset ASUS:1729354289620-34
ALTER TABLE user_safe_zones ADD CONSTRAINT FKg8djilh0qmkfhdmpd3nntximm FOREIGN KEY (safe_zone_id) REFERENCES safe_zones (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

-- changeset ASUS:1729354289620-35
ALTER TABLE relationships ADD CONSTRAINT FKgusy2l5y05c6llp3brbp1n5xs FOREIGN KEY (relationship_type_id) REFERENCES relationship_types (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

-- changeset ASUS:1729354289620-36
ALTER TABLE location_histories ADD CONSTRAINT FKihpxxo1uv7lsaclidpx4oo7sb FOREIGN KEY (user_id) REFERENCES users (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

-- changeset ASUS:1729354289620-37
ALTER TABLE user_images ADD CONSTRAINT FKl1lf9kxrd8ybmovqsxcuxhw42 FOREIGN KEY (user_id) REFERENCES users (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

-- changeset ASUS:1729354289620-38
ALTER TABLE user_images ADD CONSTRAINT FKoftbrf5xfwneoc9gpnvveoirn FOREIGN KEY (image_id) REFERENCES images (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

-- changeset ASUS:1729354289620-39
ALTER TABLE relationship_privacies ADD CONSTRAINT FKoj8o13hd3oulftgb21b9rss5g FOREIGN KEY (relationship_id) REFERENCES relationships (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

-- changeset ASUS:1729354289620-40
ALTER TABLE user_safe_zones ADD CONSTRAINT FKot5e998av6x19q9yb8n9qku03 FOREIGN KEY (user_id) REFERENCES users (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

-- changeset ASUS:1729354289620-41
ALTER TABLE members ADD CONSTRAINT FKpj3n6wh5muoeakc485whgs3x5 FOREIGN KEY (user_id) REFERENCES users (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

-- changeset ASUS:1729354289620-42
ALTER TABLE images ADD CONSTRAINT FKx9db470cm10vi4ctmk3wxxys FOREIGN KEY (message_id) REFERENCES messages (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

