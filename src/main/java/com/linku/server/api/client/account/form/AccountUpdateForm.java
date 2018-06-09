package com.linku.server.api.client.account.form;

import com.linku.server.user.domain.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 更新用户账户信息
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@ApiModel("修改用户账户信息")
public class AccountUpdateForm {
    @ApiModelProperty("姓名")
    private String name;
    @ApiModelProperty("昵称")
    private String nickname;
    @ApiModelProperty("联系电话")
    private String phone;
    @ApiModelProperty("性别")
    private String sex;
    @ApiModelProperty("头像")
    private String headImg;
    @ApiModelProperty("省份")
    private String province;
    @ApiModelProperty("城市")
    private String city;
    @ApiModelProperty("区县")
    private String country;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public User toDomain(String id){
        User t = new User();

        t.setId(id);
        t.setName(name);
        t.setNickname(nickname);
        t.setSex(sex);
        t.setHeadImg(headImg);
        t.setPhone(phone);
        t.setProvince(province);
        t.setCity(city);
        t.setCountry(country);

        return t;
    }
}

