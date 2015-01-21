# --- !Ups
CREATE TABLE TaskSateMapping (
    id bigint NOT NULL,
    state varchar(30) NOT NULL,
    PRIMARY KEY (id)
);

# --- !Downs
DROP TABLE TaskSateMapping;
