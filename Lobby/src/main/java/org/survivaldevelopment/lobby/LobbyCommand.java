package org.survivaldevelopment.lobby;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class LobbyCommand extends Command {

    public LobbyCommand() {
        super("lobby", "survivaldevelopment.lobby", "hub");
    }

    private File file;
    private Configuration configuration;

    @Override
    public void execute(CommandSender sender, String[] args) {
        try {
            this.file = new File(Lobby.getInstance().getDataFolder(), "config.yml");
            this.configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String lobby = this.configuration.getString("lobby-server");
        String inlobby = this.configuration.getString("messages.in-lobby");
        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage(new ComponentBuilder("This command can only be run by a player!").color(ChatColor.RED).create());
            return;
        }
        ProxiedPlayer player = (ProxiedPlayer) sender;
        if (player.getServer().getInfo().getName().equalsIgnoreCase(lobby)) {
            player.sendMessage(new ComponentBuilder(inlobby).color(ChatColor.RED).create());
            return;
        }
        ServerInfo target = ProxyServer.getInstance().getServerInfo(lobby);
        player.connect(target);
    }
}
