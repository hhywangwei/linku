--=========================================================================
--微信消息模板

--id:编号
--appid:微信appid
--global_id:微信模板编号
--call_key:调用key
--template_id:自己模板编号
--remark:备注
--content:模板内容
--example:例子
--create_time:创建时间
--=========================================================================
CREATE TABLE wx_small_msg_template (
  id CHAR(32) COLLATE utf8_bin NOT NULL,
  appid VARCHAR(64) COLLATE utf8_bin NOT NULL,
  global_id VARCHAR(64) COLLATE utf8_bin NOT NULL,
  call_key VARCHAR(64) COLLATE utf8_bin NOT NULL,
  template_id VARCHAR(128) COLLATE utf8_bin NOT NULL,
  remark VARCHAR(200),
  title VARCHAR(100),
  content VARCHAR(1000),
  example VARCHAR(1000),
  create_time DATETIME NOT NULL,
  PRIMARY KEY (id),
  INDEX idx_appid(appid),
  UNIQUE KEY uniq_appid_call_key (appid, call_key)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--===========================================================================
--修改shop_wx_configure表名和结构
--===========================================================================
rename table shop_wx_configure to shop_wx_authorized;
ALTER TABLE shop_wx_authorized MODIFY authorization_info varchar(2000);

--===========================================================================
--修改shop_wx_unauthorized结构
--===========================================================================
ALTER TABLE shop_wx_unauthorized MODIFY authorization_info varchar(2000);

--========================================================================
--小程序发送微信模板消息

--id:编号
--appid:微信appid
--openid:接受用户openid
--inner_id:内部调用编号
--form_id:微信formId
--page:小程序跳转页面
--title:消息标题
--content:发送信息内容
--color:消息颜色
--emphasis_keyword：关键字放大
--status：WAIT:等待发送，SUCCESS：发送成功，FAIL：发送失败
--error:发送错误信息
--create_time:创建时间
--========================================================================
CREATE TABLE wx_msg_message (
  id CHAR(32) NOT NULL,
  appid VARCHAR(100) COLLATE utf8_bin NOT NULL,
  openid CHAR(32) NOT NULL,
  inner_id VARCHAR(20) COLLATE utf8_bin NOT NULL,
  form_id VARCHAR(128) COLLATE utf8_bin NOT NULL,
  page VARCHAR(200) COLLATE utf8_bin,
  title VARCHAR(100) COLLATE utf8_bin DEFAULT "",
  content VARCHAR(1000) COLLATE utf8_bin DEFAULT '' NOT NULL,
  color VARCHAR(32) COLLATE utf8_bin,
  emphasis_keyword VARCHAR(20) COLLATE utf8_bin,
  status ENUM('WAIT', 'SUCCESS', 'FAIL') COLLATE utf8_bin NOT NULL,
  error VARCHAR(300),
  retry TINYINT DEFAULT 0 NOT NULL,
  create_time TIMESTAMP NOT NULL,
  PRIMARY KEY (id),
  INDEX idx_appid(appid),
  INDEX idx_openid(openid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;