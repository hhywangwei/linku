package com.tuoshecx.server.api.sys.wx.vo;

import com.tuoshecx.server.shop.domain.ShopWxAuthorized;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 店铺托管微信小程序公众号信息输出对象
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@ApiModel(value = "店铺托管微信小程序公众号信息输出对象")
public class ShopWxInfoVo {
    @ApiModelProperty(value = "true:有托管公众号信息,false:无托管公众号信息")
    private final Boolean has;
    @ApiModelProperty(value = "托管公众号信息")
    private final ShopWxAuthorized info;

    private ShopWxInfoVo(Boolean has, ShopWxAuthorized info){
        this.has = has;
        this.info = info;
    }

    public Boolean getHas() {
        return has;
    }

    public ShopWxAuthorized getInfo() {
        return info;
    }

    public static ShopWxInfoVo has(ShopWxAuthorized info){
        return new ShopWxInfoVo(true, info);
    }

    public static ShopWxInfoVo noHas(){
        return new ShopWxInfoVo(false, null);
    }
}
