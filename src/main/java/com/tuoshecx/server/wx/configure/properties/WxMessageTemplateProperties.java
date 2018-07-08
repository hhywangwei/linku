package com.tuoshecx.server.wx.configure.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * 微信消息模板配置
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@ConfigurationProperties(prefix = "wx.small")
public class WxMessageTemplateProperties {
    private List<MessageTemplate> msgTemplates;

    public List<MessageTemplate> getMsgTemplates() {
        return msgTemplates;
    }

    public void setMsgTemplates(List<MessageTemplate> msgTemplates) {
        this.msgTemplates = msgTemplates;
    }

    public static class MessageTemplate {
        private String callKey;
        private String id;
        private List<Integer> keywordIds;
        private String remark;

        public String getCallKey() {
            return callKey;
        }

        public void setCallKey(String callKey) {
            this.callKey = callKey;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public List<Integer> getKeywordIds() {
            return keywordIds;
        }

        public void setKeywordIds(List<Integer> keywordIds) {
            this.keywordIds = keywordIds;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }
}
