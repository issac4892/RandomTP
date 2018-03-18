package net.thomi100;

import org.bukkit.Bukkit;
import org.bukkit.World;
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

        Configuration.setDefaults(this);
        Configuration.load(this);

        Bukkit.getPluginManager().registerEvents(new SignHandler(), this);

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equalsIgnoreCase("randomtp")) {

            if(args.length == 0) {
                if(!(sender instanceof Player) || (boolean) Configuration.get("enable_default_command")) {
                    sender.sendMessage("§aRandomTP enabled on version §e" + getDescription().getVersion() + "§a by §e" + getDescription().getAuthors().get(0) + "§a.");
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
                        if(sender.hasPermission("RandomTP.teleport")) {
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

            if(args.length == 2) {

                if(args[0].equalsIgnoreCase("teleport")) {
                    if(sender instanceof Player) {
                        Player p = ((Player) sender);
                        World wld = Bukkit.getWorld(args[1]);
                        if(wld != null) {
                            if(sender.hasPermission("RandomTP.teleport.worlds")) {
                                Teleport.teleport(p, wld);
                                return true;
                            }
                            sender.sendMessage((String) Configuration.get("noPermissions"));
                            return true;
                        }
                        sender.sendMessage((String) Configuration.get("world_not_exists"));
                        return true;
                    }
                sender.sendMessage("Command not avaible for console.");
                return true;
                }

            }

            if(args.length == 3) {

                if(args[0].equalsIgnoreCase("teleport")) {
                    if(sender instanceof Player) {
                        Player p = ((Player) sender);
                        World wld = Bukkit.getWorld(args[1]);
                        Player target = Bukkit.getPlayerExact(args[2]);
                        if(wld != null) {
                            if(sender.hasPermission("RandomTP.teleport.other")) {
                                if(target != null) {
                                    Teleport.teleport(p, wld);
                                    return true;
                                }
                                sender.sendMessage((String) Configuration.get("player_not_exists"));
                                return true;
                            }
                            sender.sendMessage((String) Configuration.get("noPermissions"));
                            return true;
                        }
                        sender.sendMessage((String) Configuration.get("world_not_exists"));
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
