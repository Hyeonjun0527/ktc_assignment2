CREATE DATABASE IF NOT EXISTS ktc_plan;

ALTER DATABASE ktc_plan CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

USE ktc_plan;

DROP TABLE IF EXISTS Plan;
DROP TABLE IF EXISTS member;
CREATE TABLE Plan (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    content VARCHAR(255) NOT NULL,
    member_id BIGINT NULL,
    pwd VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);


CREATE TABLE member (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    created_at TIMESTAMP NOT NULL,
    modified_at TIMESTAMP NOT NULL
);

ALTER TABLE plan ADD CONSTRAINT fk_plan_member FOREIGN KEY (member_id) REFERENCES member(id);


ALTER TABLE Plan CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
ALTER TABLE member CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 회원 선삽입
INSERT INTO member (id, name, email, created_at, modified_at) VALUES
(1, '홍길동', 'hong1@example.com', NOW(), NOW()),
(2, '김철수', 'kim2@example.com', NOW(), NOW()),
(3, '이영희', 'lee3@example.com', NOW(), NOW());

INSERT INTO Plan (member_id, content, pwd, created_at, modified_at) VALUES
(1, '스터디 계획 1', 'pw1234', NOW(), NOW()),
(2, '스터디 계획 2', 'pw2345', NOW(), NOW()),
(1, '스터디 계획 3', 'pw3456', NOW(), NOW()),
(3, '스터디 계획 4', 'pw4567', NOW(), NOW()),
(2, '스터디 계획 5', 'pw5678', NOW(), NOW()),
(1, '스터디 계획 6', 'pw6789', NOW(), NOW()),
(3, '스터디 계획 7', 'pw7890', NOW(), NOW()),
(2, '스터디 계획 8', 'pw8901', NOW(), NOW()),
(1, '스터디 계획 9', 'pw9012', NOW(), NOW()),
(3, '스터디 계획 10', 'pw0123', NOW(), NOW()),
(1, '스터디 계획 11', 'pw1123', NOW(), NOW()),
(2, '스터디 계획 12', 'pw2123', NOW(), NOW()),
(3, '스터디 계획 13', 'pw3123', NOW(), NOW()),
(1, '스터디 계획 14', 'pw4123', NOW(), NOW()),
(2, '스터디 계획 15', 'pw5123', NOW(), NOW()),
(3, '스터디 계획 16', 'pw6123', NOW(), NOW()),
(1, '스터디 계획 17', 'pw7123', NOW(), NOW()),
(2, '스터디 계획 18', 'pw8123', NOW(), NOW()),
(3, '스터디 계획 19', 'pw9123', NOW(), NOW()),
(1, '스터디 계획 20', 'pw1023', NOW(), NOW()),
(2, '스터디 계획 21', 'pw2023', NOW(), NOW()),
(3, '스터디 계획 22', 'pw3023', NOW(), NOW()),
(1, '스터디 계획 23', 'pw4023', NOW(), NOW()),
(2, '스터디 계획 24', 'pw5023', NOW(), NOW()),
(3, '스터디 계획 25', 'pw6023', NOW(), NOW()),
(1, '스터디 계획 26', 'pw7023', NOW(), NOW()),
(2, '스터디 계획 27', 'pw8023', NOW(), NOW()),
(3, '스터디 계획 28', 'pw9023', NOW(), NOW()),
(1, '스터디 계획 29', 'pw0023', NOW(), NOW()),
(2, '스터디 계획 30', 'pw1024', NOW(), NOW());

SELECT * FROM Plan;