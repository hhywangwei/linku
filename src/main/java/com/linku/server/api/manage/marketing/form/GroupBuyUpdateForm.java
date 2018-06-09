package com.linku.server.api.manage.marketing.form;

import com.linku.server.marketing.domain.GroupBuy;
import io.swagger.annotations.ApiModel;

import javax.validation.constraints.NotBlank;

/**
 * 修改团购活动提交数据
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@ApiModel("修改营销活动提交数据")
public class GroupBuyUpdateForm extends GroupBuySaveForm {
    @NotBlank
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public GroupBuy toDomain(String shopId) {

        GroupBuy t = super.toDomain(shopId);
        t.setId(id);

        return t;
    }
}
