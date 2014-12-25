package me.mrpowergamerbr.powerjetpack;

import java.util.logging.Level;

import me.mrpowergamerbr.powerjetpack.Listeners.JetpackListener;
import me.mrpowergamerbr.powerjetpack.Utils.PowerUtils;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
	
	@Override
	public void onEnable() {
		getLogger().log(Level.INFO, "PowerJetpack Ativado!");
		saveDefaultConfig();
		PowerUtils.reloadMe();
		getServer().getPluginManager().registerEvents(new JetpackListener(), this);
		JetpackListener.tirarCoisas();
	}
	
	@Override
	public void onDisable() {
		
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (label.equalsIgnoreCase("powerjetpackreload"))
	    {
			if (sender.hasPermission("PowerJetpack.Reload")) {
			PowerUtils.reloadMe();
			sender.sendMessage("�aReload Conclu�do!");
			return true;
			}
			else {
				sender.sendMessage("�cSem �4Permiss�o�c!");
				return true;
			}
	    }
		return false;
	}
    	 
}