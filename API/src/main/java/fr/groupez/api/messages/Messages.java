package fr.groupez.api.messages;

import fr.groupez.api.messages.types.ClassicMessage;
import fr.groupez.api.messages.types.ZMessage;
import fr.groupez.api.zcore.ZPlugin;

import java.util.*;

public enum Messages {

    PREFIX("§8(§6Template§8) "),

    INVENTORY_CLONE_NULL("§cThe inventory clone is null!"),
    INVENTORY_OPEN_ERROR("§cAn error occurred with the opening of the inventory §6%id%§c."),
    TIME_DAY("%02d %day% %02d %hour% %02d %minute% %02d %second%"),
    TIME_HOUR("%02d %hour% %02d minute(s) %02d %second%"),
    TIME_MINUTE("%02d %minute% %02d %second%"),
    TIME_SECOND("%02d %second%"),

    FORMAT_SECOND("second"), FORMAT_SECONDS("seconds"),

    FORMAT_MINUTE("minute"), FORMAT_MINUTES("minutes"),

    FORMAT_HOUR("hour"), FORMAT_HOURS("hours"),

    FORMAT_DAY("d"), FORMAT_DAYS("days"),

    COMMAND_SYNTAXE_ERROR("§cYou must execute the command like this§7: §a%syntax%"),
    COMMAND_NO_PERMISSION("§cYou do not have permission to run this command."),
    COMMAND_NO_CONSOLE("§cOnly one player can execute this command."),
    COMMAND_NO_ARG("§cImpossible to find the command with its arguments."),
    COMMAND_SYNTAXE_HELP("§f%syntax% §7» §7%description%"),

    RELOAD("§aYou have just reloaded the configuration files."),

    DESCRIPTION_RELOAD("Reload configuration files"),

    ;

    private ZPlugin plugin;
    private List<ZMessage> messages = new ArrayList<>();

    Messages(String message) {
        this(MessageType.TCHAT, message);
    }

    Messages(MessageType messageType, String message) {
        this.messages.add(new ClassicMessage(messageType, Collections.singletonList(message)));
    }

    Messages(String... message) {
        this(MessageType.TCHAT, message);
    }

    Messages(MessageType messageType, String... messages) {
        this.messages.add(new ClassicMessage(messageType, Arrays.asList(messages)));
    }

    Messages(ZMessage... essentialsMessages) {
        this.messages = Arrays.asList(essentialsMessages);
    }

    public static Messages fromString(String string) {
        try {
            return valueOf(string);
        } catch (Exception ignored) {
            return null;
        }
    }

    public List<ZMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<ZMessage> messages) {
        this.messages = messages;
    }

    public String toConfigurationName() {
        return name().replace("_", "-").toLowerCase();
    }

    public String getMessageAsString() {
        String configurationName = this.toConfigurationName();
        if (this.messages.isEmpty()) {
            this.plugin.getLogger().severe(configurationName + " is empty ! Check your configuration");
            return "Error with " + configurationName + ", check your console";
        }
        ZMessage essentialsMessage = this.messages.get(0);
        if (essentialsMessage instanceof ClassicMessage classicMessage) {

            if (classicMessage.messages().isEmpty()) {
                this.plugin.getLogger().severe(configurationName + " message is empty ! Check your configuration");
                return "Error with " + configurationName + ", check your console";
            }

            return classicMessage.messages().get(0);
        }

        this.plugin.getLogger().severe(configurationName + " is not a tchat message ! Check your configuration");
        return "Error with " + configurationName + ", check your console";
    }

    public void setPlugin(ZPlugin plugin) {
        this.plugin = plugin;
    }

    public List<String> getMessageAsStringList() {
        return this.messages.stream().filter(essentialsMessage -> essentialsMessage instanceof ClassicMessage).map(essentialsMessage -> (ClassicMessage) essentialsMessage).map(ClassicMessage::messages).flatMap(List::stream).toList();
    }

}

