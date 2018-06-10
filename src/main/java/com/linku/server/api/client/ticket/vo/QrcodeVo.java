package com.linku.server.api.client.ticket.vo;

import com.linku.server.ticket.domain.Eticket;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 电子券二维码
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@ApiModel("支付二维码")
public class QrcodeVo {
    @ApiModelProperty(value = "电子券")
    private final Eticket eticket;
    @ApiModelProperty(value = "支付验证码")
    private final String valCode;

    public QrcodeVo(Eticket eticket, String valCode) {
        this.eticket = eticket;
        this.valCode = valCode;
    }

    public Eticket getEticket() {
        return eticket;
    }

    public String getValCode() {
        return valCode;
    }
}
