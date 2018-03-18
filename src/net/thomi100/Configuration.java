package net.thomi100;

import org.bukkit.Material;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;

/*
 * Created by thomi100 on 17.03.2018.
 */
public class Configuration {

    public static void setDefaults(Plugin pl) {
        boolean save = false;

        if(pl.getConfig().getString("enable_default_command") == null) {
            pl.getConfig().set("enable_default_command", true);
            save = true;
        }

        if(pl.getConfig().getString("min_x") == null) {
            pl.getConfig().set("min_x", -1000);
            save = true;
        }

        if(pl.getConfig().getString("max_x") == null) {
            pl.getConfig().set("max_x", 1000);
            save = true;
        }

        if(pl.getConfig().getString("min_z") == null) {
            pl.getConfig().set("min_z", -1000);
            save = true;
        }

        if(pl.getConfig().getString("max_z") == null) {
            pl.getConfig().set("max_z", 1000);
            save = true;
        }

        if(pl.getConfig().getString("play_sounds") == null) {
            pl.getConfig().set("play_sounds", true);
            save = true;
        }

        if(pl.getConfig().getString("show_particles") == null) {
            pl.getConfig().set("show_particles", true);
            save = true;
        }

        if(pl.getConfig().getString("console_msg") == null) {
            pl.getConfig().set("console_msg", true);
            save = true;
        }

        if(pl.getConfig().getString("disabled_blocks") == null) {
            ArrayList<String> disabled_blocks = new ArrayList<>();
            disabled_blocks.add(Material.LAVA.toString());
            disabled_blocks.add(Material.STATIONARY_LAVA.toString());
            disabled_blocks.add(Material.WATER.toString());
            disabled_blocks.add(Material.STATIONARY_WATER.toString());
            disabled_blocks.add(Material.WATER_LILY.toString());
            pl.getConfig().set("disabled_blocks", disabled_blocks);
            save = true;
        }

        if(pl.getConfig().getString("reloaded") == null) {
            pl.getConfig().set("reloaded", "&8[&9RandomTP&8] &aReloaded RandomTP.");
            save = true;
        }

        if(pl.getConfig().getString("world_not_exists") == null) {
            pl.getConfig().set("world_not_exists", "&8[&9RandomTP&8] &cThe specified world does not exist.");
            save = true;
        }

        if(pl.getConfig().getString("player_not_exists") == null) {
            pl.getConfig().set("player_not_exists", "&8[&9RandomTP&8] &cThe specified player is not online.");
            save = true;
        }

        if(pl.getConfig().getString("teleport_msg") == null) {
            pl.getConfig().set("teleport_msg", "&8[&9RandomTP&8] &aYou were teleported into the world {world}.");
            save = true;
        }

        if(pl.getConfig().getString("sneak_to_destroy") == null) {
            pl.getConfig().set("sneak_to_destroy", "&8[&9RandomTP&8] &cSneak to remove the sign.");
            save = true;
        }

        if(pl.getConfig().getString("specify_world") == null) {
            pl.getConfig().set("specify_world", "&8[&9RandomTP&8] &cPlease specify a world in line 4.");
            save = true;
        }

        if(pl.getConfig().getString("noPermissions") == null) {
            pl.getConfig().set("noPermissions", "&8[&9RandomTP&8] &cYou don't have eneugh permissions for that.");
            save = true;
        }

        if(pl.getConfig().getString("help_message") == null) {
            pl.getConfig().set("help_message", "&8[&9RandomTP&8] &aHelp for RandomTP Version {version}:\n" +
                    "&8[&9RandomTP&8] &e/randomtp &7 - &aMain command for the plugin.\n" +
                    "&8[&9RandomTP&8] &e/rtp reload &7 - &aReloads the config.\n" +
                    "&8[&9RandomTP&8] &e/rtp teleport &7 - &aTeleports you into your world.\n" +
                    "&8[&9RandomTP&8] &e/rtp teleport <World> &7 - &aTeleports you into the specified world.\n" +
                    "&8[&9RandomTP&8] &e/rtp releport <World> <Player> &7 - &aTeleports the player into the specified world.");
            save = true;
        }

        if(pl.getConfig().getString("sign_1") == null) {
            pl.getConfig().set("sign_1", "&8[&9RandomTP&8]");
            save = true;
        }

        if(pl.getConfig().getString("sign_2") == null) {
            pl.getConfig().set("sign_2", " ");
            save = true;
        }

        if(pl.getConfig().getString("sign_3") == null) {
            pl.getConfig().set("sign_3", "&aRandom TP:");
            save = true;
        }

        if(save) {
            pl.saveConfig();
            System.out.println("[RandomTP] Created some new configuration values.");
            System.out.println("[RandomTP] You might want to configure them.");
        }
    }

    private static HashMap<String, Object> config = new HashMap<>();

    public static void load(Plugin pl) {
        config.clear();
        for(String key : pl.getConfig().getKeys(false)) {
            Object obj = pl.getConfig().get(key);
            if(obj instanceof String) {
                String string = ((String) obj).replace("&", "§");

                string = string.replace("{version}", pl.getDescription().getVersion());
                string = string.replace("{author}", pl.getDescription().getAuthors().get(0));

                config.put(key, string);
            } else {
                config.put(key, obj);
            }
        }
    }

    @Nullable
    public static Object get(String key) {
        return config.getOrDefault(key, null);
    }

}
