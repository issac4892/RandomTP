package net.thomi100;

import org.bukkit.*;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import java.rmi.NoSuchObjectException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
 * Created by thomi100 on 17.03.2018.
 */
public class Teleport {

    private static int getX() {
        int min_x = (int) Configuration.get("min_x");
        int max_x = (int) Configuration.get("max_x");
        boolean lower = false;
        if(min_x < 0) {
            min_x = -min_x;
            max_x = max_x + min_x;
            lower = true;
        }
        int x = new Random().nextInt(max_x + min_x) - min_x;
        if(lower) x -= min_x;
        return x;
    }

    private static int getZ() {
        int min_z = (int) Configuration.get("min_z");
        int max_z = (int) Configuration.get("max_z");
        boolean lower = false;
        if(min_z < 0) {
            min_z = -min_z;
            max_z = max_z + min_z;
            lower = true;
        }
        int z = new Random().nextInt(max_z + min_z) - min_z;
        if(lower) z -= min_z;
        return z;
    }

    public static Location getLocation(World world, int x, int z) {
        Location highest = world.getHighestBlockAt(x, z).getLocation();
        highest.setY(highest.getY() + 0.3);
        return highest;
    }

    public static void teleport(Player p, World world) {
        try {

            ArrayList<Material> disabled_blocks = new ArrayList<>();
            for(String mat : ((List<String>) Configuration.get("disabled_blocks"))) { // TODO
                Material material = Material.getMaterial(mat);
                if(material != null) {
                    disabled_blocks.add(material);
                }
            }

            Location loc = null;
            while(loc == null || disabled_blocks.contains(loc.getBlock().getType()) || disabled_blocks.contains(loc.getBlock().getRelative(BlockFace.DOWN).getType())) {
                loc = getLocation(world, getX(), getZ());
            }

            p.teleport(loc);
            p.sendMessage(((String) Configuration.get("teleport_msg")).replace("{world}", world.getName()));
            if((boolean) Configuration.get("console_msg")) System.out.println("[RandomTP] " + p.getName() + " was teleported into " + world.getName() + " (" + loc.getBlock().getRelative(BlockFace.DOWN).getType().toString() + ", " + loc.getBlockX() + "/" + loc.getBlockY() + "/" + loc.getBlockZ() + ").");
        } catch (NullPointerException e) {

            System.err.println("[RandomTP] Cannot read list disabled_blocks. There may be occured an error while an administrator created the list.");
            p.teleport(getLocation(world, getX(), getZ()));

        } finally {

            playEffects(p);

        }
    }

    public static void playEffects(Player p) {
        if((boolean) Configuration.get("play_sounds")) {
            p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 10, 15);
            p.playSound(p.getLocation(), Sound.ENDERDRAGON_WINGS, 10, 15);
        }
        if((boolean) Configuration.get("show_particles")) {
            p.getWorld().playEffect(p.getLocation(), Effect.ENDER_SIGNAL, 15);
        }
    }

}
