INSERT INTO users (email, phone, password_hash, full_name, status) VALUES
('demo@famhealth.app', '+919999999999', '$2a$10$6GH8fG8eMNlM0Je5gvvJ2ueNvQ2fGo2BzmfRM6gOC6L8jB8cAjvsS', 'Demo User', 'ACTIVE');
INSERT INTO accounts (owner_user_id, name, country_code) VALUES (1, 'Demo Family', 'IN');
INSERT INTO person_profiles (account_id, first_name, last_name, relation_type, sex, birth_year, timezone) VALUES
(1, 'Asha', 'Patel', 'self', 'F', 1993, 'Asia/Kolkata');
