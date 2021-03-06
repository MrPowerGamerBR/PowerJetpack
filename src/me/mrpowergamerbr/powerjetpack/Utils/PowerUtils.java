package me.mrpowergamerbr.powerjetpack.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;

public class PowerUtils {
	public static Material fuel = Material.COAL;
	public static Material jetpack = Material.CHAINMAIL_CHESTPLATE;
	public static String voandoJetpack = null;
	public static String chaoJetpack = null;
	public static String semPerm = null;
	public static String tirouJetpack = null;
	public static String woosh = null;
	public static String combustivel = null;
	public static String semCombustivel = null;
	public static String naoPodeMundo = null;
	public static Integer fuelQuantity = 1;
	public static Integer verifyTime = 3;
	public static ArrayList<String> mundosBlock = new ArrayList<String>();
	public static ArrayList<String> jetpackType = new ArrayList<String>();
	
	public static Server getServer()
	  {
	    return Bukkit.getServer();
	  }
	  
	  public static FileConfiguration getConfig()
	  {
	    return getPlugin().getConfig();
	  }
	  
	  public static Plugin getPlugin()
	  {
	    return Bukkit.getPluginManager().getPlugin("PowerJetpack");
	  }
	  
	  public static Logger getLogger()
	  {
		  return Bukkit.getLogger();
	  }
	  
	  public static void saveConfig()
	  {
		  getPlugin().saveConfig();
		  return;
	  }
	  
	  public static void reloadConfig()
	  {
		  getPlugin().reloadConfig();
		  return;
	  }
	  
	  public static File getDataFolder()
	  {
		  return getPlugin().getDataFolder();
	  }
	  
	// Unchecked = mundosBlock
	@SuppressWarnings("unchecked")
	public static void reloadMe()
	  {
		  reloadConfig();
		  //fuel = //Material.valueOf(getConfig().getString("Jetpack.Fuel"));
		  jetpack = Material.valueOf(getConfig().getString("Jetpack.Jetpack"));
		  voandoJetpack = getConfig().getString("Mensagens.VoandoJetpack").replace("&", "�");
		  chaoJetpack = getConfig().getString("Mensagens.ChaoJetpack").replace("&", "�");
		  semPerm = getConfig().getString("Mensagens.SemPerm").replace("&", "�");
		  tirouJetpack = getConfig().getString("Mensagens.TirouJetpack").replace("&", "�");
		  woosh = getConfig().getString("Mensagens.Woosh").replace("&", "�");
		  combustivel = getConfig().getString("Mensagens.Combustivel").replace("&", "�");
		  naoPodeMundo = getConfig().getString("Mensagens.NaoPodeMundo").replace("&", "�");
		  semCombustivel = getConfig().getString("Mensagens.SemCombustivel").replace("&", "�");
		  mundosBlock = (ArrayList<String>) getConfig().getList("Jetpack.MundosBloqueados");
		  fuelQuantity = getConfig().getInt("Jetpack.Quantidade");
		  verifyTime = getConfig().getInt("Jetpack.VerifyTime");
		  jetpackType.clear();
		  Set<String> j = getConfig().getConfigurationSection("Jetpack.Jetpacks").getKeys(false);
		  for (String s : j) {
			  jetpackType.add(getConfig().getString("Jetpack.Jetpacks." + s + ".Permissao") + ":" + getConfig().getString("Jetpack.Jetpacks." + s + ".Fuel") + ":" + getConfig().getString("Jetpack.Jetpacks." + s + ".Quantidade"));
			  getLogger().log(Level.INFO, s);
			  getLogger().log(Level.INFO, getConfig().getString("Jetpack.Jetpacks." + s + ".Permissao") + ":" + getConfig().getString("Jetpack.Jetpacks." + s + ".Fuel") + ":" + getConfig().getString("Jetpack.Jetpacks." + s + ".Quantidade"));
		  }
		  
	  }
	  
	  public static void removeInventoryItems(PlayerInventory inv, Material type, int amount) {
		  for (ItemStack is : inv.getContents()) {
			  if (is != null && is.getType() == type) {
				  int newamount = is.getAmount() - amount;
				  if (newamount > 0) {
					  is.setAmount(newamount);
					  break;
				  } else {
					  inv.remove(is);
					  amount = -newamount;
					  if (amount == 0) break;
				  }
			  }
		  }
	  }
}