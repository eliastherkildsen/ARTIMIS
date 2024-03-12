package org.apollo.template.Service;

import org.apollo.template.Service.Debugger.DebugMessage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigLoader {

    public static ConfigLoader instance;
    private final int DEBUG;
    private final Properties properties = new Properties();
    private final String CONFIG_PATH = "src/main/java/org/apollo/template/Service/Debugger/config.properties";
    private final File CONFIG_FILE = new File(CONFIG_PATH);

    public static void get() {
        if (ConfigLoader.instance == null) {
            ConfigLoader.instance = new ConfigLoader();
        }

    }

    private ConfigLoader() {
        this.DEBUG = Integer.parseInt(loadDebug());
        instance = this;
    }

    private String loadDebug() {
        try {
            properties.load(new FileInputStream(CONFIG_FILE));
            return properties.getProperty("debug");

        } catch (IOException e) {
            DebugMessage.error(this, "Failed to load the config file. " +
                    "This can be due to the file not existing.... ");

            throw new RuntimeException(e);
        }
    }

    public int getDebug(){
        return this.DEBUG;
    }


}
