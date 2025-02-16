create table if not exists merch(

                                    id serial primary key,
                                    name varchar(50) unique not null,
                                    price INTEGER NOT NULL CHECK (price > 0)

);

INSERT INTO merch (name, price) VALUES
                                    ('t-shirt', 80),
                                    ('cup', 20),
                                    ('book', 50),
                                    ('pen', 10),
                                    ('powerbank', 200),
                                    ('hoody', 300),
                                    ('umbrella', 200),
                                    ('socks', 10),
                                    ('wallet', 50),
                                    ('pink-hoody', 500);

create table if not exists users(

                                    id serial primary key,
                                    username varchar(50) unique not null,
                                    password varchar,
                                    role varchar(50),
                                    account_balance int check ( account_balance >= 0 )


);

create table if not exists inventory(

                                        id serial primary key,
                                        product_name varchar(50),
                                        product_count int,
                                        user_id int,
                                        foreign key (user_id) references users(id) on delete cascade

);


create table if not exists coin_history(

                                           id serial primary key,

                                           user_id int,

                                           from_user varchar,

                                           to_user varchar,

                                           coins int,

                                           foreign key (user_id) references users(id) on delete cascade

);


