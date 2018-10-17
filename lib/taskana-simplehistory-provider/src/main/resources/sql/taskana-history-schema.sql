CREATE SCHEMA IF NOT EXISTS TASKANA;

SET SCHEMA TASKANA;

CREATE TABLE HISTORY_EVENTS (
        ID VARCHAR(40) NOT NULL,
        TYPE VARCHAR(64) NULL,
        USER_ID VARCHAR(64) NULL,
        CREATED TIMESTAMP NULL,
        COMMENT VARCHAR(64) NULL,
        WORKBASKET_KEY VARCHAR(64) NULL,
        TASK_ID VARCHAR(64) NULL,
        PRIMARY KEY (ID),
);
