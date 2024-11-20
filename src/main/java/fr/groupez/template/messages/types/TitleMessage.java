package fr.groupez.template.messages.types;


import fr.groupez.template.messages.MessageType;

public record TitleMessage(String title, String subtitle, long start, long time,
                           long end) implements ZMessage {

    @Override
    public MessageType messageType() {
        return MessageType.TITLE;
    }
}