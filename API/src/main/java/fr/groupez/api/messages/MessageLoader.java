package fr.groupez.api.messages;


import fr.groupez.api.messages.types.BossBarMessage;
import fr.groupez.api.messages.types.ClassicMessage;
import fr.groupez.api.messages.types.TitleMessage;
import fr.groupez.api.messages.types.ZMessage;
import fr.groupez.api.zcore.ZPlugin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.ComponentSerializer;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class MessageLoader {

    private final Locale locale = Locale.getDefault();
    private final ZPlugin plugin;
    private final List<Messages> loadedMessages = new ArrayList<>();

    public MessageLoader(ZPlugin plugin) {
        this.plugin = plugin;
    }

    public void load() {

        File file = new File(plugin.getDataFolder(), "messages.yml");
        this.copyFile();

        this.loadMessages(YamlConfiguration.loadConfiguration(file));

        if (this.loadedMessages.size() != Messages.values().length) {
            this.plugin.getLogger().log(Level.SEVERE, "Messages were not loaded correctly.");
            for (Messages value : Messages.values()) {
                if (!this.loadedMessages.contains(value)) {
                    value.setPlugin(plugin);
                    this.plugin.getLogger().log(Level.SEVERE, value + " was not loaded.");

                    List<ZMessage> newMessages = new ArrayList<>();
                    for (ZMessage message : value.getMessages()) {
                        if (message instanceof ClassicMessage classicMessage) {
                            newMessages.add(new ClassicMessage(classicMessage.messageType(), classicMessage.messages().stream().map(this::replaceMessagesColors).toList()));
                        } else if (message instanceof BossBarMessage bossBarMessage) {
                            newMessages.add(new BossBarMessage(this.replaceMessagesColors(bossBarMessage.text()), bossBarMessage.color(), bossBarMessage.overlay(), bossBarMessage.flags(), bossBarMessage.duration(), bossBarMessage.isStatic()));
                        } else if (message instanceof TitleMessage titleMessage) {
                            newMessages.add(new TitleMessage(this.replaceMessagesColors(titleMessage.title()), this.replaceMessagesColors(titleMessage.subtitle()), titleMessage.start(), titleMessage.time(), titleMessage.end()));
                        }
                    }
                    value.setMessages(newMessages);
                }
            }
        }
    }

    private void copyFile() {

        String messageFileName = "messages";
        String localMessageName = "messages_" + locale.getLanguage();
        if (this.plugin.resourceExist("messages/" + localMessageName + ".yml")) {
            messageFileName = localMessageName;
        }

        this.plugin.saveOrUpdateConfiguration("messages/" + messageFileName + ".yml", "messages.yml", false);
    }

    private void loadMessages(YamlConfiguration configuration) {

        this.loadedMessages.clear();

        for (String key : configuration.getKeys(false)) {

            String messageKey = key.replace("-", "_").toUpperCase();
            try {

                Messages message = Messages.fromString(messageKey);
                if (message == null) {
                    plugin.getLogger().severe("Impossible to find the message " + key + ", it does not exist, you must delete it.");
                    continue;
                }

                message.setPlugin(this.plugin);

                List<ZMessage> essentialsMessages = new ArrayList<>();
                List<Map<?, ?>> mapList = configuration.getMapList(key);

                if (!mapList.isEmpty()) {

                    for (int index = 0; index != mapList.size(); index++) {

                        String path = key + " and index " + (index + 1);
                        Map<?, ?> map = mapList.get(index);
                        MessageType messageType = map.containsKey("type") ? MessageType.fromString((String) map.get("type")) : MessageType.TCHAT;
                        if (messageType == null) {
                            messageType = MessageType.TCHAT;
                            plugin.getLogger().severe("Message type was not found for " + path + ", use TCHAT by default.");
                        }

                        if (messageType == MessageType.BOSSBAR) {

                            String text = getValue(map, "text", path, "Default text", true);
                            String color = getValue(map, "color", path, "WHITE", false);
                            String overlay = getValue(map, "overlay", path, "PROGRESS", false);
                            List<String> flags = getValue(map, "flags", path, new ArrayList<>(), false);
                            long duration = getValue(map, "duration", path, 60L, false);
                            boolean isStatic = getValue(map, "static", path, false, false);

                            BossBarMessage bossBarMessage = new BossBarMessage(text, color, overlay, flags, duration, isStatic);

                            if (bossBarMessage.isValid(this.plugin)) {
                                essentialsMessages.add(bossBarMessage);
                            }

                        } else if (messageType == MessageType.TITLE) {

                            String title = getValue(map, "username", path, "Default username", true);
                            String subtitle = getValue(map, "subtitle", path, "Default subtitle", true);
                            long start = getValue(map, "start", path, 100L, false);
                            long time = getValue(map, "time", path, 2800L, false);
                            long end = getValue(map, "end", path, 100L, false);

                            ZMessage essentialsMessage = new TitleMessage(title, subtitle, start, time, end);
                            essentialsMessages.add(essentialsMessage);
                        } else {

                            List<String> messages = getMessage(map);

                            messages.removeIf(Objects::isNull);
                            if (messages.isEmpty()) {

                                plugin.getLogger().severe("Message is empty for " + key + " and index " + index + ", use default configuration.");
                            } else {

                                ZMessage essentialsMessage = new ClassicMessage(messageType, messages);
                                essentialsMessages.add(essentialsMessage);
                            }
                        }
                    }
                } else if (configuration.contains(key + ".type")) {

                    MessageType messageType = MessageType.fromString(configuration.getString(key + ".type", "TCHAT"));
                    if (messageType == null) {
                        messageType = MessageType.TCHAT;
                        plugin.getLogger().severe("Message type was not found for " + key + ", use TCHAT by default.");
                    }

                    if (messageType == MessageType.TITLE) {

                        String title = configuration.getString(key + ".username", "Default username");
                        String subtitle = configuration.getString(key + ".subtitle", "Default subtitle");
                        long start = configuration.getLong(key + ".start", 100);
                        long time = configuration.getLong(key + ".time", 2800);
                        long end = configuration.getLong(key + ".end", 100);

                        ZMessage essentialsMessage = new TitleMessage(title, subtitle, start, time, end);
                        essentialsMessages.add(essentialsMessage);

                    } else if (messageType == MessageType.BOSSBAR) {

                        String text = configuration.getString(key + ".text", "Default Text");
                        String color = configuration.getString("color", "WHITE");
                        String overlay = configuration.getString("overlay", "PROGRESS");
                        List<String> flags = configuration.getStringList("flags");
                        long duration = configuration.getLong("duration", 60);
                        boolean isStatic = configuration.getBoolean("static", false);

                        BossBarMessage bossBarMessage = new BossBarMessage(text, color, overlay, flags, duration, isStatic);

                        if (bossBarMessage.isValid(this.plugin)) {
                            essentialsMessages.add(bossBarMessage);
                        }

                    } else {

                        List<String> messages = configuration.getStringList(key + ".messages");
                        if (messages.isEmpty()) {
                            messages.add(replaceMessagesColors(configuration.getString(key + ".message")));
                        } else {
                            messages = messages.stream().map(this::replaceMessagesColors).collect(Collectors.toList());
                        }

                        messages.removeIf(Objects::isNull);
                        if (messages.isEmpty()) {

                            plugin.getLogger().severe("Message is empty for " + key + ", use default configuration.");
                        } else {

                            ZMessage essentialsMessage = new ClassicMessage(messageType, messages);
                            essentialsMessages.add(essentialsMessage);
                        }
                    }

                } else {

                    List<String> messages = configuration.getStringList(key);
                    if (messages.isEmpty()) {
                        messages.add(replaceMessagesColors(configuration.getString(key)));
                    } else {
                        messages = messages.stream().map(this::replaceMessagesColors).collect(Collectors.toList());
                    }

                    messages.removeIf(Objects::isNull);
                    if (messages.isEmpty()) {

                        plugin.getLogger().severe("Message is empty for " + key + ", use default configuration.");
                    } else {

                        ZMessage essentialsMessage = new ClassicMessage(MessageType.TCHAT, messages);
                        essentialsMessages.add(essentialsMessage);
                    }
                }

                message.setMessages(essentialsMessages);
                this.loadedMessages.add(message);

            } catch (Exception exception) {
                exception.printStackTrace();
                this.plugin.getLogger().log(Level.SEVERE, messageKey + " key was not found !");
            }
        }

    }

    private String replaceMessagesColors(String message) {
        MiniMessage miniMessage = MiniMessage.miniMessage();
        Component component = miniMessage.deserialize(message);
        String legacy = LegacyComponentSerializer.legacyAmpersand().serialize(component);
        return ChatColor.translateAlternateColorCodes('&', legacy);
    }

    private List<String> getMessage(Map<?, ?> map) {
        List<String> messages = new ArrayList<>();

        for (String key : new String[]{"messages", "message"}) {
            Object value = map.get(key);
            if (value instanceof List<?>) {
                for (Object item : (List<?>) value) {
                    if (item != null) {
                        messages.add(item.toString());
                    }
                }
            } else if (value != null) {
                messages.add(value.toString());
            }
        }

        return messages;
    }

    private <T> T getValue(Map<?, ?> map, String key, String path, T defaultValue, boolean isRequired) {
        if (map.containsKey(key)) {
            Object value = map.get(key);

            // Check for specific types and attempt to cast/convert
            if (defaultValue instanceof Integer && value instanceof Number number) {
                return (T) Integer.valueOf(number.intValue());
            } else if (defaultValue instanceof Long && value instanceof Number number) {
                return (T) Long.valueOf(number.longValue());
            } else if (defaultValue instanceof Double && value instanceof Number number) {
                return (T) Double.valueOf(number.doubleValue());
            } else if (defaultValue instanceof Float && value instanceof Number number) {
                return (T) Float.valueOf(number.floatValue());
            } else if (defaultValue instanceof Boolean && value instanceof Boolean) {
                return (T) value;
            } else if (defaultValue instanceof String && value instanceof String) {
                return (T) value;
            } else if (defaultValue != null && defaultValue.getClass().isInstance(value)) {
                return (T) value;
            } else if (defaultValue == null && value != null) {
                try {
                    return (T) value;
                } catch (ClassCastException ignored) {
                    this.plugin.getLogger().severe("Type mismatch for the key " + key + " for the message " + path);
                }
            } else {
                this.plugin.getLogger().severe("Type mismatch for the key " + key + " for the message " + path);
            }
        }

        if (isRequired) {
            this.plugin.getLogger().severe("Unable to find the key " + key + " for the message " + path);
        }

        return defaultValue;
    }


}