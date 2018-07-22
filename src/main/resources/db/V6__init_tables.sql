--=========================================================================
-- 增加微信统一订中预支付编号
--========================================================================
ALTER TABLE wx_pay_order ADD pre_pay VARCHAR(80);
ALTER TABLE wx_pay_order ADD appid VARCHAR(50) NOT NULL;

--=========================================================================
--店铺微信小程序支付配置

--id:编号
--shop_id:店铺编号
--appid:微信appid
--mch_id:微信支付mchId
--pay_key:微信支付key,服务商支付则不需要设置
--create_time:创建时间
--=========================================================================
CREATE TABLE shop_wx_pay (
  id CHAR(32) COLLATE utf8_bin NOT NULL,
  shop_id CHAR(32) COLLATE utf8_bin NOT NULL,
  appid VARCHAR(128) COLLATE utf8_bin NOT NULL,
  mch_id VARCHAR(128) COLLATE utf8_bin NOT NULL,
  pay_key VARCHAR(64) COLLATE utf8_bin,
  create_time DATETIME NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY uniq_shop_id (shop_id),
  UNIQUE KEY uniq_appid (appid)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--========================================================================
--初始测试微信小程序支付配置
--========================================================================
INSERT INTO shop_wx_pay (id, shop_id, appid, mch_id, pay_key, create_time)
        VALUES('1', '393e82297b514996beba689bcc1dc61c', 'wxd94a7c7b59afe68e', '1508275241', 'Hw0Mrm9QAGIn2r9DyxdP6rU1tmXp2CgJ', now());