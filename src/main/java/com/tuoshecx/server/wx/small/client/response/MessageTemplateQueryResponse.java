package com.tuoshecx.server.wx.small.client.response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MessageTemplateQueryResponse extends WxSmallResponse {
    private final List<Template> templates;

    @SuppressWarnings("unchecked")
    public MessageTemplateQueryResponse(Map<String, Object> map){
        super(map);
        List<Object> list = (List<Object>)map.get("list");
        templates = new ArrayList<>(list.size());
        for(Object o : list){
            templates.add(new Template((Map<String, String>)o));
        }
    }

    public List<Template> getTemplates() {
        return templates;
    }

    public static class Template {
        private final String id;
        private final String title;
        private final String content;
        private final String example;

        Template(Map<String, String> map){
            this.id = map.get("template_id");
            this.title = map.get("title");
            this.content = map.get("content");
            this.example = map.get("example");
        }

        public String getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getContent() {
            return content;
        }

        public String getExample() {
            return example;
        }
    }
}
