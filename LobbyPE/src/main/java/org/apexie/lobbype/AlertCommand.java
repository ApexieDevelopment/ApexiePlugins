package org.apexie.lobbype;

import com.nukkitx.protocol.bedrock.data.command.CommandData;
import com.nukkitx.protocol.bedrock.data.command.CommandParamData;
import com.nukkitx.protocol.bedrock.data.command.CommandParamType;
import dev.waterdog.command.Command;
import dev.waterdog.command.CommandSender;
import dev.waterdog.command.CommandSettings;
import dev.waterdog.utils.Configuration;

import java.util.Collections;

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
        String alertFormat = configuration.getString("messages.alert-format");
        if(strings.length == 0) {
            commandSender.sendMessage(alertUsage.replace("%alert.usage%", getUsageMessage()));
        } else {
            LobbyPE.getInstance().getProxy().getPlayers().forEach((uuid, proxiedPlayer) -> {
                proxiedPlayer.sendMessage(alertFormat.replace("%alert.message%", String.join(" ", strings)));
            });
        }
        return true;
    }

    @Override
    public CommandData craftNetwork() {
        CommandParamData[][] parameterData = new CommandParamData[][]{{
                new CommandParamData("message", false, null, CommandParamType.TEXT, null, Collections.emptyList())
        }};
        return new CommandData(this.getName(), this.getDescription(), Collections.emptyList(), (byte) 0, null, parameterData);
    }
}
