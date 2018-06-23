--=========================================================================
--现金券

--id:编号
--code:电子券编号
--shop_id:所属店铺编号
--user_id:所属用户编号
--name:用户姓名
--head_img:用头像
--money:金额
--state:奖券状态
--version:版本号，乐观锁
--from_date:有效期开始日期
--to_date:有效期结束日期
--create_time:创建时间
--use_time:使用时间
--=========================================================================
CREATE TABLE shop_pticket (
  id CHAR(32) COLLATE utf8_bin NOT NULL,
  code CHAR(32) COLLATE utf8_bin NOT NULL,
  shop_id CHAR(32) COLLATE utf8_bin NOT NULL,
  user_id CHAR(32) COLLATE utf8_bin NOT NULL,
  name VARCHAR(20) COLLATE utf8_bin DEFAULT "",
  head_img VARCHAR(200) COLLATE utf8_bin,
  money INTEGER COLLATE utf8_bin NOT NULL,
  state ENUM('WAIT', 'USE', 'EXPIRE') COLLATE utf8_bin NOT NULL,
  version INTEGER NOT NULL,
  from_date DATETIME NOT NULL,
  to_date DATETIME NOT NULL,
  create_time DATETIME NOT NULL,
  use_time DATETIME,
  PRIMARY KEY (id),
  UNIQUE KEY uniq_code (code),
  INDEX idx_shop_id (shop_id),
  INDEX idx_user_id (user_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--=========================================================================
--大转盘

--id:编号
--shop_id:所属店铺编号
--name:名称
--help:帮助
--day_limit:每天限玩次数
--pticket_days:奖券有效期
--state:状态
--from_date:有效期开始日期
--to_date:有效期结束日期
--create_time:创建时间
--=========================================================================
CREATE TABLE game_big_wheel (
  id CHAR(32) COLLATE utf8_bin NOT NULL,
  shop_id CHAR(32) COLLATE utf8_bin NOT NULL,
  name VARCHAR(20) COLLATE utf8_bin DEFAULT "",
  help VARCHAR(200) COLLATE utf8_bin,
  day_limit INT NOT NULL,
  pticket_days INT NOT NULL,
  state ENUM('WAIT', 'OPEN', 'CLOSE') COLLATE utf8_bin NOT NULL,
  from_date DATETIME NOT NULL,
  to_date DATETIME NOT NULL,
  create_time DATETIME NOT NULL,
  PRIMARY KEY (id),
  INDEX idx_shop_id (shop_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--=========================================================================
--大转盘明细项

--id:编号
--big_wheel_id:所属店铺编号
--item_index:顺序
--from_cursor:开始角度
--to_cursor:结束角度
--money:中奖金额
--title:中奖标题
--ratio:中奖率
--=========================================================================
CREATE TABLE game_big_wheel_item (
  id CHAR(32) COLLATE utf8_bin NOT NULL,
  big_wheel_id CHAR(32) COLLATE utf8_bin NOT NULL,
  item_index TINYINT NOT NULL,
  from_cursor SMALLINT NOT NULL,
  to_cursor SMALLINT NOT NULL,
  money INT NOT NULL,
  title VARCHAR(200) COLLATE utf8_bin,
  ratio TINYINT NOT NULL,
  PRIMARY KEY (id),
  INDEX idx_big_wheel_id (big_wheel_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--=========================================================================
--游戏玩家信息

--id:编号
--game_id:游戏编号
--game_name:游戏名称
--user_id:用户编号
--head_img:用户头像
--name:用户名
--prize:中奖名称
--money:中奖金额
--pticket_id:现金券
--create_time:创建时间
--=========================================================================
CREATE TABLE game_play (
  id CHAR(32) COLLATE utf8_bin NOT NULL,
  game_id CHAR(32) COLLATE utf8_bin NOT NULL,
  game_name VARCHAR(30) COLLATE utf8_bin NOT NULL,
  user_id CHAR(32) COLLATE utf8_bin NOT NULL,
  head_img VARCHAR(200) COLLATE utf8_bin NOT NULL,
  name VARCHAR(30) COLLATE utf8_bin NOT NULL,
  prize VARCHAR(30) COLLATE utf8_bin NOT NULL,
  money INT NOT NULL,
  pticket_id CHAR(32) COLLATE utf8_bin NOT NULL,
  create_time DATETIME NOT NULL,
  PRIMARY KEY (id),
  INDEX idx_game_id (game_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;