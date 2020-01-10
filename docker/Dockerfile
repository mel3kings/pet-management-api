FROM mysql

ENV MYSQL_DATABASE=local \
    MYSQL_PASSWORD=test

ADD schema.sql /docker-entrypoint-initdb.d

EXPOSE 3306