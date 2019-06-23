drop table if  exists employee;
drop table if exists CALC_GROUP;
CREATE TABLE CALC_GROUP (
    CALCGRP_ID           BIGINT           NOT NULL AUTO_INCREMENT,
    NAME                 VARCHAR(40)      NOT NULL,
    DESCP                VARCHAR(100)         NULL,
    FLAG1                VARCHAR( 1)          NULL,
    FLAG2                VARCHAR( 1)          NULL,
    FLAG3                VARCHAR( 1)          NULL,
    FLAG4                VARCHAR( 1)          NULL,
    FLAG5                VARCHAR( 1)          NULL,
    UDF1                 VARCHAR(40)          NULL,
    UDF2                 VARCHAR(40)          NULL,
    UDF3                 VARCHAR(40)          NULL,
    UDF4                 VARCHAR(40)          NULL,
    UDF5                 VARCHAR(40)          NULL,    
    CONSTRAINT PK_CALC_GROUP PRIMARY KEY (CALCGRP_ID)
);



CREATE TABLE EMPLOYEE(
    EMP_ID                   BIGINT           NOT NULL AUTO_INCREMENT,
    NAME                     VARCHAR(40)      DEFAULT 'EMPTY' NOT NULL,
    LASTNAME                 VARCHAR(40)      NOT NULL,
    FIRSTNAME                VARCHAR(40)      NOT NULL,
    DAY_START_TIME           DATETIME             NULL,
    SHFTPAT_ID                   BIGINT           NOT NULL,
    CALCGRP_ID                   BIGINT           NOT NULL,
    BASE_RATE                DECIMAL(20, 5)   DEFAULT 0 NOT NULL,
    PAYGRP_ID                    BIGINT           NOT NULL,
    EFFECTIVE_DATE           DATETIME         DEFAULT CURRENT_TIMESTAMP NOT NULL,
    HIRE_DATE                DATETIME         DEFAULT CURRENT_TIMESTAMP NOT NULL,
    SENIORITY_DATE           DATETIME         DEFAULT CURRENT_TIMESTAMP NOT NULL,
    BIRTH_DATE               DATETIME         DEFAULT CURRENT_TIMESTAMP NOT NULL,
    TERMINATION_DATE         DATETIME         DEFAULT '3000-01-01 00:00:00' NOT NULL,
    STATUS                   VARCHAR(1)       DEFAULT 'A' NOT NULL,
    SIN                      VARCHAR(40)      NOT NULL,
    SHFTPAT_OFFSET           INTEGER          DEFAULT 0 NOT NULL,
	EMAILADDRESS             VARCHAR(40)          NULL,
    FLAG1                    VARCHAR(1)           NULL,
    FLAG2                    VARCHAR(1)           NULL,
    FLAG3                    VARCHAR(1)           NULL,
    FLAG4                    VARCHAR(1)           NULL,
    FLAG5                    VARCHAR(1)           NULL,
    FLAG6                    VARCHAR(1)           NULL,
    VAL1                     VARCHAR(40)          NULL,
    VAL2                     VARCHAR(40)          NULL,
    VAL3                     VARCHAR(40)          NULL,
    VAL4                     VARCHAR(40)          NULL,
    VAL5                     VARCHAR(40)          NULL,
    DEF_MINUTES              INTEGER          DEFAULT 480 NOT NULL,
    RETAIL_AVAIL             VARCHAR(1)           NULL,
    FULLTIME                 VARCHAR(1)           NULL,
    TZ_ID                        BIGINT           NULL,
    FULLNAME                 VARCHAR(80)      NOT NULL,
    SENIORITY_NUMBER         INTEGER				  ,       
    EMPTYP_ID					 BIGINT				  ,
    EMPWRKTYP_ID				 BIGINT				  ,
    FTE					     DECIMAL(6,5)	  DEFAULT 0 NOT NULL,
    
    CONSTRAINT PK_EMPLOYEE PRIMARY KEY (EMP_ID)    
);

drop table if exists CALC_GROUP;
CREATE TABLE CALC_GROUP (
    CALCGRP_ID           BIGINT           NOT NULL AUTO_INCREMENT,
    NAME                 VARCHAR(40)      NOT NULL,
    DESCP                VARCHAR(100)         NULL,
    FLAG1                VARCHAR( 1)          NULL,
    FLAG2                VARCHAR( 1)          NULL,
    FLAG3                VARCHAR( 1)          NULL,
    FLAG4                VARCHAR( 1)          NULL,
    FLAG5                VARCHAR( 1)          NULL,
    UDF1                 VARCHAR(40)          NULL,
    UDF2                 VARCHAR(40)          NULL,
    UDF3                 VARCHAR(40)          NULL,
    UDF4                 VARCHAR(40)          NULL,
    UDF5                 VARCHAR(40)          NULL,    
    CONSTRAINT PK_CALC_GROUP PRIMARY KEY (CALCGRP_ID)
);


drop table if exists PAY_GROUP;
CREATE TABLE PAY_GROUP(
    PAYGRP_ID                    BIGINT           NOT NULL AUTO_INCREMENT,
    PAYGRP_NAME                  VARCHAR(40)      NOT NULL,
    PAYGRP_DESC                  VARCHAR(100)         NULL,
    PAYGRP_START_DATE            DATETIME         NOT NULL,
    PAYGRP_END_DATE              DATETIME         NOT NULL,
    PAYGRPTYP_ID                 BIGINT           NOT NULL,
    PAYSYS_ID                    BIGINT           NOT NULL,
    PAYGRP_ADJUST_DATE           DATETIME        DEFAULT '1900-01-01 00:00:00' NOT NULL,
    PAYGRP_SUPERVISOR_DATE       DATETIME        DEFAULT '1900-01-01 00:00:00' NOT NULL,
    PAYGRP_HANDS_OFF_DATE        DATETIME        DEFAULT '1900-01-01 00:00:00' NOT NULL,
    PAYGRP_YEAR_FIRST_DATE       DATETIME        DEFAULT '2000-01-01 00:00:00'     NULL,
    PAYGRP_DAY_START_TIME        DATETIME             NULL,
    PAYGRP_UDF1                  VARCHAR(100)         NULL,
    PAYGRP_UDF2                  VARCHAR(100)         NULL,
    PAYGRP_UDF3                  VARCHAR(100)         NULL,
    PAYGRP_UDF4                  VARCHAR(100)         NULL,
    PAYGRP_UDF5                  VARCHAR(100)         NULL,
    PAYGRP_FLAG1                 VARCHAR(1)           NULL,
    PAYGRP_FLAG2                 VARCHAR(1)           NULL,
    PAYGRP_FLAG3                 VARCHAR(1)           NULL,
    PAYGRP_FLAG4                 VARCHAR(1)           NULL,
    PAYGRP_FLAG5                 VARCHAR(1)           NULL,
    PAYGRP_DATE1                 DATETIME             NULL,
    PAYGRP_DATE2                 DATETIME             NULL,
    PAYGRP_DATE3                 DATETIME             NULL,
    PAYGRP_DATE4                 DATETIME             NULL,
    PAYGRP_DATE5                 DATETIME             NULL,
    PGC_ID                       BIGINT               NULL,
    PAYGRP_OFFSET                INTEGER          NOT NULL DEFAULT 0,
    PAYGRP_ADJUST_FLAG           CHAR(1)          NOT NULL DEFAULT 'Y',
    PAYGRP_TS_LOCKOUT_DATE       DATETIME             NULL,
    PAYGRP_TS_LOCKOUT_OFFSET     BIGINT               NULL,
    PAYGRP_LOCKDOWN_OFFSET       BIGINT               NULL,
    PAYGRP_ROLL_LOCKDOWN_FLAG    CHAR(1)              NULL,
    PAYGRP_ROLL_LOCKOUT_FLAG     CHAR(1)              NULL,
    PAYGRP_ROLL_HANDSOFF_FLAG    CHAR(1)              NULL,
    PAYGRP_FUTURE_WINDOW         BIGINT               NULL,
    PET_ID                       BIGINT               NULL,
    PREUT_ID                     BIGINT               NULL,
    PAYGRP_TS_LOCKOUT_TIME DATETIME                   NULL,
    CONSTRAINT CHK_PGRP_START_END CHECK (PAYGRP_START_DATE <= PAYGRP_END_DATE),
    CONSTRAINT PK_PAY_GROUP PRIMARY KEY (PAYGRP_ID)
);


drop table if exists SHIFT;

CREATE TABLE SHIFT(
    SHFT_ID                      BIGINT           NOT NULL AUTO_INCREMENT,
    SHFT_NAME                    VARCHAR(40)      NOT NULL,
    SHFT_DESC                    VARCHAR(100)     NOT NULL,
    SHFT_START_TIME              DATETIME         NOT NULL,
    SHFT_END_TIME                DATETIME         NOT NULL,
    SHFTGRP_ID                   BIGINT           NOT NULL,
    SHFT_YAG                     VARCHAR(1)           NULL,
    COLR_ID                      BIGINT               NULL,
    SHFT_UDF1                    VARCHAR(100)         NULL,
    SHFT_UDF2                    VARCHAR(100)         NULL,
    SHFT_UDF3                    VARCHAR(100)         NULL,
    SHFT_UDF4                    VARCHAR(100)         NULL,
    SHFT_UDF5                    VARCHAR(100)         NULL,
    SHFT_VAL1                    INTEGER              NULL,
    SHFT_VAL2                    INTEGER              NULL,
    SHFT_VAL3                    INTEGER              NULL,
    SHFT_VAL4                    INTEGER              NULL,
    SHFT_VAL5                    INTEGER              NULL,
    CONSTRAINT PK_SHIFT PRIMARY KEY (SHFT_ID)
);

drop table if exists SHIFT_BREAK;
CREATE TABLE SHIFT_BREAK(
    SHFTBRK_ID                   BIGINT           NOT NULL AUTO_INCREMENT,
    SHFTBRK_START_TIME           DATETIME         NOT NULL,
    SHFTBRK_END_TIME             DATETIME         NOT NULL,
    SHFTBRK_MINUTES              INTEGER          DEFAULT 0 NOT NULL,
    SHFT_ID                      BIGINT           NOT NULL,
    TCODE_ID                     BIGINT           NOT NULL,
    HTYPE_ID                     BIGINT           NOT NULL,
    CONSTRAINT PK_SHIFT_BREAK PRIMARY KEY (SHFTBRK_ID)
);


ALTER TABLE EMPLOYEE
ADD CONSTRAINT FK_EMPLOYEE_CALC_GROUP
FOREIGN KEY (CALCGRP_ID) REFERENCES CALC_GROUP(CALCGRP_ID);
