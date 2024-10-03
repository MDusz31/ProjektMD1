-- Database: databasemd

-- DROP DATABASE IF EXISTS databasemd;

CREATE DATABASE databasemd
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Polish_Poland.1250'
    LC_CTYPE = 'Polish_Poland.1250'
    LOCALE_PROVIDER = 'libc'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;

GRANT TEMPORARY, CONNECT ON DATABASE databasemd TO PUBLIC;

GRANT ALL ON DATABASE databasemd TO postgres;

GRANT ALL ON DATABASE databasemd TO usermd;