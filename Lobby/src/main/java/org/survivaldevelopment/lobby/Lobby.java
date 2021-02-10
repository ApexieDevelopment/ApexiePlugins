package org.survivaldevelopment.lobby;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public final class Lobby extends Plugin {

    private static Lobby instance;
    private Configuration configuration;

    @Override
    public void onEnable() {
        // Plugin startup logic
        setInstance(this);
        getProxy().getPluginManager().registerCommand(this, new LobbyCommand());

        if (!getDataFolder().exists()) getDataFolder().mkdir();
        String path = "config.yml";
        File file = new File(getDataFolder(), path);
        try {
            if (!file.exists()) {
                Files.copy(getResourceAsStream(path), file.toPath());
            }
            configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setInstance(Lobby instance) {
        Lobby.instance = instance;
    }

    public static Lobby getInstance() {
        return instance;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
