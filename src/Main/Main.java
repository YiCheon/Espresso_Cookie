package Main;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(command.getName().equals("Espresso_Cookie")) {
			
			getServer().getPluginManager().registerEvents(new Interact(this), this);
			
			return false;
		}
		
		return true;
	}

}
