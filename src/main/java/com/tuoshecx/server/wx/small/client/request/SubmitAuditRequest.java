package com.tuoshecx.server.wx.small.client.request;

import java.util.ArrayList;
import java.util.List;

 public class SubmitAuditRequest extends WxSmallRequest {
    private final List<SubmitAuditItem> items;

     public SubmitAuditRequest(String token) {
         super(token);
         this.items = new ArrayList<>();
     }

    public void addItem(SubmitAuditItem item){
        items.add(item);
    }

    public List<SubmitAuditItem> getItems() {
        return items;
    }

    public static class SubmitAuditItem {
        private final String title;
        private final String address;
        private final String tag;
        private final String firstClass;
        private final String secondClass;
        private final String thirdClass;
        private final Integer firstId;
        private final Integer secondId;
        private final Integer thirdId;

        public SubmitAuditItem(String title, String address, String tag, String firstClass,
                               String secondClass, String thirdClass, Integer firstId, Integer secondId, Integer thirdId) {
            this.title = title;
            this.address = address;
            this.tag = tag;
            this.firstClass = firstClass;
            this.secondClass = secondClass;
            this.thirdClass = thirdClass;
            this.firstId = firstId;
            this.secondId = secondId;
            this.thirdId = thirdId;
        }

        public String getTitle() {
            return title;
        }

        public String getAddress() {
            return address;
        }

        public String getTag() {
            return tag;
        }

        public String getFirstClass() {
            return firstClass;
        }

        public String getSecondClass() {
            return secondClass;
        }

        public String getThirdClass() {
            return thirdClass;
        }

        public Integer getFirstId() {
            return firstId;
        }

        public Integer getSecondId() {
            return secondId;
        }

        public Integer getThirdId() {
            return thirdId;
        }
    }
}
