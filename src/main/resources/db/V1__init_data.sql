--初始化系统管理员
INSERT INTO base_sys(id, username, password, name, roles, is_manager, is_enable, is_delete, update_time, create_time)
VALUES ("1", "admin", "12345678", "admin", "ROLE_SYS", 1, 1, 0, now(), now());

INSERT INTO base_auto_number(n_key, number) VALUES ('main_insert_key', 0);