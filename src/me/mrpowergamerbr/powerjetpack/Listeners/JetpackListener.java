package me.mrpowergamerbr.powerjetpack.Listeners;

import java.util.ArrayList;

import me.mrpowergamerbr.powerjetpack.Utils.PowerUtils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class JetpackListener extends PowerUtils implements Listener {
	static ArrayList<Player> plist = new ArrayList<Player>();
	
	public static void tirarCoisas() {
		getServer().getScheduler().runTaskTimer(getPlugin(), new Runnable() {
			// plz, why deprecation ;-; i cri evry tmy
			@SuppressWarnings("deprecation")
			public void run() {
				player:
				for (Player p : Bukkit.getOnlinePlayers()) {
					if (plist.contains(p)) {
						// Para os Safados que tiram a Jetpack durante o uso...
						if (p.getEquipment().getChestplate() == null || p.getEquipment().getChestplate().getType() != PowerUtils.jetpack) {
							p.sendMessage(PowerUtils.tirouJetpack);
							plist.remove(p);
							p.setAllowFlight(false);
						}
						else {
							if (!(p.isOnGround())) {
									for (String j : PowerUtils.jetpackType) {
										String[] split = j.split(":");
										if (p.hasPermission(split[0])) {
											Material type = Material.valueOf(split[1]);
											if (p.getInventory().contains(type, Integer.parseInt(split[2]))) {
												for (String m : PowerUtils.mundosBlock) {
													if (p.getWorld().getName().equalsIgnoreCase(m)) {
														p.sendMessage(PowerUtils.naoPodeMundo);
														plist.remove(p);
														p.setAllowFlight(false);
														continue player;
													}
												}
												p.sendMessage(PowerUtils.woosh);
												PowerUtils.removeInventoryItems(p.getInventory(), type, Integer.parseInt(split[2]));
												continue player;
											}
											else {
												p.sendMessage(PowerUtils.combustivel);
												plist.remove(p);
												p.setAllowFlight(false);
												continue player;
											}
										}
									}
								}
							}
						}
					}
				}	
		}, PowerUtils.verifyTime * 20L, PowerUtils.verifyTime * 20L);
	}
	
	@EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=true)
	private void onSHIFT(final InventoryClickEvent e) {
		getServer().getScheduler().runTaskLater(getPlugin(), new Runnable() {
			@Override
			public void run() {
				if (plist.contains(e.getWhoClicked())) {
					if (e.getWhoClicked().getInventory().getChestplate() == null || e.getWhoClicked().getInventory().getChestplate().getType() != Material.CHAINMAIL_CHESTPLATE) {
						Player p = (Player) e.getWhoClicked();
						p.sendMessage(PowerUtils.tirouJetpack);
						plist.remove(e.getWhoClicked());
						p.setAllowFlight(false);
					}
				}
			}
		}, 1L);
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
					// Verificar se o Player está no Chão
					if (e.getPlayer().isOnGround()) {
						for (String j : PowerUtils.jetpackType) {
							e.getPlayer().sendMessage("verificando as parada doida");
							String[] split = j.split(":");
							if (e.getPlayer().hasPermission(split[0])) {
								Material type = Material.valueOf(split[1]);
								if (!(e.getPlayer().getAllowFlight())) {
									if (e.getPlayer().getInventory().contains(type, Integer.parseInt(split[2]))) {
										for (String m : PowerUtils.mundosBlock) {
											if (e.getPlayer().getWorld().getName().equalsIgnoreCase(m)) {
												e.getPlayer().sendMessage(PowerUtils.naoPodeMundo);
												return;
											}
										}
										e.getPlayer().setAllowFlight(true);
										e.getPlayer().sendMessage(PowerUtils.voandoJetpack);
										plist.add(e.getPlayer());
										return;
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
									return;
								}
							}
						}
						e.getPlayer().sendMessage(PowerUtils.semPerm);
						return;
					}
					return;
			}
		}
	}

}
