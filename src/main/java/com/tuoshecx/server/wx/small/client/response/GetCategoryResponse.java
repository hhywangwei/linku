package com.tuoshecx.server.wx.small.client.response;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GetCategoryResponse extends WxSmallResponse {
    private final List<ProgramCategory> categories;

    @SuppressWarnings("unchecked")
    public GetCategoryResponse(Map<String, Object> data){
        super(data);
        List<Map<String, Object>> items = (List<Map<String, Object>>)data.get("category_list");
        this.categories = items.stream().map(ProgramCategory::new).collect(Collectors.toList());
    }

    public List<ProgramCategory> getCategories() {
        return categories;
    }

    public static class ProgramCategory {
        private final String firstClass;
        private final String secondClass;
        private final String thirdClass;
        private final Integer firstId;
        private final Integer secondId;
        private final Integer thirdId;

        ProgramCategory(Map<String, Object> item){
            this.firstClass = (String)item.getOrDefault("first_class", "");
            this.secondClass = (String)item.getOrDefault("second_class", "");
            this.thirdClass = (String)item.getOrDefault("third_class", "");
            this.firstId = (Integer)item.getOrDefault("first_id", -1);
            this.secondId = (Integer)item.getOrDefault("second_id", -1);
            this.thirdId = (Integer)item.getOrDefault("third_id", -1);
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
