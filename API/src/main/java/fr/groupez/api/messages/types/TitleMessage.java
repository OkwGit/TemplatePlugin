package fr.groupez.api.messages.types;


import fr.groupez.api.messages.MessageType;

public record TitleMessage(String title, String subtitle, long start, long time,
                           long end) implements ZMessage {

    @Override
    public MessageType messageType() {
        return MessageType.TITLE;
    }
}