package net.thomi100;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nullable;
import java.util.HashMap;

/*
 * Created by thomi100 on 17.03.2018.
 */
public class Configuration {

    public static void setDefaults(Plugin pl) {
        boolean save = false;

        if(pl.getConfig().getString("prefix") == null) {
            pl.getConfig().set("prefix", "&8[&9RandomTP&8] &r");
            save = true;
        }

        if(pl.getConfig().getString("enable_default_command") == null) {
            pl.getConfig().set("enable_default_command", "true");
            save = true;
        }

        if(pl.getConfig().getString("help_message") == null) {
            pl.getConfig().set("help_message", "{prefix} &aHelp for RandomTP Version {version}: " +
                    "{prefix} &e/randomtp &7 - &amain command");
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

                if(((String) obj).contains("{prefix}") && config.containsKey("prefix")) {
                    string = string.replace("{prefix}", (String) config.get("prefix"));
                }

                string = string.replace("{version}", pl.getDescription().getVersion());

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
