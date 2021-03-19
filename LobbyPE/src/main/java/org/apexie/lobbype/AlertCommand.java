package org.apexie.lobbype;

import dev.waterdog.ProxyServer;
import dev.waterdog.command.Command;
import dev.waterdog.command.CommandSender;
import dev.waterdog.command.CommandSettings;
import dev.waterdog.utils.Configuration;

public class AlertCommand extends Command {

    public AlertCommand() {
        super("alert", CommandSettings.builder()
                .setDescription("Broadcasts a message to all online players")
                .setPermission("alert.command")
                .setUsageMessage("/alert <message>")
                .build());
    }

    @Override
    public boolean onExecute(CommandSender commandSender, String s, String[] strings) {
        Configuration configuration = LobbyPE.getInstance().getConfig();
        String alertUsage = configuration.getString("messages.alertUsage");
        String alertFormat = configuration.getString("alert-format");
        if(strings.length == 0) {
            commandSender.sendMessage(alertUsage.replace("%alert.usage%", getUsageMessage()));
        } else {
            ProxyServer.getInstance().getPlayers().values().forEach(p -> p.sendMessage(alertFormat.replace("%alert.message%", String.join(" ", strings))));
        }
        return true;
    }
}
