--h2 database is working in Oracle mode
CREATE SEQUENCE MTG_ID_SEQ
    START WITH 200
    INCREMENT BY 100;

CREATE TABLE MEETINGS (
    ID NUMBER(19,0) NOT NULL PRIMARY KEY,
    DESCRIPTION VARCHAR2(500) NOT NULL,
    DATE_DATE TIMESTAMP NULL,
    DATE_OFFSETDT TIMESTAMP NULL
);

INSERT INTO MEETINGS
   (SELECT 1, 'H2 startup', sysdate, sysdate from dual);
INSERT INTO MEETINGS
   (SELECT 2, 'H2 startup minus two days', sysdate-2, sysdate-2 from dual);