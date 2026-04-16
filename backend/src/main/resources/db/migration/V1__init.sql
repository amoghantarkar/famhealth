CREATE TABLE users (
  id BIGSERIAL PRIMARY KEY,
  email VARCHAR(255) UNIQUE NOT NULL,
  phone VARCHAR(40),
  password_hash VARCHAR(255) NOT NULL,
  full_name VARCHAR(255) NOT NULL,
  status VARCHAR(40) NOT NULL,
  created_at TIMESTAMP DEFAULT now(),
  updated_at TIMESTAMP DEFAULT now(),
  deleted BOOLEAN DEFAULT FALSE
);
CREATE TABLE accounts (
  id BIGSERIAL PRIMARY KEY,
  owner_user_id BIGINT REFERENCES users(id),
  name VARCHAR(255) NOT NULL,
  country_code VARCHAR(10) NOT NULL,
  created_at TIMESTAMP DEFAULT now()
);
CREATE TABLE person_profiles (
  id BIGSERIAL PRIMARY KEY,
  account_id BIGINT REFERENCES accounts(id),
  first_name VARCHAR(120),
  last_name VARCHAR(120),
  relation_type VARCHAR(60),
  sex VARCHAR(30),
  birth_year INT,
  timezone VARCHAR(80),
  created_at TIMESTAMP DEFAULT now(),
  deleted BOOLEAN DEFAULT FALSE
);
CREATE TABLE medical_records (
  id BIGSERIAL PRIMARY KEY,
  account_id BIGINT REFERENCES accounts(id),
  profile_id BIGINT REFERENCES person_profiles(id),
  uploaded_by_user_id BIGINT REFERENCES users(id),
  record_type VARCHAR(60),
  provider_name VARCHAR(255),
  record_date DATE,
  source_type VARCHAR(50),
  processing_status VARCHAR(50),
  created_at TIMESTAMP DEFAULT now()
);
CREATE TABLE record_files (
  id BIGSERIAL PRIMARY KEY,
  medical_record_id BIGINT REFERENCES medical_records(id),
  storage_key VARCHAR(500), file_name VARCHAR(255), mime_type VARCHAR(120),
  file_size BIGINT, checksum VARCHAR(255), uploaded_at TIMESTAMP DEFAULT now()
);
CREATE TABLE extracted_observations (
  id BIGSERIAL PRIMARY KEY,
  medical_record_id BIGINT REFERENCES medical_records(id),
  profile_id BIGINT REFERENCES person_profiles(id),
  metric_code VARCHAR(80), metric_name VARCHAR(120), value_text VARCHAR(120), value_numeric NUMERIC(12,3),
  unit VARCHAR(40), reference_range VARCHAR(120), observed_at DATE, extraction_confidence NUMERIC(4,2), source_type VARCHAR(60),
  source_page INT, source_snippet TEXT, review_status VARCHAR(50), created_at TIMESTAMP DEFAULT now()
);
CREATE TABLE observation_reviews (
  id BIGSERIAL PRIMARY KEY,
  observation_id BIGINT REFERENCES extracted_observations(id),
  reviewed_by_user_id BIGINT REFERENCES users(id),
  original_value VARCHAR(255), corrected_value VARCHAR(255), review_action VARCHAR(60), reviewed_at TIMESTAMP DEFAULT now()
);
CREATE TABLE health_metric_summaries (
  id BIGSERIAL PRIMARY KEY,
  profile_id BIGINT REFERENCES person_profiles(id),
  metric_code VARCHAR(80), metric_name VARCHAR(120), latest_value NUMERIC(12,3), previous_value NUMERIC(12,3), trend_direction VARCHAR(20),
  record_count INT, abnormal_flag BOOLEAN, last_updated_at TIMESTAMP
);
CREATE TABLE family_access_grants (
  id BIGSERIAL PRIMARY KEY,
  account_id BIGINT REFERENCES accounts(id), profile_id BIGINT REFERENCES person_profiles(id), granted_to_user_id BIGINT REFERENCES users(id),
  permission_level VARCHAR(20), status VARCHAR(30), granted_at TIMESTAMP DEFAULT now(), revoked_at TIMESTAMP
);
CREATE TABLE chat_sessions (
  id BIGSERIAL PRIMARY KEY,
  account_id BIGINT REFERENCES accounts(id), profile_id BIGINT REFERENCES person_profiles(id), created_by_user_id BIGINT REFERENCES users(id),
  title VARCHAR(200), created_at TIMESTAMP DEFAULT now()
);
CREATE TABLE chat_messages (
  id BIGSERIAL PRIMARY KEY,
  chat_session_id BIGINT REFERENCES chat_sessions(id), role VARCHAR(20), content TEXT, citations_json TEXT, created_at TIMESTAMP DEFAULT now()
);
CREATE TABLE audit_logs (
  id BIGSERIAL PRIMARY KEY,
  actor_user_id BIGINT REFERENCES users(id), account_id BIGINT REFERENCES accounts(id), profile_id BIGINT REFERENCES person_profiles(id),
  action_type VARCHAR(80), resource_type VARCHAR(80), resource_id VARCHAR(120), metadata_json TEXT, created_at TIMESTAMP DEFAULT now()
);
CREATE TABLE refresh_tokens (
  id BIGSERIAL PRIMARY KEY,
  user_id BIGINT REFERENCES users(id),
  token VARCHAR(255) UNIQUE,
  expires_at TIMESTAMP,
  revoked BOOLEAN DEFAULT FALSE
);
