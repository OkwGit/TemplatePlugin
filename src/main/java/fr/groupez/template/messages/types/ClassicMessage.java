package fr.groupez.template.messages.types;

import fr.groupez.template.messages.MessageType;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public record ClassicMessage(MessageType messageType, List<String> messages) implements ZMessage {

    public static ZMessage tchat(String... strings) {
        return new ClassicMessage(MessageType.TCHAT, Arrays.asList(strings));
    }

    public static ZMessage action(String strings) {
        return new ClassicMessage(MessageType.ACTION, Collections.singletonList(strings));
    }

}