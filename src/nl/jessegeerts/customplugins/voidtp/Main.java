package nl.jessegeerts.customplugins.voidtp;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Jesse on 8-2-2017.
 */
public class Main extends JavaPlugin implements Listener{

    public void onEnable(){
        Bukkit.getServer().getPluginManager().registerEvents(this, this);
        getConfig().addDefault("World", "world");
        getConfig().addDefault("X", 50);
        getConfig().addDefault("Y", 100);
        getConfig().addDefault("Z", 120);
        getConfig().addDefault("Pitch", 0);
        getConfig().addDefault("Yaw", 0);
        saveConfig();
    }


    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){
        Player p = event.getPlayer();
        Location loc = p.getLocation();
        World world = Bukkit.getServer().getWorld(getConfig().getString("World"));

        if (loc.getBlockY() <= 1){
            p.teleport(new Location(world, getConfig().getInt("X"), getConfig().getInt("Y"), getConfig().getInt("Z"), getConfig().getInt("Pitch"), getConfig().getInt("Yaw")));
            event.setCancelled(true);
        }
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandlabel, String[] args){
        if(cmd.getName().equalsIgnoreCase("voidtpreload")){
            reloadConfig();
            sender.sendMessage(ChatColor.GREEN + "Config has been reloaded");
        }


        return true;
    }
}
