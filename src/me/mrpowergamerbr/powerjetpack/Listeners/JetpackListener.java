package me.mrpowergamerbr.powerjetpack.Listeners;

import java.util.ArrayList;

import me.mrpowergamerbr.powerjetpack.Utils.PowerUtils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class JetpackListener extends PowerUtils implements Listener {
	static ArrayList<Player> plist = new ArrayList<Player>();
	
	public static void tirarCoisas() {
		getServer().getScheduler().runTaskLater(getPlugin(), new Runnable() {
			// plz, why deprecation ;-; i cri evry tmy
			@SuppressWarnings("deprecation")
			public void run() {
				for (Player p : Bukkit.getOnlinePlayers()) {
					if (plist.contains(p)) {
						// Para os Safados que tiram a Jetpack durante o uso...
						if (p.getEquipment().getChestplate() == null || p.getEquipment().getChestplate().getType() != PowerUtils.jetpack) {
							p.sendMessage(PowerUtils.tirouJetpack);
							plist.remove(p);
							p.setAllowFlight(false);
						}
						else {
							if (p.getInventory().contains(PowerUtils.fuel)) {
								for (String m : PowerUtils.mundosBlock) {
									if (p.getWorld().getName().equalsIgnoreCase(m)) {
										p.sendMessage(PowerUtils.naoPodeMundo);
										plist.remove(p);
										p.setAllowFlight(false);
										return;
									}
								}
								p.sendMessage(PowerUtils.woosh);
								PowerUtils.removeInventoryItems(p.getInventory(), PowerUtils.fuel, 1);
							}
							else {
								p.sendMessage(PowerUtils.combustivel);
								plist.remove(p);
								p.setAllowFlight(false);
							}
						}
					}
				}
				tirarCoisas();
			}	
		}, 60L);
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=true)
	private void onSHIFT(PlayerToggleSneakEvent e)
	{
		// Só ative a Jetpack se o Player apertou SHIFT (PlayerToggleSneakEvent acontece duas vezes, quando você aperta o SHIFT,
		// e depois quando você solta o SHIFT
		if (e.getPlayer().isSneaking() == false) {
			// Só verifique a Jetpack se o Player está vestindo uma Jetpack (Default: Peitoral de Chain)
			if (e.getPlayer().getEquipment().getChestplate() != null && e.getPlayer().getEquipment().getChestplate().getType() == PowerUtils.jetpack) {
				// Permissões Marotas
				if (e.getPlayer().hasPermission("PowerJetpack.UsarJetpack")) {
					// Verificar se o Player está no Chão
					if (e.getPlayer().isOnGround()) {
						if (!(e.getPlayer().getAllowFlight())) {
							if (e.getPlayer().getInventory().contains(PowerUtils.fuel)) {
								for (String m : PowerUtils.mundosBlock) {
									if (e.getPlayer().getWorld().getName().equalsIgnoreCase(m)) {
										e.getPlayer().sendMessage(PowerUtils.naoPodeMundo);
										return;
									}
								}
								e.getPlayer().setAllowFlight(true);
								e.getPlayer().sendMessage(PowerUtils.voandoJetpack);
								plist.add(e.getPlayer());
							}
							else {
								e.getPlayer().sendMessage(PowerUtils.semCombustivel);
								return;
							}
						}
						else {
							e.getPlayer().setAllowFlight(false);
							e.getPlayer().sendMessage(PowerUtils.chaoJetpack);
							plist.remove(e.getPlayer());
						}
					}
				}
				else {
					e.getPlayer().sendMessage(PowerUtils.semPerm);
				}
			}
		}
	}

}
