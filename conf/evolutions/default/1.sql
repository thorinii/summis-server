# Initial Database schema

# --- !Ups

CREATE TABLE project (
  id SERIAL,
  slug VARCHAR(255) NOT NULL,
  name VARCHAR(255) NOT NULL,

  PRIMARY KEY (id)
);


# --- !Downs

DROP TABLE project;
