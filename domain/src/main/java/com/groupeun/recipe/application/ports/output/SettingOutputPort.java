package com.groupeun.recipe.application.ports.output;

import com.groupeun.recipe.domain.model.Setting;

import java.util.Map;

public interface SettingOutputPort {

    Map<String, String> importSetting ();
    void exportSetting (Map<String, String> settings);

}
