CREATE TABLE IF NOT EXISTS roles (
    role_id             UUID            NOT NULL PRIMARY KEY,
    role                VARCHAR(255)    NOT NULL
);
INSERT INTO roles VALUES ('0f18acb3-5d62-4219-98fd-a229c25f4499', 'ROLE_ADMIN'), ('4296f816-53b6-4b19-94ff-f2a6768d38a7', 'ROLE_WRITER') ON CONFLICT DO NOTHING;

CREATE TABLE IF NOT EXISTS users (
    id                  UUID            NOT NULL PRIMARY KEY,
    active              INT             DEFAULT 0,
    name                VARCHAR(255)    NOT NULL,
    password            VARCHAR(255)    NOT NULL
);
INSERT INTO users VALUES ('3dccddc9-a5dd-459d-b476-cd7e42cba8eb', 1, 'admin', '$2a$10$tSeHGqlg8d5QY7MwSusx/uyGWfYZ8daUA8cClaBibI47x9s4VpfD.') ON CONFLICT DO NOTHING;
INSERT INTO users VALUES ('344d15a3-2723-4eee-8df9-6dae013bf046', 1, 'writer', '$2a$10$tSeHGqlg8d5QY7MwSusx/uyGWfYZ8daUA8cClaBibI47x9s4VpfD.') ON CONFLICT DO NOTHING;
INSERT INTO users VALUES ('3c7ff277-d3df-4534-ac38-9cb9843f186a', 1, 'writer2', '$2a$10$tSeHGqlg8d5QY7MwSusx/uyGWfYZ8daUA8cClaBibI47x9s4VpfD.') ON CONFLICT DO NOTHING;

CREATE TABLE IF NOT EXISTS user_role (
    user_id             UUID            NOT NULL,
    role_id             UUID            NOT NULL,
    PRIMARY KEY (user_id, role_id)
);
INSERT INTO user_role VALUES (
    '3dccddc9-a5dd-459d-b476-cd7e42cba8eb',
    (SELECT role_id FROM roles WHERE role = 'ROLE_ADMIN')
    ), (
    '3dccddc9-a5dd-459d-b476-cd7e42cba8eb',
    (SELECT role_id FROM roles WHERE role = 'ROLE_WRITER')
    ), (
    '344d15a3-2723-4eee-8df9-6dae013bf046',
    (SELECT role_id FROM roles WHERE role = 'ROLE_WRITER')
    ), (
    '3c7ff277-d3df-4534-ac38-9cb9843f186a',
    (SELECT role_id FROM roles WHERE role = 'ROLE_WRITER')
) ON CONFLICT DO NOTHING;

CREATE TABLE IF NOT EXISTS articles (
    id                  UUID                        NOT NULL PRIMARY KEY,
    author_id           UUID                        NOT NULL,
    name                VARCHAR(100)                NOT NULL,
    content             VARCHAR(255)                NOT NULL,
    date_publishing     TIMESTAMP WITH TIME ZONE    NOT NULL
);

INSERT INTO articles VALUES (
    '8dcf4ef3-f809-4985-8fd0-ba1f04b2cfdd',
    '344d15a3-2723-4eee-8df9-6dae013bf046',
    'test article',
    'test content',
    '2022-10-04T13:09:00Z'
), (
    'afced82f-e1a9-43a6-bc7c-eae7f5616c62',
    '344d15a3-2723-4eee-8df9-6dae013bf046',
    'test article 2',
    'test content 2',
    '2022-10-03T10:09:00Z'
);