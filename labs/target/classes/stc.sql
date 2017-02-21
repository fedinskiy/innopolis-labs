CREATE TABLE call
(
    id INTEGER DEFAULT nextval('call_id_seq'::regclass) PRIMARY KEY NOT NULL,
    call_reason_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    superuser_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    status SMALLINT,
    CONSTRAINT call_call_reason_id_fk FOREIGN KEY (call_reason_id) REFERENCES call_reason (id),
    CONSTRAINT call_user_id_fk FOREIGN KEY (user_id) REFERENCES "user" (id),
    CONSTRAINT call_superuser_id_fk FOREIGN KEY (superuser_id) REFERENCES superuser (id)
);
CREATE TABLE call_reason
(
    id INTEGER DEFAULT nextval('call_reason_id_seq'::regclass) PRIMARY KEY NOT NULL,
    name VARCHAR(50) NOT NULL,
    script TEXT NOT NULL
);
CREATE TABLE email
(
    id INTEGER DEFAULT nextval('email_id_seq'::regclass) PRIMARY KEY NOT NULL,
    user_id BIGINT NOT NULL,
    superuser_id BIGINT NOT NULL,
    email_reason_id BIGINT NOT NULL,
    sended_at TIMESTAMP NOT NULL,
    content TEXT NOT NULL,
    subject VARCHAR(100) NOT NULL,
    CONSTRAINT email_user_id_fk FOREIGN KEY (user_id) REFERENCES "user" (id),
    CONSTRAINT email_superuser_id_fk FOREIGN KEY (superuser_id) REFERENCES superuser (id),
    CONSTRAINT email_email_reason_id_fk FOREIGN KEY (email_reason_id) REFERENCES email_reason (id)
);
CREATE TABLE email_reason
(
    id INTEGER DEFAULT nextval('email_reason_id_seq'::regclass) PRIMARY KEY NOT NULL,
    name VARCHAR(50) NOT NULL,
    template TEXT NOT NULL,
    subject VARCHAR(100) NOT NULL
);
CREATE TABLE interview
(
    id INTEGER DEFAULT nextval('interview_id_seq'::regclass) PRIMARY KEY NOT NULL,
    name VARCHAR(50) NOT NULL
);
CREATE TABLE interview_answer
(
    id INTEGER DEFAULT nextval('interview_answer_id_seq'::regclass) PRIMARY KEY NOT NULL,
    rating SMALLINT NOT NULL,
    interview_result_id BIGINT NOT NULL,
    interview_question_id BIGINT NOT NULL,
    CONSTRAINT interview_answer_interview_result_id_fk FOREIGN KEY (interview_result_id) REFERENCES interview_result (id),
    CONSTRAINT interview_answer_interview_question_id_fk FOREIGN KEY (interview_question_id) REFERENCES interview_question (id)
);
CREATE TABLE interview_question
(
    id INTEGER DEFAULT nextval('interview_question_id_seq'::regclass) PRIMARY KEY NOT NULL,
    interview_id BIGINT NOT NULL,
    question TEXT NOT NULL,
    criteria TEXT NOT NULL,
    CONSTRAINT interview_question_interview_id_fk FOREIGN KEY (interview_id) REFERENCES interview (id)
);
CREATE TABLE interview_result
(
    id INTEGER DEFAULT nextval('interview_result_id_seq'::regclass) PRIMARY KEY NOT NULL,
    user_id BIGINT NOT NULL,
    superuser_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    total_rating SMALLINT NOT NULL,
    CONSTRAINT interview_result_user_id_fk FOREIGN KEY (user_id) REFERENCES "user" (id),
    CONSTRAINT interview_result_superuser_id_fk FOREIGN KEY (superuser_id) REFERENCES superuser (id)
);
CREATE TABLE scheduled_call
(
    id INTEGER DEFAULT nextval('scheduled_call_id_seq'::regclass) PRIMARY KEY NOT NULL,
    call_id BIGINT NOT NULL,
    scheduled_at TIMESTAMP NOT NULL,
    CONSTRAINT scheduled_call_call_id_fk FOREIGN KEY (call_id) REFERENCES call (id)
);
CREATE TABLE superuser
(
    id INTEGER DEFAULT nextval('user_id_seq'::regclass) PRIMARY KEY NOT NULL,
    firstname VARCHAR(20) NOT NULL,
    middlename VARCHAR(20),
    lastname VARCHAR(20) NOT NULL,
    email VARCHAR(40) NOT NULL
);
CREATE TABLE test_result
(
    id INTEGER DEFAULT nextval('test_result_id_seq'::regclass) PRIMARY KEY NOT NULL,
    user_id BIGINT NOT NULL,
    rating SMALLINT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    CONSTRAINT test_result_user_id_fk FOREIGN KEY (user_id) REFERENCES "user" (id)
);
CREATE TABLE "user"
(
    id INTEGER DEFAULT nextval('user_id_seq'::regclass) PRIMARY KEY NOT NULL,
    bitrix_id BIGINT NOT NULL,
    firstname VARCHAR(20) NOT NULL,
    middlename VARCHAR(20),
    lastname VARCHAR(20) NOT NULL,
    email VARCHAR(40) NOT NULL,
    phone VARCHAR(20),
    birthdate DATE
);
CREATE UNIQUE INDEX user_bitrix_id_uindex ON "user" (bitrix_id);