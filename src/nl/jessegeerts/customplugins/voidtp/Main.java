package nl.jessegeerts.customplugins.voidtp;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Jesse on 8-2-2017.
 */
public class Main extends JavaPlugin implements Listener{
    
    private Location spawn;
    
    //This is just to don't write getConfig() over and over again.
    private FileConfiguration config;

    public void onEnable(){
        getLogger().info("This plugin was made by Jesse Geerts");
        Bukkit.getServer().getPluginManager().registerEvents(this, this);
        getConfig().addDefault("World", "world");
        getConfig().addDefault("X", 50);
        getConfig().addDefault("Y", 100);
        getConfig().addDefault("Z", 120);
        getConfig().addDefault("Pitch", 0);
        getConfig().addDefault("Yaw", 0);
        getConfig().options().copyDefaults(true); //Forgot about this, whooops!
        saveConfig();
        
        config = getConfig();
        loadLocation();
    }


    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){
        Player player = event.getPlayer();
        Location loc = player.getLocation();
        
        if (loc.getBlockY() <= 1){
        	if (!player.getWorld().equals(spawn.getWorld())) return;
            event.setCancelled(true);
            player.teleport(spawn);
        }
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandlabel, String[] args){
        if(cmd.getName().equalsIgnoreCase("voidtpreload")){
            reloadConfig();
            loadLocation();
            sender.sendMessage(ChatColor.GREEN + "Config has been reloaded");
        }
        else if (cmd.getName().equalsIgnoreCase("setvoidspawn")) {
            if (!(sender instanceof Player)) sender.sendMessage(ChatColor.RED + "You need to be a Player in order to do this!");
            else {
                Player player = (Player) sender;   
                
                Location location = player.getLocation();
                spawn = location;
                
                config.set("World", location.getWorld().getName());
                config.set("X", (int) location.getX());
                config.set("Y", (int) location.getY());
                config.set("Z", (int) location.getZ());
                config.set("Pitch", (double) location.getPitch());
                config.set("Yaw", (double) location.getYaw());
                saveConfig();
                
                player.sendMessage(ChatColor.GREEN + "Spawn successfully set!");
            }
        }

        return true;
    }
    
    private void loadLocation() {
        World world = Bukkit.getWorld(config.getString("World"));
        spawn = new Location(world, config.getInt("X"), config.getInt("Y"), config.getInt("Z"));
        spawn.setYaw((float) config.getDouble("Yaw"));
        spawn.setPitch((float) config.getDouble("Pitch"));
    }
}
