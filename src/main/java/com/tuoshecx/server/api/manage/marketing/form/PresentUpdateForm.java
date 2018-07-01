package com.tuoshecx.server.api.manage.marketing.form;

import com.tuoshecx.server.marketing.domain.SharePresent;
import io.swagger.annotations.ApiModel;

import javax.validation.constraints.NotBlank;

/**
 * 修改多人行活动提交数据
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@ApiModel("修改多人行活动提交数据")
public class PresentUpdateForm extends PresentSaveForm {
    @NotBlank
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public SharePresent toDomain(String shopId) {

        SharePresent t = super.toDomain(shopId);
        t.setId(id);

        return t;
    }
}
