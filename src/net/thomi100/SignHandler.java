package net.thomi100;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

/*
 * Created by thomi100 on 18.03.2018.
 */
public class SignHandler implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if(e.getClickedBlock() != null && e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if(e.getClickedBlock().getState() instanceof Sign) {
                Sign sign = (Sign) e.getClickedBlock().getState();
                if(sign.getLine(0).equals(((String) Configuration.get("sign_1"))) &&
                        sign.getLine(1).equals(((String) Configuration.get("sign_2"))) &&
                        sign.getLine(2).equals(((String) Configuration.get("sign_3")))) {
                    World wld = Bukkit.getWorld(sign.getLine(3));
                    if(wld != null) {
                        if(e.getPlayer().hasPermission("RandomTP.sign.use") || e.getPlayer().hasPermission("RandomTP.*")) {
                            Teleport.teleport(e.getPlayer(), wld);
                        } else {
                            e.getPlayer().sendMessage((String) Configuration.get("noPermMsg"));
                        }
                    } else {
                        e.getPlayer().sendMessage((String) Configuration.get("world_not_exists"));
                    }
                }
            }
        }
    }

    @EventHandler
    public void onSignChange(SignChangeEvent e) {
        String line0 = e.getLine(0);
        Player p = e.getPlayer();
        if(line0.equalsIgnoreCase("[RandomTP]") || line0.equalsIgnoreCase("[random]") || line0.equalsIgnoreCase("[rtp]")) {
            if(p.hasPermission("RandomTP.sign.create") || e.getPlayer().hasPermission("RandomTP.*")) {
                String line3 = e.getLine(3);
                if(line3 != null && line3.length() > 0) {
                    e.setLine(0, ((String) Configuration.get("sign_1")));
                    e.setLine(1, ((String) Configuration.get("sign_2")));
                    e.setLine(2, ((String) Configuration.get("sign_3")));
                    return;
                }
                e.getPlayer().sendMessage((String) Configuration.get("specify_world"));
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if(e.getBlock() != null) {
            if(e.getBlock().getState() instanceof Sign) {
                Sign sign = (Sign) e.getBlock().getState();
                if(sign.getLine(0).equals(((String) Configuration.get("sign_1"))) &&
                        sign.getLine(1).equals(((String) Configuration.get("sign_2"))) &&
                        sign.getLine(2).equals(((String) Configuration.get("sign_3")))) {
                    if(e.getPlayer().hasPermission("RandomTP.sign.remove") || e.getPlayer().hasPermission("RandomTP.*")) {
                        if(!e.getPlayer().isSneaking()) {
                            e.getPlayer().sendMessage((String) Configuration.get("sneak_to_destroy"));
                            e.setCancelled(true);
                        }
                    } else {
                        e.setCancelled(true);
                        e.getPlayer().sendMessage((String) Configuration.get("noPermMsg"));
                    }
                }
            }
        }
    }

}
