package com.linku.server.api.manage.game.form;

import com.linku.server.game.wheel.domain.BigWheel;

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
    public BigWheel toDomain() {
        BigWheel t = super.toDomain();
        t.setId(id);

        return t;
    }
}
