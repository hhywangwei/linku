--=========================================================================
-- 修改微信推送消息字段
--========================================================================
ALTER TABLE wx_msg_message CHANGE inner_id call_key VARCHAR(64) COLLATE utf8_bin NOT NULL;
ALTER TABLE wx_msg_message CHANGE status state ENUM('WAIT', 'SUCCESS', 'FAIL') COLLATE utf8_bin NOT NULL;