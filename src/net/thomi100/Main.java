package net.thomi100;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/*
 * Created by thomi100 on 17.03.2018.
 */
public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        System.out.println("[RandomTP] Enabling the plugin...");

        Configuration.setDefaults(this);
        Configuration.load(this);
    }

    @Override
    public void onDisable() {
        System.out.println("[RandomTP] Disabling the plugin...");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equalsIgnoreCase("randomtp")) {

            if(args.length == 0) {
                if(!(sender instanceof Player) || (boolean) Configuration.get("enable_default_command")) {
                    sender.sendMessage(Configuration.get("prefix") + "§aRandomTP enabled on version §e" + getDescription().getVersion() + "§a by §e" + getDescription().getAuthors() + "§a.");
                    return true;
                }
            }

            if(args.length == 1) {

                if(args[0].equalsIgnoreCase("reload")) {
                    if(!(sender instanceof Player) || sender.hasPermission("RandomTP.reload")) {
                        Configuration.setDefaults(this);
                        Configuration.load(this);
                        sender.sendMessage((String) Configuration.get("reloaded"));
                        return true;
                    }
                    sender.sendMessage((String) Configuration.get("noPermissions"));
                    return true;
                }

                if(args[0].equalsIgnoreCase("teleport")) {
                    if(sender instanceof Player) {
                        Player p = ((Player) sender);
                        if(sender.hasPermission("RandomTP.teleport.self")) {
                            Teleport.teleport(p, p.getWorld());
                            return true;
                        }
                        sender.sendMessage((String) Configuration.get("noPermissions"));
                        return true;
                    }
                    sender.sendMessage("Command not avaible for console.");
                    return true;
                }

            }
            sender.sendMessage((String) Configuration.get("help_message"));
            return true;
        }
        return false;
    }


}
