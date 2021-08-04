//Created by Duckulus on 03 Aug, 2021 

package de.amin.kit;

import de.amin.hardcoregames.HG;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.IOException;

public class KitSetting {

    private Kit kit;
    private String name;
    private String configKey;
    private double defaultValue;
    private double minValue;
    private double maxValue;
    private FileConfiguration config;

    public KitSetting(Kit kit, String name, double defaultValue, double minValue, double maxValue) {
        this.kit = kit;
        this.name = name;
        this.defaultValue = defaultValue;
        this.minValue = minValue;
        this.maxValue = maxValue;
        configKey = "kitsettings." + kit.getName() + "." + name;

        config = HG.INSTANCE.getFileConfig();

        if (config.getString(configKey) == null) {
            try {
                config.set(configKey, defaultValue);
                config.save(HG.INSTANCE.getFile());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static double get(Kit kit, String name){
        return HG.INSTANCE.getFileConfig().getDouble("kitsettings." + kit.getName() + "." + name);
    }

    public double getValue() {
        return config.getDouble(configKey);
    }

    public void setValue(double value) {
        try {
            config.set(configKey, value);
            config.save(HG.INSTANCE.getFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Kit getKit() {
        return kit;
    }

    public void setKit(Kit kit) {
        this.kit = kit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getConfigKey() {
        return configKey;
    }

    public void setConfigKey(String configKey) {
        this.configKey = configKey;
    }

    public double getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(double defaultValue) {
        this.defaultValue = defaultValue;
    }

    public double getMinValue() {
        return minValue;
    }

    public void setMinValue(double minValue) {
        this.minValue = minValue;
    }

    public double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(double maxValue) {
        this.maxValue = maxValue;
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public void setConfig(FileConfiguration config) {
        this.config = config;
    }

    public void restoreDefault() {
        setValue(defaultValue);
    }
}
