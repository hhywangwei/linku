--=========================================================================
--店铺微信Token

--id:编号
--shop_id:店铺编号
--appid:微信appid
--access_token:微信accessToken
--refresh_token:微信refreshToken
--expires_time:微信accessToken过期时间
--update_time:更新时间
--=========================================================================
CREATE TABLE shop_wx_token (
  id CHAR(32) COLLATE utf8_bin NOT NULL,
  shop_id CHAR(32) COLLATE utf8_bin NOT NULL,
  appid VARCHAR(128) COLLATE utf8_bin NOT NULL,
  access_token CHAR(200) COLLATE utf8_bin NOT NULL,
  refresh_token VARCHAR(200) COLLATE utf8_bin NOT NULL,
  expires_time DATETIME NOT NULL,
  update_time DATETIME NOT NULL,
  PRIMARY KEY (id),
  INDEX idx_shop_id(shop_id),
  UNIQUE KEY uniq_appid (appid)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--========================================================================
--删除店铺配置
--========================================================================
DROP TABLE shop_configure;

--=========================================================================
--店铺关联小程序配置

--id:编号
--shop_id:店铺编号
--appid:微信appid
--nickname:授权方昵称
--head_img:授权方头像
--service_type_info:
--verify_type_info:授权方认证类型，-1代表未认证，0代表微信认证
--username:小程序的原始ID
--name:小程序的主体名称
--business_info：用以了解以下功能的开通状况
--mini_program_info：可根据这个字段判断是否为小程序类型授权
--qrcode_url：二维码图片的URL，开发者最好自行也进行保存
--authorization_info：授权信息
--authorization：true:认证通过,false:未认证通过
--update_time:修改时间
--create_time:创建时间
--=========================================================================
CREATE TABLE shop_wx_configure (
  id CHAR(32) COLLATE utf8_bin NOT NULL,
  shop_id CHAR(32) COLLATE utf8_bin NOT NULL,
  appid VARCHAR(128) COLLATE utf8_bin NOT NULL,
  nickname CHAR(200) COLLATE utf8_bin NOT NULL,
  head_img VARCHAR(200) COLLATE utf8_bin NOT NULL,
  service_type_info TINYINT NOT NULL,
  verify_type_info TINYINT NOT NULL,
  username VARCHAR(200) COLLATE utf8_bin NULL,
  name  VARCHAR(200) COLLATE utf8_bin NULL,
  business_info VARCHAR(200) COLLATE utf8_bin NULL,
  mini_program_info VARCHAR(600) COLLATE utf8_bin NULL,
  qrcode_url VARCHAR(200) COLLATE utf8_bin NULL,
  authorization_info VARCHAR(500) COLLATE utf8_bin NULL,
  authorization TINYINT NOT NULL,
  update_time DATETIME NOT NULL,
  create_time DATETIME NOT NULL,
  PRIMARY KEY (id),
  INDEX idx_shop_id(shop_id),
  UNIQUE KEY uniq_appid (appid)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


--=========================================================================
--店铺关联小程序配置

--id:编号
--shop_id:店铺编号
--appid:微信appid
--nickname:授权方昵称
--head_img:授权方头像
--service_type_info:
--verify_type_info:授权方认证类型，-1代表未认证，0代表微信认证
--username:小程序的原始ID
--name:小程序的主体名称
--business_info：用以了解以下功能的开通状况
--mini_program_info：可根据这个字段判断是否为小程序类型授权
--qrcode_url：二维码图片的URL，开发者最好自行也进行保存
--authorization_info：授权信息
--create_time:创建时间
--=========================================================================
CREATE TABLE shop_wx_unauthorized (
  id CHAR(32) COLLATE utf8_bin NOT NULL,
  shop_id CHAR(32) COLLATE utf8_bin NOT NULL,
  appid VARCHAR(128) COLLATE utf8_bin NOT NULL,
  nickname CHAR(200) COLLATE utf8_bin NOT NULL,
  head_img VARCHAR(200) COLLATE utf8_bin NOT NULL,
  service_type_info TINYINT NOT NULL,
  verify_type_info TINYINT NOT NULL,
  username VARCHAR(200) COLLATE utf8_bin NULL,
  name  VARCHAR(200) COLLATE utf8_bin NULL,
  business_info VARCHAR(200) COLLATE utf8_bin NULL,
  mini_program_info VARCHAR(600) COLLATE utf8_bin NULL,
  qrcode_url VARCHAR(200) COLLATE utf8_bin NULL,
  authorization_info VARCHAR(500) COLLATE utf8_bin NULL,
  create_time DATETIME NOT NULL,
  PRIMARY KEY (id),
  INDEX idx_shop_id(shop_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

