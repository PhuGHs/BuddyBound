-- liquibase formatted sql

-- changeset liquibase:1729698215794-1
CREATE TABLE accounts (created_at datetime NOT NULL, id BIGINT AUTO_INCREMENT NOT NULL, role_id BIGINT NULL, updated_at datetime NULL, user_id BIGINT NULL, email VARCHAR(255) NOT NULL, password VARCHAR(255) NOT NULL, verification_code VARCHAR(255) NULL, CONSTRAINT PK_ACCOUNTS PRIMARY KEY (id), UNIQUE (role_id), UNIQUE (user_id));

-- changeset liquibase:1729698215794-2
CREATE TABLE images (id BIGINT AUTO_INCREMENT NOT NULL, message_id BIGINT NULL, image_url VARCHAR(255) NULL, CONSTRAINT PK_IMAGES PRIMARY KEY (id));

-- changeset liquibase:1729698215794-3
CREATE TABLE location_histories (latitude DOUBLE NULL, longtitude DOUBLE NULL, created_at datetime NULL, id BIGINT AUTO_INCREMENT NOT NULL, user_id BIGINT NULL, CONSTRAINT PK_LOCATION_HISTORIES PRIMARY KEY (id));

-- changeset liquibase:1729698215794-4
CREATE TABLE members (group_id BIGINT NULL, id BIGINT AUTO_INCREMENT NOT NULL, join_date datetime NULL, user_id BIGINT NULL, nick_name VARCHAR(255) NULL, CONSTRAINT PK_MEMBERS PRIMARY KEY (id));

-- changeset liquibase:1729698215794-5
CREATE TABLE messages (created_at datetime NOT NULL, group_id BIGINT NULL, id BIGINT AUTO_INCREMENT NOT NULL, member_id BIGINT NULL, updated_at datetime NULL, content VARCHAR(255) NULL, CONSTRAINT PK_MESSAGES PRIMARY KEY (id));

-- changeset liquibase:1729698215794-6
CREATE TABLE notifications (created_at datetime NOT NULL, id BIGINT AUTO_INCREMENT NOT NULL, receiver_id BIGINT NULL, sender_id BIGINT NULL, updated_at datetime NULL, message VARCHAR(255) NULL, notification_type VARCHAR(255) NULL, CONSTRAINT PK_NOTIFICATIONS PRIMARY KEY (id));

-- changeset liquibase:1729698215794-7
CREATE TABLE relationship_privacies (share_contacts BIT(1) NULL, share_locations BIT(1) NULL, share_safe_zones BIT(1) NULL, id BIGINT AUTO_INCREMENT NOT NULL, relationship_id BIGINT NULL, CONSTRAINT PK_RELATIONSHIP_PRIVACIES PRIMARY KEY (id), UNIQUE (relationship_id));

-- changeset liquibase:1729698215794-8
CREATE TABLE relationship_types (id BIGINT AUTO_INCREMENT NOT NULL, category VARCHAR(255) NULL, `description` VARCHAR(255) NULL, sub_category VARCHAR(255) NULL, CONSTRAINT PK_RELATIONSHIP_TYPES PRIMARY KEY (id));

-- changeset liquibase:1729698215794-9
CREATE TABLE relationships (is_pending BIT(1) NULL, created_at datetime NOT NULL, id BIGINT AUTO_INCREMENT NOT NULL, receiver_id BIGINT NULL, relationship_type_id BIGINT NULL, sender_id BIGINT NULL, updated_at datetime NULL, content VARCHAR(255) NULL, CONSTRAINT PK_RELATIONSHIPS PRIMARY KEY (id));

-- changeset liquibase:1729698215794-10
CREATE TABLE `role` (id BIGINT AUTO_INCREMENT NOT NULL, `description` VARCHAR(255) NULL, role_name VARCHAR(255) NULL, CONSTRAINT PK_ROLE PRIMARY KEY (id));

-- changeset liquibase:1729698215794-11
CREATE TABLE safe_zones (latitude DOUBLE NOT NULL, longitude DOUBLE NOT NULL, radius FLOAT(12) NULL, id BIGINT AUTO_INCREMENT NOT NULL, CONSTRAINT PK_SAFE_ZONES PRIMARY KEY (id));

-- changeset liquibase:1729698215794-12
CREATE TABLE user_images (is_main_avatar BIT(1) NULL, id BIGINT AUTO_INCREMENT NOT NULL, image_id BIGINT NULL, user_id BIGINT NULL, CONSTRAINT PK_USER_IMAGES PRIMARY KEY (id));

-- changeset liquibase:1729698215794-13
CREATE TABLE user_safe_zones (created_at datetime NOT NULL, id BIGINT AUTO_INCREMENT NOT NULL, safe_zone_id BIGINT NULL, updated_at datetime NULL, user_id BIGINT NULL, CONSTRAINT PK_USER_SAFE_ZONES PRIMARY KEY (id));

-- changeset liquibase:1729698215794-14
CREATE TABLE users (gender BIT(1) NULL, id BIGINT AUTO_INCREMENT NOT NULL, country VARCHAR(255) NULL, full_name VARCHAR(255) NOT NULL, phone_number VARCHAR(255) NULL, CONSTRAINT PK_USERS PRIMARY KEY (id));

-- changeset liquibase:1729698215794-15
CREATE INDEX FK13vcnq3ukas06ho1yrbc5lrb5 ON notifications(sender_id);

-- changeset liquibase:1729698215794-16
CREATE INDEX FK2ocnpmahu5o1cyysukrfbso93 ON relationships(sender_id);

-- changeset liquibase:1729698215794-17
CREATE INDEX FK5gbcx5cu8uri4eq4tef1rmpei ON messages(member_id);

-- changeset liquibase:1729698215794-18
CREATE INDEX FK9kxl0whvhifo6gw4tjq36v53k ON notifications(receiver_id);

-- changeset liquibase:1729698215794-19
CREATE INDEX FKf4q004l9bqjfr7hcbbm2wudhk ON relationships(receiver_id);

-- changeset liquibase:1729698215794-20
CREATE INDEX FKg8djilh0qmkfhdmpd3nntximm ON user_safe_zones(safe_zone_id);

-- changeset liquibase:1729698215794-21
CREATE INDEX FKgusy2l5y05c6llp3brbp1n5xs ON relationships(relationship_type_id);

-- changeset liquibase:1729698215794-22
CREATE INDEX FKihpxxo1uv7lsaclidpx4oo7sb ON location_histories(user_id);

-- changeset liquibase:1729698215794-23
CREATE INDEX FKl1lf9kxrd8ybmovqsxcuxhw42 ON user_images(user_id);

-- changeset liquibase:1729698215794-24
CREATE INDEX FKoftbrf5xfwneoc9gpnvveoirn ON user_images(image_id);

-- changeset liquibase:1729698215794-25
CREATE INDEX FKot5e998av6x19q9yb8n9qku03 ON user_safe_zones(user_id);

-- changeset liquibase:1729698215794-26
CREATE INDEX FKpj3n6wh5muoeakc485whgs3x5 ON members(user_id);

-- changeset liquibase:1729698215794-27
CREATE INDEX FKx9db470cm10vi4ctmk3wxxys ON images(message_id);

-- changeset liquibase:1729698215794-28
ALTER TABLE notifications ADD CONSTRAINT FK13vcnq3ukas06ho1yrbc5lrb5 FOREIGN KEY (sender_id) REFERENCES users (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

-- changeset liquibase:1729698215794-29
ALTER TABLE relationships ADD CONSTRAINT FK2ocnpmahu5o1cyysukrfbso93 FOREIGN KEY (sender_id) REFERENCES users (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

-- changeset liquibase:1729698215794-30
ALTER TABLE accounts ADD CONSTRAINT FK4xtl6t8kwxccusmtwohwmao2k FOREIGN KEY (role_id) REFERENCES `role` (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

-- changeset liquibase:1729698215794-31
ALTER TABLE messages ADD CONSTRAINT FK5gbcx5cu8uri4eq4tef1rmpei FOREIGN KEY (member_id) REFERENCES members (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

-- changeset liquibase:1729698215794-32
ALTER TABLE notifications ADD CONSTRAINT FK9kxl0whvhifo6gw4tjq36v53k FOREIGN KEY (receiver_id) REFERENCES users (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

-- changeset liquibase:1729698215794-33
ALTER TABLE relationships ADD CONSTRAINT FKf4q004l9bqjfr7hcbbm2wudhk FOREIGN KEY (receiver_id) REFERENCES users (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

-- changeset liquibase:1729698215794-34
ALTER TABLE user_safe_zones ADD CONSTRAINT FKg8djilh0qmkfhdmpd3nntximm FOREIGN KEY (safe_zone_id) REFERENCES safe_zones (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

-- changeset liquibase:1729698215794-35
ALTER TABLE relationships ADD CONSTRAINT FKgusy2l5y05c6llp3brbp1n5xs FOREIGN KEY (relationship_type_id) REFERENCES relationship_types (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

-- changeset liquibase:1729698215794-36
ALTER TABLE location_histories ADD CONSTRAINT FKihpxxo1uv7lsaclidpx4oo7sb FOREIGN KEY (user_id) REFERENCES users (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

-- changeset liquibase:1729698215794-37
ALTER TABLE user_images ADD CONSTRAINT FKl1lf9kxrd8ybmovqsxcuxhw42 FOREIGN KEY (user_id) REFERENCES users (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

-- changeset liquibase:1729698215794-38
ALTER TABLE accounts ADD CONSTRAINT FKnjuop33mo69pd79ctplkck40n FOREIGN KEY (user_id) REFERENCES users (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

-- changeset liquibase:1729698215794-39
ALTER TABLE user_images ADD CONSTRAINT FKoftbrf5xfwneoc9gpnvveoirn FOREIGN KEY (image_id) REFERENCES images (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

-- changeset liquibase:1729698215794-40
ALTER TABLE relationship_privacies ADD CONSTRAINT FKoj8o13hd3oulftgb21b9rss5g FOREIGN KEY (relationship_id) REFERENCES relationships (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

-- changeset liquibase:1729698215794-41
ALTER TABLE user_safe_zones ADD CONSTRAINT FKot5e998av6x19q9yb8n9qku03 FOREIGN KEY (user_id) REFERENCES users (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

-- changeset liquibase:1729698215794-42
ALTER TABLE members ADD CONSTRAINT FKpj3n6wh5muoeakc485whgs3x5 FOREIGN KEY (user_id) REFERENCES users (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

-- changeset liquibase:1729698215794-43
ALTER TABLE images ADD CONSTRAINT FKx9db470cm10vi4ctmk3wxxys FOREIGN KEY (message_id) REFERENCES messages (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

