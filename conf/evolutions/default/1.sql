# Initial Database schema

# --- !Ups

CREATE TABLE project (
  id BIGINT(20) NOT NULL AUTO_INCREMENT,
  slug VARCHAR(255) NOT NULL,
  name VARCHAR(255) NOT NULL,

  PRIMARY KEY (id)
);


# --- !Downs

DROP TABLE project;
