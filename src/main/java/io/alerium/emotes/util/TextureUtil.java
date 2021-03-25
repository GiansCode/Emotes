package io.alerium.emotes.util;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import io.alerium.emotes.util.nbt.ItemNBT;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.UUID;

public final class TextureUtil {

    public static ItemStack getTexturedHead(final String textureString) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        final SkullMeta skullMeta = (SkullMeta) item.getItemMeta();

        final TextureType type = TextureType.valueOf(textureString.split(":")[0]);
        final String texture = textureString.split(":")[1];

        switch (type) {
            case NAME:
                skullMeta.setOwner(texture);
                break;
            case UUID:
                skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(UUID.fromString(texture)));
                break;
            case BASE:
                item.setItemMeta(getTexturedMeta(skullMeta, texture));
                break;
        }

        item.setItemMeta(skullMeta);
        item = ItemNBT.setNBTTag(item, "emote-item", "emote-item");

        return item;
    }

    private static SkullMeta getTexturedMeta(final SkullMeta skullMeta, final String texture) {
        final GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures", texture));

        try {
            final Field field = skullMeta.getClass().getDeclaredField("profile");

            field.setAccessible(true);
            field.set(skullMeta, profile);
        } catch (final NoSuchFieldException | IllegalArgumentException | IllegalAccessException ex) {
            ex.printStackTrace();
        }

        return skullMeta;
    }

    private enum TextureType {

        NAME, UUID, BASE

    }

}
