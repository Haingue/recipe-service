package com.groupeun.recipe.application.ports.input;

import com.groupeun.recipe.domain.model.Setting;

public interface SettingInputPort {

    Setting getSetting (String name);

    Setting updateSetting (String name, String value);

    void reloadSetting ();

}
