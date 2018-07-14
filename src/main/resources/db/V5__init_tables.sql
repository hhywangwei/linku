--=========================================================================
--微信小程序服务域名配置

--id:编号
--request_domain:请求合法域名
--wsrequest_domain:socket请求合法域名
--upload_domain:上传请求合法域名
--download_domain:下载请求合法域名
--web_view_domain:业务域名
--=========================================================================
CREATE TABLE wx_small_domain (
  id CHAR(32) COLLATE utf8_bin NOT NULL,
  request_domain VARCHAR(500) COLLATE utf8_bin NOT NULL,
  wsrequest_domain VARCHAR(500) COLLATE utf8_bin NOT NULL,
  upload_domain VARCHAR(500) COLLATE utf8_bin NOT NULL,
  download_domain VARCHAR(500) COLLATE utf8_bin NOT NULL,
  web_view_domain VARCHAR(500) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--初始配置信息
INSERT INTO wx_small_domain (id, request_domain, wsrequest_domain, upload_domain, download_domain, web_view_domain)
VALUES ('10000', 'https://api.tuoshecx.com', 'https://api.tuoshecx.com', 'https://api.tuoshecx.com', 'https://api.tuoshecx.com', 'https://tuoshecx.com');

--=========================================================================
--微信小程序配置

--id:编号
--template_id:配置版本号
--ext:小程序ext
--ext_pages:小程序ext_pages
--pages:小程序pages
--window:小程序window
--tab_bar:小程序tab_bar
--debug: 是否调试
--create_time: 创建时间
--=========================================================================
CREATE TABLE wx_small_configure (
  id CHAR(32) COLLATE utf8_bin NOT NULL,
  template_id INTEGER NOT NULL,
  ext VARCHAR(500) COLLATE utf8_bin DEFAULT '' NOT NULL,
  ext_pages VARCHAR(500) COLLATE utf8_bin DEFAULT '' NOT NULL,
  pages VARCHAR(500) COLLATE utf8_bin DEFAULT '' NOT NULL,
  window VARCHAR(500) COLLATE utf8_bin DEFAULT '' NOT NULL,
  tab_bar VARCHAR(500) COLLATE utf8_bin DEFAULT '' NOT NULL,
  debug TINYINT DEFAULT 0 NOT NULL,
  create_time DATETIME NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY uniq_template_id (template_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--初始缺省配置
INSERT INTO wx_small_configure (id, template_id, ext, ext_pages, pages, window, tab_bar, debug, create_time)
VALUES ('1000', -1, '', '', '', '', '', 0, now());

--=========================================================================
--微信小程序发布

--id:编号
--shop_id:店铺编号
--appid:微信appid
--is_set_domain:是否设置服务器域
--template_id:配置版本号
--state:发布状态
--remark:备注
--update_time:修改时间
--create_time: 创建时间
--=========================================================================
CREATE TABLE wx_small_deploy (
  id CHAR(32) COLLATE utf8_bin NOT NULL,
  shop_id CHAR(32) COLLATE utf8_bin NOT NULL,
  appid VARCHAR(128) COLLATE utf8_bin NOT NULL,
  is_set_domain TINYINT DEFAULT 0 NOT NULL,
  template_id INTEGER NOT NULL,
  state ENUM('WAIT', 'COMMIT', 'AUDIT', 'PASS', 'REFUSE', 'RELEASE') COLLATE utf8_bin NOT NULL,
  remark VARCHAR(500) COLLATE utf8_bin DEFAULT '' NOT NULL,
  update_time DATETIME NOT NULL,
  create_time DATETIME NOT NULL,
  PRIMARY KEY (id),
  INDEX idx_shop_id (shop_id),
  INDEX idx_appid (appid),
  UNIQUE KEY uniq_appid_template_id(appid, template_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--===========================================================================
--微信小程序发布日志

--id:编号
--deploy_id:发布编号
--action:发布动作
--message:消息
--create_time:创建时间
--============================================================================
CREATE TABLE wx_small_deploy_log (
  id CHAR(32) COLLATE utf8_bin NOT NULL,
  deploy_id CHAR(32) COLLATE utf8_bin NOT NULL,
  action ENUM('SET_DOMAIN', 'COMMIT', 'AUDIT', 'AUDIT_REFUSE', 'AUDIT_PASS', 'RELEASE' ) COLLATE utf8_bin NOT NULL,
  message VARCHAR(800) COLLATE utf8_bin NOT NULL,
  create_time DATETIME NOT NULL,
  PRIMARY KEY (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--===========================================================================
--微信小程序审核配置
--
--id:编号
--shop_id:店铺编号
--appid:微信appid
--title:标题
--address:
--tag:
--first_id:第一分类编号
--first-class;第一分类
--second_id:第二分类编号
--second_class:第二分类
--third_id:第三分类编号
--third_class:第三分类
--create_time:创建时间
--===========================================================================
CREATE TABLE wx_small_audit_configure (
  id CHAR(32) COLLATE utf8_bin NOT NULL,
  shop_id CHAR(32) COLLATE utf8_bin NOT NULL,
  appid VARCHAR(128) COLLATE utf8_bin NOT NULL,
  title VARCHAR(50) COLLATE utf8_bin  NOT NULL,
  adddress VARCHAR(200) COLLATE utf8_bin,
  tag VARCHAR(50) COLLATE utf8_bin,
  first_id INTEGER,
  first_class VARCHAR(50) COLLATE utf8_bin,
  second_id INTEGER,
  second_class VARCHAR(50) COLLATE utf8_bin,
  third_id INTEGER,
  third_class VARCHAR(50) COLLATE utf8_bin,
  create_time DATETIME NOT NULL,
  PRIMARY KEY (id),
  INDEX idx_shop_id (shop_id),
  INDEX idx_appid (appid)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;