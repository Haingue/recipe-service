package com.groupeun.recipe.domain.exception;

import java.util.UUID;

public class SettingNotFound extends DomainException {
    public SettingNotFound() {
        super("Setting not found");
    }

    public SettingNotFound(String name) {
        super(String.format("Setting[name=%s] not found", name));
    }
}
