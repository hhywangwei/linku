package com.tuoshecx.server.api.manage.game.form;

import com.tuoshecx.server.game.wheel.domain.BigWheel;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class BigWheelUpdateForm extends BigWheelSaveForm {
    @NotNull
    @Size(min = 1)
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public BigWheel toDomain(String shopId) {
        BigWheel t = super.toDomain(shopId);
        t.setId(id);

        return t;
    }
}
