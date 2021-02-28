package org.apexie.lobbype;

import dev.waterdog.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class LobbyPE extends Plugin {

    private static LobbyPE instance;

    public static void setInstance(LobbyPE instance) {
        LobbyPE.instance = instance;
    }

    public static LobbyPE getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        setInstance(this);
        // Lobby command
        getProxy().getCommandMap().registerCommand("lobby", new LobbyCommand());
        getProxy().getCommandMap().registerAlias("hub", new LobbyCommand());

        // Alert command
        getProxy().getCommandMap().registerCommand("alert", new AlertCommand());

        if (!getDataFolder().exists()) getDataFolder().mkdir();
        String path = "config.yml";
        File file = new File(getDataFolder(), path);
        try {
            if (!file.exists()) {
                Files.copy(getResourceFile("config.yml"), file.toPath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
