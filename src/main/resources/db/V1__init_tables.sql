-------------------------------------------------------------------------
--创建业务数据表
-------------------------------------------------------------------------

--========================================================================
--自增数表

--id:编号
--n_key:数字key
--number:最大数
--========================================================================
CREATE TABLE base_auto_number (
  id INTEGER NOT NULL AUTO_INCREMENT,
  n_key VARCHAR(64) COLLATE utf8_bin NOT NULL,
  number INTEGER DEFAULT 0,
  PRIMARY KEY (id),
  UNIQUE KEY idx_base_auto_number_key (n_key)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

INSERT INTO base_auto_number(n_key, number) VALUES ('main_insert_key', 0);

--========================================================================
--系统管理员表

--id:编号
--username:用户名
--password:密码
--name:姓名
--head_img:头像
--phone:联系电话
--email:邮件
--roles:权限角色
--is_manager:是否是超级管理员
--is_enable:是否可用
--is_delete:是否删除
--update_time:更新时间
--create_time:创建时间
--========================================================================
CREATE TABLE base_sys (
  id CHAR(32) COLLATE utf8_bin NOT NULL,
  username VARCHAR(30) COLLATE utf8_bin NOT NULL,
  password VARCHAR(50) COLLATE utf8_bin NOT NULL,
  name VARCHAR(30) COLLATE utf8_bin,
  head_img VARCHAR(300) COLLATE utf8_bin,
  phone VARCHAR(20) COLLATE utf8_bin DEFAULT '' NOT NULL,
  email VARCHAR(50) COLLATE utf8_bin DEFAULT '' NOT NULL,
  roles VARCHAR(300) COLLATE utf8_bin DEFAULT '' NOT NULL,
  is_manager TINYINT DEFAULT 0 NOT NULL,
  is_enable TINYINT DEFAULT 1 NOT NULL,
  is_delete TINYINT DEFAULT 0 NOT NULL,
  update_time DATETIME NOT NULL,
  create_time DATETIME NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY idx_base_sys_username (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--初始化系统管理员
INSERT INTO base_sys(id, username, password, name, roles, is_manager, is_enable, is_delete, update_time, create_time)
VALUES ("1", "admin", "12345678", "admin", "ROLE_SYS", 1, 1, 0, now(), now());

--========================================================================
--上传文件

--id:编号
--user_id:用户编号
--user_type:用户类型
--url:图片访问路径
--path:路径
--create_time:创建时间
--========================================================================
CREATE TABLE base_upload (
  id CHAR(32) COLLATE utf8_bin NOT NULL,
  user_id CHAR(32) COLLATE utf8_bin NOT NULL,
  user_type VARCHAR(20) COLLATE utf8_bin NOT NULL,
  url VARCHAR(500) COLLATE utf8_bin NOT NULL,
  path VARCHAR(300) COLLATE utf8_bin NOT NULL,
  create_time DATETIME NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--========================================================================
--店铺表

--id:编号
--name:店铺名称
--phone:联系电话
--contact:联系人
--province:省份
--city:城市
--county:县
--address:所在地址
--location:地理坐标
--icon:图标
--images:显示图片
--summary:文章摘要
--detail:店铺描述
--open_time:开店时间
--services:服务
--state:店铺状态（WAIT:等待发布，OPEN:发布， CLOSE:关闭)
--is_try:是否使用
--is_delete:是否删除
--update_time:更新时间
--create_time:创建时间
--========================================================================
CREATE TABLE shop_info (
  id CHAR(32) COLLATE utf8_bin NOT NULL,
  name VARCHAR(30) COLLATE utf8_bin NOT NULL,
  phone VARCHAR(20) COLLATE utf8_bin NOT NULL,
  contact VARCHAR(15) COLLATE utf8_bin NOT NULL,
  province VARCHAR(15) COLLATE utf8_bin NOT NULL,
  province_name VARCHAR(15) COLLATE utf8_bin DEFAULT '',
  city VARCHAR(15) COLLATE utf8_bin NOT NULL,
  city_name VARCHAR(15) COLLATE utf8_bin DEFAULT '',
  county VARCHAR(15) COLLATE utf8_bin NOT NULL,
  county_name VARCHAR(15) COLLATE utf8_bin DEFAULT '',
  address VARCHAR(30) COLLATE utf8_bin NOT NULL,
  location VARCHAR(50) COLLATE utf8_bin,
  icon VARCHAR(300) COLLATE utf8_bin,
  images VARCHAR(2000) COLLATE utf8_bin,
  summary VARCHAR(200) COLLATE utf8_bin DEFAULT'' NOT NULL,
  detail VARCHAR(2000) COLLATE utf8_bin DEFAULT'' NOT NULL,
  open_time VARCHAR(200) COLLATE utf8_bin,
  services VARCHAR(400) COLLATE utf8_bin,
  state ENUM('WAIT','OPEN','CLOSE') COLLATE utf8_bin NOT NULL,
  is_try TINYINT DEFAULT 0 NOT NULL,
  try_from_time DATETIME ,
  try_to_time DATETIME,
  is_delete TINYINT DEFAULT 0 NOT NULL,
  update_time DATETIME NOT NULL,
  create_time DATETIME NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--=======================================================================
--店铺微信配置
--
--id:编号
--:shop_id:店铺编号
--appid:微信appid
--=======================================================================
CREATE TABLE shop_configure (
  id CHAR(32) COLLATE utf8_bin NOT NULL,
  shop_id CHAR(32) COLLATE utf8_bin NOT NULL,
  appid VARCHAR(128) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY uniq_shop_id (shop_id),
  UNIQUE KEY uniq_appid (appid)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--========================================================================
--店铺管理员

--id:编号
--shop_id:所属店铺编号
--username:用户名
--password:用户密码
--name:姓名
--head_img:头像
--phone:联系电话
--roles:所属角色
--is_enable:是否可用
--is_manager:是否管理者
--is_delete:是否删除
--update_time:更新时间
--create_time:创建时间
--=========================================================================
CREATE TABLE shop_manager (
  id CHAR(32) COLLATE utf8_bin NOT NULL,
  shop_id CHAR(32) COLLATE utf8_bin NOT NULL,
  username VARCHAR(30) COLLATE utf8_bin NOT NULL,
  password VARCHAR(50) COLLATE utf8_bin NOT NULL,
  name VARCHAR(40) COLLATE utf8_bin DEFAULT '' NOT NULL,
  head_img VARCHAR(300) COLLATE utf8_bin,
  phone VARCHAR(20) COLLATE utf8_bin DEFAULT '' NOT NULL,
  roles VARCHAR(300) COLLATE utf8_bin,
  is_enable TINYINT DEFAULT 1 NOT NULL,
  is_manager TINYINT DEFAULT 0 NOT NULL,
  is_delete TINYINT DEFAULT 0 NOT NULL,
  update_time DATETIME NOT NULL,
  create_time DATETIME NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY uniq_username (username),
  INDEX idx_shop_id (shop_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--==========================================================================
--店铺通知信息
--
--id:编号
--shop_id:店铺编号
--title:消息标题
--type:消息类型
--content:消息内容
--is_read:是否读
--uri:访问uri
--source:消息来源
--create_time:创建时间
--read_time:读取时间
--==========================================================================
CREATE TABLE shop_notice (
  id CHAR(32) COLLATE utf8_bin NOT NULL,
  shop_id CHAR(32) COLLATE utf8_bin NOT NULL,
  title VARCHAR(50) COLLATE utf8_bin NOT NULL,
  type VARCHAR(20) COLLATE utf8_bin DEFAULT '' NOT NULL,
  content VARCHAR(300) COLLATE utf8_bin NOT NULL,
  uri VARCHAR(200) COLLATE utf8_bin,
  source VARCHAR(100) COLLATE utf8_bin DEFAULT '' NOT NULL,
  is_read TINYINT DEFAULT 0 NOT NULL,
  create_time DATETIME NOT NULL,
  read_time DATETIME,
  PRIMARY KEY (id),
  INDEX idx_shop_id (shop_id),
  UNIQUE KEY uniq_shop_id_source(shop_id, source)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--===========================================================================
--用户

--id:编号
--shop_id:店铺编号
--appid:微信appid
--openid:微信openid
--unionid:微信unionid
--username:用户名
--password:用户密码
--name:姓名
--nickname:昵称
--phone:联系电话
--sex:性别
--head_img:头像
--province:省
--city:城市
--country:国家
--update_time:修改时间
--create_time:创建时间
--===========================================================================
CREATE TABLE shop_user (
  id CHAR(32) COLLATE utf8_bin NOT NULL,
  shop_id CHAR(32) COLLATE utf8_bin NOT NULL,
  appid VARCHAR(50) COLLATE utf8_bin NOT NULL,
  openid VARCHAR(50) COLLATE utf8_bin NOT NULL,
  unionid VARCHAR(50) COLLATE utf8_bin,
  username VARCHAR(20) COLLATE utf8_bin NOT NULL,
  password VARCHAR(20) COLLATE utf8_bin NOT NULL,
  name VARCHAR(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '',
  nickname VARCHAR(20) COLLATE utf8_bin NOT NULL DEFAULT '',
  phone VARCHAR(20) COLLATE utf8_bin NOT NULL DEFAULT '',
  sex VARCHAR(2) COLLATE utf8_bin NOT NULL DEFAULT '',
  head_img VARCHAR(200) COLLATE utf8_bin,
  province VARCHAR(20) COLLATE utf8_bin NOT NULL DEFAULT '',
  city VARCHAR(20) COLLATE utf8_bin NOT NULL DEFAULT '',
  country VARCHAR(20) COLLATE utf8_bin NOT NULL DEFAULT '',
  update_time DATETIME NOT NULL,
  create_time DATETIME NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY uniq_username (username),
  UNIQUE KEY uniq_openid (openid),
  INDEX idx_shop_id (shop_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--===========================================================================
--用户账户

--id:编号
--user_id:用户编号
--pay_code:支付验证码
--create_time:创建时间
--===========================================================================
CREATE TABLE shop_account (
  id CHAR(32) COLLATE utf8_bin NOT NULL,
  user_id CHAR(32) COLLATE utf8_bin NOT NULL,
  pay_code VARCHAR(32) COLLATE utf8_bin NOT NULL,
  create_time DATETIME NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY uniq_user_id (user_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--===========================================================================
--商品分类

--id:编号
--shop_id:所属店铺编号
--name:分类名称
--icon:分类图标
--show_order:显示排序
--update_time:修改时间
--create_time:创建时间
--===========================================================================
CREATE TABLE goods_catalog (
  id CHAR(32) COLLATE utf8_bin NOT NULL,
  shop_id CHAR(32) COLLATE utf8_bin NOT NULL,
  name VARCHAR(20) COLLATE utf8_bin NOT NULL,
  icon VARCHAR(200) COLLATE utf8_bin,
  show_order INTEGER DEFAULT 9999,
  update_time DATETIME NOT NULL,
  create_time DATETIME NOT NULL,
  PRIMARY KEY (id),
  INDEX idx_shop_id (shop_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--===========================================================================
--商品

--id:编号
--shop_id:所属店铺编号
--name:商品名称
--icon:商品图标
--images:商品描述图片
--summary:商品摘要
--detail:商品描述
--price:商品价格
--real_price:商品实际价格
--discount:商品折扣
--catalog:商品分类
--tag:商品标签
--sell:销售量
--stock:库存
--is_group:是否组合商品
--is_open:是否上架
--show_order:显示排序
--is_delete:是否删除
--update_time:修改时间
--create_time:创建时间
--===========================================================================
CREATE TABLE goods_info (
  id CHAR(32) COLLATE utf8_bin NOT NULL,
  shop_id CHAR(32) COLLATE utf8_bin NOT NULL,
  name VARCHAR(20) COLLATE utf8_bin NOT NULL,
  icon VARCHAR(200) COLLATE utf8_bin,
  images VARCHAR(2000) COLLATE utf8_bin,
  summary VARCHAR(200) COLLATE utf8_bin DEFAULT '' NOT NULL,
  detail VARCHAR(2000) COLLATE utf8_bin DEFAULT '' NOT NULL,
  price INTEGER NOT NULL,
  real_price INTEGER NOT NULL,
  discount INTEGER NOT NULL,
  catalog VARCHAR(20) COLLATE utf8_bin,
  tag VARCHAR(20) COLLATE utf8_bin,
  sell INTEGER NOT NULL DEFAULT 0,
  stock INTEGER NOT NULL DEFAULT 0,
  is_group TINYINT NOT NULL DEFAULT 0,
  is_open TINYINT NOT NULL DEFAULT 0,
  show_order INTEGER NOT NULL DEFAULT 9999,
  is_delete TINYINT NOT NULL DEFAULT 0,
  update_time DATETIME NOT NULL,
  create_time DATETIME NOT NULL,
  PRIMARY KEY (id),
  INDEX idx_shop_id (shop_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--===========================================================================
--组合商品明细

--id:编号
--goods_id:组合商品编号
--item_goods_id:商品编号
--name:分类名称
--icon:分类图标
--detail:描述
--create_time:创建时间
--===========================================================================
CREATE TABLE goods_group (
  id CHAR(32) COLLATE utf8_bin NOT NULL,
  goods_id CHAR(32) COLLATE utf8_bin NOT NULL,
  item_goods_id CHAR(32) COLLATE utf8_bin NOT NULL,
  name VARCHAR(20) COLLATE utf8_bin NOT NULL,
  icon VARCHAR(200) COLLATE utf8_bin,
  count INTEGER NOT NULL DEFAULT 1,
  create_time DATETIME NOT NULL,
  PRIMARY KEY (id),
  INDEX idx_goods_id (goods_id),
  INDEX idx_item_goods_id (item_goods_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--=========================================================================
--电子券

--id:编号
--code:电子券编号
--shop_id:所属店铺编号
--user_id:所属用户编号
--name:用户姓名
--head_img:用头像
--order_id:订单编号
--goods_id:商品编号
--goods_name:商品名称
--goods_icon:商品图标
--state:电子券状态
--version:版本号，乐观锁
--from_date:有效期开始日期
--to_date:有效期结束日期
--create_time:创建时间
--use_time:使用时间
--=========================================================================
CREATE TABLE shop_eticket (
  id CHAR(32) COLLATE utf8_bin NOT NULL,
  code CHAR(32) COLLATE utf8_bin NOT NULL,
  shop_id CHAR(32) COLLATE utf8_bin NOT NULL,
  user_id CHAR(32) COLLATE utf8_bin NOT NULL,
  name VARCHAR(20) COLLATE utf8_bin DEFAULT "",
  head_img VARCHAR(200) COLLATE utf8_bin,
  order_id CHAR(32) COLLATE utf8_bin NOT NULL,
  goods_id CHAR(32) COLLATE utf8_bin NOT NULL,
  goods_name VARCHAR(30) NOT NULL DEFAULT "",
  goods_icon VARCHAR(200),
  state ENUM('WAIT', 'USE', 'EXPIRE', 'PRESENT') COLLATE utf8_bin NOT NULL,
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

--===========================================================================
--设置团购活动

--id:编号
--shop_id:所属商品编号
--goods_id:商品编号
--name:活动名称
--icon:活动图标
--images:描述图片
--summary:摘要
--detail:描述
--help:活动帮助
--price:活动时价格
--start_time:活动开始时间
--end_time:活动结束时间
--is_open:活动是否开始
--is_delete:是否删除
--show_order:活动排序
--person:活动参加人数
--wait_day:活动等待天数
--update_time:修改时间
--create_time:创建时间
--===========================================================================
CREATE TABLE marketing_conf_group (
  id CHAR(32) COLLATE utf8_bin NOT NULL,
  shop_id CHAR(32) COLLATE utf8_bin NOT NULL,
  goods_id CHAR(32) COLLATE utf8_bin NOT NULL,
  name VARCHAR(20) COLLATE utf8_bin DEFAULT '' NOT NULL,
  icon VARCHAR(200) COLLATE utf8_bin,
  images VARCHAR(2000) COLLATE utf8_bin,
  summary VARCHAR(200) COLLATE utf8_bin DEFAULT '' NOT NULL,
  detail VARCHAR(3000) COLLATE utf8_bin DEFAULT '' NOT NULL,
  help VARCHAR(500) COLLATE utf8_bin DEFAULT '' NOT NULL,
  price INTEGER DEFAULT 0 NOT NULL,
  start_time DATETIME NOT NULL,
  end_time DATETIME NOT NULL,
  is_open TINYINT DEFAULT 0 NOT NULL,
  is_delete TINYINT DEFAULT 0 NOT NULL,
  show_order INTEGER DEFAULT 9999 NOT NULL,
  person TINYINT NOT NULL,
  days TINYINT NOT NULL,
  update_time DATETIME NOT NULL,
  create_time DATETIME NOT NULL,
  PRIMARY KEY (id),
  INDEX idx_shop_id (shop_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--===========================================================================
--设置多人行活动

--id:编号
--shop_id:所属商品编号
--goods_id:商品编号
--name:活动名称
--icon:活动图标
--images:描述图片
--summary:摘要
--detail:描述
--help:活动帮助
--price:活动时价格
--start_time:活动开始时间
--end_time:活动结束时间
--is_open:活动是否开始
--is_delete:是否删除
--show_order:活动排序
--person:活动参加人数
--is_first:是否新用户
--days:活动等待天数
--update_time:修改时间
--create_time:创建时间
--===========================================================================
CREATE TABLE marketing_conf_present (
  id CHAR(32) COLLATE utf8_bin NOT NULL,
  shop_id CHAR(32) COLLATE utf8_bin NOT NULL,
  goods_id CHAR(32) COLLATE utf8_bin NOT NULL,
  name VARCHAR(20) COLLATE utf8_bin DEFAULT '' NOT NULL,
  icon VARCHAR(200) COLLATE utf8_bin,
  images VARCHAR(2000) COLLATE utf8_bin,
  summary VARCHAR(200) COLLATE utf8_bin DEFAULT '' NOT NULL,
  detail VARCHAR(3000) COLLATE utf8_bin DEFAULT '' NOT NULL,
  help VARCHAR(500) COLLATE utf8_bin DEFAULT '' NOT NULL,
  price INTEGER DEFAULT 0 NOT NULL,
  start_time DATETIME NOT NULL,
  end_time DATETIME NOT NULL,
  is_open TINYINT DEFAULT 0 NOT NULL,
  is_delete TINYINT DEFAULT 0 NOT NULL,
  show_order INTEGER DEFAULT 9999 NOT NULL,
  person TINYINT NOT NULL,
  is_first TINYINT NOT NULL,
  days TINYINT NOT NULL,
  update_time DATETIME NOT NULL,
  create_time DATETIME NOT NULL,
  PRIMARY KEY (id),
  INDEX idx_shop_id (shop_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--===========================================================================
--设置秒杀活动

--id:编号
--shop_id:所属商品编号
--goods_id:商品编号
--name:活动名称
--icon:活动图标
--images:描述图片
--summary:摘要
--detail:描述
--help:活动帮助
--price:活动时价格
--start_time:活动开始时间
--end_time:活动结束时间
--is_open:活动是否开始
--is_delete:是否删除
--show_order:活动排序
--stock:库存
--remain:剩余库存
--version:版本号，乐观锁
--update_time:修改时间
--create_time:创建时间
--===========================================================================
CREATE TABLE marketing_conf_second_kill (
  id CHAR(32) COLLATE utf8_bin NOT NULL,
  shop_id CHAR(32) COLLATE utf8_bin NOT NULL,
  goods_id CHAR(32) COLLATE utf8_bin NOT NULL,
  name VARCHAR(20) COLLATE utf8_bin DEFAULT '' NOT NULL,
  icon VARCHAR(200) COLLATE utf8_bin,
  images VARCHAR(2000) COLLATE utf8_bin,
  summary VARCHAR(200) COLLATE utf8_bin DEFAULT '' NOT NULL,
  detail VARCHAR(3000) COLLATE utf8_bin DEFAULT '' NOT NULL,
  help VARCHAR(500) COLLATE utf8_bin DEFAULT '' NOT NULL,
  price INTEGER DEFAULT 0 NOT NULL,
  start_time DATETIME NOT NULL,
  end_time DATETIME NOT NULL,
  is_open TINYINT DEFAULT 0 NOT NULL,
  is_delete TINYINT DEFAULT 0 NOT NULL,
  show_order INTEGER DEFAULT 9999 NOT NULL,
  stock INTEGER DEFAULT 0 NOT NULL,
  remain INTEGER DEFAULT 0 NOT NULL,
  version INTEGER DEFAULT 0 NOT NULL,
  update_time DATETIME NOT NULL,
  create_time DATETIME NOT NULL,
  PRIMARY KEY (id),
  INDEX idx_shop_id (shop_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--===========================================================================
--组团营销活动记录

--id:编号
--shop_id:店铺编号
--marketing_id:活动编号
--name:活动名称
--icon:活动图标
--need_person:需要拼团人数
--join_person:参加人数
--is_first:是否新用户生效
--type:营销类型
--join_user_detail:购买用户描述
--state:拼团状态
--is_share:是否分享
--price:活动价格
--goods_id:活动商品编号
--user_id:创建拼团用户编号
--version:版本号，乐观锁
--start_time:开始时间
--end_time:结束时间
--close_time:关闭时间
--create_time:创建时间
--===========================================================================
CREATE TABLE marketing_record_group (
  id CHAR(32) COLLATE utf8_bin NOT NULL,
  shop_id CHAR(32) COLLATE utf8_bin NOT NULL,
  marketing_id CHAR(32) COLLATE utf8_bin NOT NULL,
  name VARCHAR(20) COLLATE utf8_bin NOT NULL,
  icon VARCHAR(200) COLLATE utf8_bin NOT NULL,
  need_person TINYINT NOT NULL,
  join_person TINYINT NOT NULL DEFAULT 0,
  is_first TINYINT NOT NULL DEFAULT 0,
  type VARCHAR(20) NOT NULL,
  join_user_detail VARCHAR(3000),
  state VARCHAR(20) COLLATE utf8_bin NOT NULL,
  is_share TINYINT DEFAULT 0 NOT NULL,
  price INTEGER NOT NULL,
  goods_id CHAR(32) COLLATE utf8_bin NOT NULL,
  user_id CHAR(32) COLLATE utf8_bin NOT NULL,
  version INTEGER NOT NULL DEFAULT 0,
  start_time DATETIME NOT NULL,
  end_time DATETIME NOT NULL,
  close_time DATETIME,
  create_time DATETIME NOT NULL,
  PRIMARY KEY (id),
  INDEX idx_shop_id (shop_id),
  INDEX idx_active_id (marketing_id),
  INDEX idx_user_id (user_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--===========================================================================
--组团营销活动记录明细

--id:编号
--record_id:组团营销活动记录编号
--user_id:用户编号
--nickname:用户昵称
--head_img:用户头像
--phone:电话号码
--is_owner:是否是团主
--order_id:订单编号
--is_first:是否新用户生效
--is_cancel:是否取消
--create_time:创建时间
--cancel_time:取消时间
--===========================================================================
CREATE TABLE marketing_record_group_item (
  id CHAR(32) COLLATE utf8_bin NOT NULL,
  record_id CHAR(32) COLLATE utf8_bin NOT NULL,
  user_id CHAR(32) COLLATE utf8_bin NOT NULL,
  nickname VARCHAR(20) COLLATE utf8_bin,
  head_img VARCHAR(200) COLLATE utf8_bin,
  phone VARCHAR(20) COLLATE utf8_bin,
  is_owner TINYINT NOT NULL DEFAULT 0,
  order_id CHAR(32) COLLATE utf8_bin NOT NULL,
  is_first TINYINT NOT NULL DEFAULT 0,
  is_cancel TINYINT NOT NULL DEFAULT 0,
  create_time DATETIME NOT NULL,
  cancel_time DATETIME,
  PRIMARY KEY (id),
  UNIQUE KEY uniq_order_id (order_id),
  INDEX idx_record_id (record_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--===========================================================================
--秒杀记录

--id:编号
--shop_id:店铺编号
--marketing_id:活动编号
--goods_id:商品编号
--order_id:订单编号
--name:活动名称
--icon:活动图标
--price:价格
--user_id:用户编号
--nickname:用户昵称
--head_img:用户头像
--phone:联系电话
--state:秒杀状态，WAIT:等待支付，PAY:支付，CANCEL:取消
--update_time:更新时间
--create_time:创建时间
--===========================================================================
CREATE TABLE marketing_record_second_kill (
  id CHAR(32) COLLATE utf8_bin NOT NULL,
  shop_id CHAR(32) COLLATE utf8_bin NOT NULL,
  marketing_id CHAR(32) COLLATE utf8_bin NOT NULL,
  goods_id CHAR(32) COLLATE utf8_bin NOT NULL,
  order_id CHAR(32) COLLATE utf8_bin NOT NULL,
  name VARCHAR(20) COLLATE utf8_bin NOT NULL,
  icon VARCHAR(200) COLLATE utf8_bin NOT NULL,
  price INTEGER NOT NULL,
  user_id CHAR(32) COLLATE utf8_bin NOT NULL,
  nickname VARCHAR(30) COLLATE utf8_bin,
  head_img VARCHAR(300) COLLATE utf8_bin,
  phone VARCHAR(20) COLLATE utf8_bin,
  state ENUM('WAIT', 'PAY', 'CANCEL') COLLATE utf8_bin NOT NULL,
  update_time DATETIME NOT NULL,
  create_time DATETIME NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY uniq_order_id (order_id),
  INDEX idx_shop_id (shop_id),
  INDEX idx_marketing_id (marketing_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--===========================================================================
--订单

--id:编号
--shop_id:店铺编号
--user_id:用户编号
--name:用户名
--phone:联系电话
--count:购买数量
--total:订单金额
--pay_total:支付金额
--detail:订单描述
--state:订单状态
--marketing_id:营销活动编号
--marketing_type:营销活动类型
--version:版本号，乐观锁
--is_delete:true:删除订单
--create_time:订单创建时间
--pay_time:支付时间
--cancel_time:取消时间
--===========================================================================
CREATE TABLE order_info (
  id CHAR(32) COLLATE utf8_bin NOT NULL,
  shop_id CHAR(32) COLLATE utf8_bin NOT NULL,
  user_id CHAR(32) COLLATE utf8_bin NOT NULL,
  name VARCHAR(30) COLLATE utf8_bin NOT NULL,
  phone VARCHAR(30) COLLATE utf8_bin NOT NULL,
  count INTEGER DEFAULT 0 NOT NULL,
  total INTEGER DEFAULT 0 NOT NULL,
  pay_total INTEGER DEFAULT 0 NOT NULL,
  detail VARCHAR(2000) COLLATE utf8_bin NOT NULL,
  state ENUM('WAIT', 'PAY', 'CANCEL', 'ROLL') COLLATE utf8_bin NOT NULL,
  marketing_id CHAR(32) COLLATE utf8_bin NOT NULL,
  marketing_type VARCHAR(20) COLLATE utf8_bin NOT NULL,
  version INTEGER NOT NULL DEFAULT 0,
  is_delete TINYINT(1) NOT NULL DEFAULT 0,
  create_time DATETIME NOT NULL,
  pay_time DATETIME,
  cancel_time DATETIME,
  PRIMARY KEY (id),
  INDEX idx_shop_id (shop_id),
  INDEX idx_user_id (user_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--===========================================================================
--订单明细

--id:编号
--order_id:订单编号
--goods_id:商品编号
--name:商品名称
--icon:商品图标
--price:商品价格
--count:购买数量
--total:总金额
--===========================================================================
CREATE TABLE order_item (
  id CHAR(32) COLLATE utf8_bin NOT NULL,
  order_id CHAR(32) COLLATE utf8_bin NOT NULL,
  goods_id CHAR(32) COLLATE utf8_bin NOT NULL,
  name VARCHAR(20) COLLATE utf8_bin,
  icon VARCHAR(200) COLLATE utf8_bin,
  price INTEGER NOT NULL,
  count INTEGER NOT NULL,
  total INTEGER NOT NULL,
  PRIMARY KEY (id),
  INDEX idx_order_id (order_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--==========================================================================
--营销项目支付通知处理日志
--
--id:订单编号
--marketing_id:营销活动编号
--marketing_type:营销活动类型
--state:处理状态(WAIT:等待处理,SUCCESS:处理成功,FAIL:处理失败)
--message:处理消息
--update_time:更新时间
--create_time:创建时间
--==========================================================================
CREATE TABLE order_marketing_pay (
  id CHAR(32) COLLATE utf8_bin NOT NULL,
  marketing_id CHAR(32) COLLATE utf8_bin NOT NULL,
  marketing_type CHAR(20) COLLATE utf8_bin NOT NULL,
  state ENUM('WAIT', 'SUCCESS', 'FAIL') COLLATE utf8_bin NOT NULL,
  message VARCHAR(200) COLLATE utf8_bin,
  update_time DATETIME NOT NULL,
  create_time DATETIME NOT NULL,
  PRIMARY KEY (id),
  INDEX idx_state (state)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--=======================================================================
-- 微信统一订单

--id:编号
--user_id:用户编号
--shop_id:店铺编号
--openid:openid
--out_trade_no:订单编号(需要支付的商品订单号)
--detail:订单描述
--attach:附加码
--fee_type:货币
--total_fee:支付金额(单位为分)
--real_total_fee:实际支付金额，微信推送的金额
--trade_type:支付方式(NATIVE:二维码，JSAPI:jsapi)
--transaction_no:微信支付订单号
--state:状态，wait:等待支付,pay:支付,refund:退款
--refund_fee:退款金额
--version:版本号
--create_time:创建时间
--pay_time:支付时间
--refund_time:退款时间
--========================================================================
CREATE TABLE wx_pay_order (
  id CHAR(32) COLLATE utf8_bin NOT NULL,
  user_id CHAR(32) COLLATE utf8_bin NOT NULL,
  shop_id CHAR(32) COLLATE utf8_bin NOT NULL,
  openid CHAR(32) COLLATE utf8_bin NOT NULL,
  out_trade_no VARCHAR(48) COLLATE utf8_bin NULL,
  detail VARCHAR(100) COLLATE utf8_bin NULL,
  attach VARCHAR(200) COLLATE utf8_bin NULL,
  fee_type VARCHAR(16) COLLATE utf8_bin NULL,
  total_fee INTEGER DEFAULT 0,
  real_total_fee INTEGER DEFAULT 0,
  trade_type VARCHAR(10) COLLATE utf8_bin NULL,
  transaction_no VARCHAR(42) COLLATE utf8_bin,
  state VARCHAR(10) COLLATE utf8_bin NOT NULL,
  refund_fee INTEGER DEFAULT 0,
  version INTEGER DEFAULT 0,
  is_delete TINYINT NOT NULL DEFAULT 0,
  create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  pay_time DATETIME,
  refund_time DATETIME,
  PRIMARY KEY (id),
  UNIQUE KEY idx_wx_pay_order_oid(out_trade_no)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--========================================================================
--微信通知统一订单信息
--
--id:编号
--sign:签名
--sign_type:签名类型
--openid:openid
--is_subscribe:是否关注
--trade_type:交易类型
--bank_type:类型
--total_fee:支付金额
--settlement_total_fee:应结订单金额
--fee_type:货币类型
--cash_fee:现金支付金额订单现金
--cash_fee_type:货币类型
--transaction_id:微信支付订单号
--out_trade_no:商户系统内部订单号
--attach:商家数据包
--time_end:支付完成时间
--create_time:创建时间
--========================================================================
CREATE TABLE wx_pay_notify (
  id CHAR(32) COLLATE utf8_bin NOT NULL,
  openid VARCHAR(32) COLLATE utf8_bin NOT NULL,
  sign VARCHAR(50) COLLATE utf8_bin NOT NULL,
  sign_type VARCHAR(20) COLLATE utf8_bin NOT NULL,
  is_subscribe TINYINT DEFAULT FALSE,
  trade_type VARCHAR(20) COLLATE utf8_bin NOT NULL,
  bank_type VARCHAR(20) COLLATE utf8_bin,
  total_fee INTEGER DEFAULT 0,
  settlement_total_fee INTEGER DEFAULT 0,
  fee_type VARCHAR(20) COLLATE utf8_bin NOT NULL,
  cash_fee INTEGER DEFAULT 0,
  cash_fee_type VARCHAR(20) COLLATE utf8_bin NOT NULL,
  transaction_id VARCHAR(60) COLLATE utf8_bin NOT NULL,
  out_trade_no VARCHAR(60) COLLATE utf8_bin NOT NULL,
  attach VARCHAR(200) COLLATE utf8_bin,
  time_end VARCHAR(30) COLLATE utf8_bin,
  create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  INDEX idx_wx_pay_notify_oid(out_trade_no)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--=======================================================================
-- 微信退款

--id:编号
--user_id:用户编号
--shop_id:店铺编号
--openid:微信openid
--out_trade_no:业务订单编号
--refund_id:微信退款编号
--cash_fee:订单总金额
--refund_fee:退款金额
--refund_desc:退款描述
--state:状态
--create_time:创建时间
--success_time:成功时间
--=======================================================================
CREATE TABLE wx_pay_refund (
  id CHAR(32) COLLATE utf8_bin NOT NULL,
  user_id CHAR(32) COLLATE utf8_bin NOT NULL,
  shop_id CHAR(32) COLLATE utf8_bin NOT NULL,
  openid VARCHAR(32) COLLATE utf8_bin NOT NULL,
  out_trade_no VARCHAR(48) COLLATE utf8_bin NOT NULL,
  refund_id VARCHAR(48) COLLATE utf8_bin NULL,
  cash_fee INTEGER DEFAULT 0,
  total_fee INTEGER DEFAULT 0,
  refund_fee INTEGER DEFAULT 0,
  refund_desc VARCHAR(200) COLLATE utf8_bin,
  state VARCHAR(10) COLLATE utf8_bin NOT NULL,
  create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  success_time DATETIME NULL,
  PRIMARY KEY (id),
  UNIQUE KEY idx_wx_pay_refund_oid (out_trade_no),
  INDEX idx_wx_pay_refund_uid (user_id),
  INDEX idx_wx_pay_refund_sid (shop_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--=======================================================================
-- 微信退款通知

--id:编号
--transaction_no:微信交易编号
--out_trade_no:业务订单编号
--out_refund_no:业务退款编号
--refund_id:微信退款编号
--total_fee:支付金额
--settlement_total_fee:当该订单有使用非充值券时，返回此字段
--refund_fee:退款金额
--settlement_refund_fee:退款金额=申请退款金额-非充值代金券退款金额，退款金额
--state:状态,SUCCESS-退款成功;CHANGE-退款异常;REFUNDCLOSE—退款关闭
--success_time:退款成功实践
--refund_recv_accout:退款入账账户
--refund_account:退款资金来源
--refund_request_source:退款发起来源
--create_time:创建时间
--=======================================================================
CREATE TABLE wx_pay_refund_notify (
  id CHAR(32) COLLATE utf8_bin NOT NULL,
  out_trade_no VARCHAR(48) COLLATE utf8_bin NOT NULL,
  transaction_no VARCHAR(48) COLLATE utf8_bin NOT NULL,
  out_refund_no VARCHAR(48) COLLATE utf8_bin NOT NULL,
  refund_id VARCHAR(48) COLLATE utf8_bin NULL,
  total_fee INTEGER DEFAULT 0,
  settlement_total_fee INTEGER DEFAULT 0,
  refund_fee INTEGER DEFAULT 0,
  settlement_refund_fee INTEGER DEFAULT 0,
  state VARCHAR(20) COLLATE utf8_bin NOT NULL,
  success_time VARCHAR(20) COLLATE utf8_bin NULL,
  refund_recv_accout VARCHAR(48) COLLATE utf8_bin NULL,
  refund_account VARCHAR(20) COLLATE utf8_bin NULL,
  refund_request_source VARCHAR(20) COLLATE utf8_bin NULL,
  create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  INDEX idx_wx_pay_refund_notify_oid (out_trade_no)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--=======================================================================
--错误日志表
--
--id:编号
--out_trade_no:交易订单编号
--action:执行动作
--way:支付方式
--message:错误信息
--attach:附加信息
--create_time:创建时间
--=====================================================================
CREATE TABLE wx_pay_error_log (
  id CHAR(32) COLLATE utf8_bin NOT NULL,
  out_trade_no VARCHAR(48) COLLATE utf8_bin NOT NULL,
  action VARCHAR(20) COLLATE utf8_bin NOT NULL,
  way VARCHAR(20) COLLATE utf8_bin NOT NULL,
  message VARCHAR(200) COLLATE utf8_bin,
  attach VARCHAR(200) COLLATE utf8_bin,
  create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;