CREATE TABLE users (
                       user_id       SERIAL PRIMARY KEY,
                       email         VARCHAR(255) NOT NULL UNIQUE,
                       password_hash VARCHAR(255) NOT NULL,
                       nickname      VARCHAR(50),
                       role          VARCHAR(20) NOT NULL DEFAULT 'USER',
                       created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE artists (
                         artist_id    SERIAL PRIMARY KEY,
                         user_id      INT NOT NULL,
                         profile_img  TEXT,
                         bio          TEXT,
                         is_verified  BOOLEAN DEFAULT FALSE,
                         royalty_rate DECIMAL(5, 2),

                         FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE vendors (
                         vendor_id    SERIAL PRIMARY KEY,
                         user_id      INT NOT NULL,
                         company_name VARCHAR(100),
                         shipping_fee INT DEFAULT 0,

                         FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE vendor_products (
                                 v_prod_id             SERIAL PRIMARY KEY,
                                 vendor_id             INT NOT NULL,
                                 category_name         VARCHAR(50) NOT NULL,
                                 min_order_quantity    INT NOT NULL,
                                 weekly_min_capacity   INT NOT NULL,
                                 weekly_max_capacity   INT NOT NULL,

                                 FOREIGN KEY (vendor_id) REFERENCES vendors(vendor_id)
);

CREATE TABLE vendor_price_tiers (
                                    tier_id        SERIAL PRIMARY KEY,
                                    v_prod_id      INT NOT NULL,
                                    min_quantity   INT NOT NULL,
                                    max_quantity   INT NOT NULL,
                                    price_per_unit INT NOT NULL,

                                    FOREIGN KEY (v_prod_id) REFERENCES vendor_products(v_prod_id)
);

CREATE TABLE funding_projects (
                                  project_id     SERIAL PRIMARY KEY,
                                  creator_id     INT NOT NULL,
                                  artist_id      INT NOT NULL,
                                  vendor_id      INT,
                                  v_prod_id      INT,
                                  title          VARCHAR(200) NOT NULL,
                                  description    TEXT,
                                  ai_image_url   TEXT NOT NULL,
    -- status: PENDING_VENDOR | RECRUITING | CONFIRMED | PRODUCING | DONE | CANCELLED
                                  status         VARCHAR(30) NOT NULL DEFAULT 'PENDING_VENDOR',
                                  max_unit_price INT NOT NULL,
                                  target_count   INT NOT NULL,
                                  current_count  INT NOT NULL DEFAULT 0,
                                  target_date    TIMESTAMP,
                                  created_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                                  FOREIGN KEY (creator_id) REFERENCES users(user_id),
                                  FOREIGN KEY (artist_id)  REFERENCES artists(artist_id),
                                  FOREIGN KEY (vendor_id)  REFERENCES vendors(vendor_id),
                                  FOREIGN KEY (v_prod_id)  REFERENCES vendor_products(v_prod_id)
);

CREATE TABLE vendor_proposals (
                                  proposal_id  SERIAL PRIMARY KEY,
                                  project_id   INT NOT NULL,
                                  vendor_id    INT NOT NULL,
                                  v_prod_id    INT NOT NULL,
    -- status: PENDING | APPROVED | REJECTED
                                  status       VARCHAR(20) NOT NULL DEFAULT 'PENDING',
                                  proposed_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                  responded_at TIMESTAMP,

                                  FOREIGN KEY (project_id) REFERENCES funding_projects(project_id),
                                  FOREIGN KEY (vendor_id)  REFERENCES vendors(vendor_id),
                                  FOREIGN KEY (v_prod_id)  REFERENCES vendor_products(v_prod_id)
);

CREATE TABLE participations (
                                participation_id SERIAL PRIMARY KEY,
                                project_id       INT NOT NULL,
                                user_id          INT NOT NULL,
                                total_paid       INT NOT NULL,
                                refund_amount    INT DEFAULT 0,
    -- status: PAID | REFUND_PENDING | REFUNDED | PRODUCING | DELIVERED | DONE
                                status           VARCHAR(20) NOT NULL DEFAULT 'PAID',
                                tracking_number  VARCHAR(100),
                                address          TEXT NOT NULL,
                                participated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                                FOREIGN KEY (project_id) REFERENCES funding_projects(project_id),
                                FOREIGN KEY (user_id)    REFERENCES users(user_id)
);

CREATE TABLE user_artists (
                              user_artist_id SERIAL PRIMARY KEY,
                              user_id        INT NOT NULL,
                              artist_id      INT NOT NULL,
                              created_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                              UNIQUE (user_id, artist_id),
                              FOREIGN KEY (user_id)   REFERENCES users(user_id),
                              FOREIGN KEY (artist_id) REFERENCES artists(artist_id)
);