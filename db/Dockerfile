FROM mysql
WORKDIR /tmp
COPY world/world.sql /docker-entrypoint-initdb.d
ENV MYSQL_ROOT_PASSWORD example