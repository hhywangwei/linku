--==========================================================================
--修改marketing_record_group表
--==========================================================================
ALTER TABLE marketing_record_group MODIFY COLUMN state ENUM('WAIT', 'ACTIVATE', 'CLOSE', 'SUCCESS') COLLATE utf8_bin NOT NULL;

--===========================================================================
--组团营销营销活动记录结束

--id:编号
--record_id:营销活动记录编号
--item_id:关联明细编号
--action:动作
--state:状态
--message:处理消息
--create_time:创建时间
--===========================================================================
CREATE TABLE marketing_record_group (
  id CHAR(32) COLLATE utf8_bin NOT NULL,
  record_id CHAR(32) COLLATE utf8_bin NOT NULL,
  item_id CHAR(32) COLLATE utf8_bin NOT NULL,
  action VARCHAR(20) COLLATE utf8_bin NOT NULL,
  state ENUM('WAIT', 'SUCCESS', 'FAIL') COLLATE utf8_bin NOT NULL;
  message VARCHAR(200) COLLATE utf8_bin,
  create_time DATETIME NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY uniq_record_id_item_id (record_id, item_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;