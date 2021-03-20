package io.alerium.emotes.util.message;

import org.bukkit.configuration.file.FileConfiguration;

public enum MessageType {

    CHAT, ACTION_BAR, TITLE, SUBTITLE;

    /**
     * Returns the current selected MessageType or defaults to {@link MessageType#CHAT}
     * if the given string is invalid or missing.
     *
     * @param configuration Our plugin's config.yml
     * @return Current selected message type
     */
    public static MessageType getSelectedType(final FileConfiguration configuration) {
        final String selected = configuration.getString("message.type");
        if (selected == null)
            return CHAT;

        MessageType result = CHAT;
        for (final MessageType type : values()) {
            if (!type.name().equalsIgnoreCase(selected)) {
                continue;
            }

            result = type;
            break;
        }

        return result;
    }

}
