package Main;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;

public class Interact implements Listener {
	
	private Main plugin;
	
	private int ticks =0;

	public Interact(Main plugin) {
		this.plugin = plugin;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void PlayerInteractEvent(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			
			Player p = e.getPlayer();
			
			if(p.getItemInHand().getType().equals(Material.OBSIDIAN)) {
				ArmorStand as = (ArmorStand) p.getWorld().spawnEntity(p.getLocation(), EntityType.ARMOR_STAND);
				as.setVisible(false);
				as.setHelmet(new ItemStack(Material.GLOWSTONE));
				as.setVelocity(p.getLocation().getDirection().multiply(1));
				
				new BukkitRunnable() {
					
					double phi = 0;
					
					@Override
					public void run() {		
						
						ticks++;
						
						if(ticks >= 50) {
						
							for(Entity ent : as.getNearbyEntities(5, 5, 5)) {
								if(ent instanceof LivingEntity) {
							
										   double  x = 5 * Math.cos(ticks);
							               double z = 5 * Math.sin(ticks);
							                
							               double x1 = 3 * Math.cos(ticks);
							               double z1 = 3 * Math.sin(ticks);
									 
							               ((LivingEntity) ent).damage(0.5f);
							               Location loc = as.getLocation().add(x,1,z);
							               Location loc2 = as.getLocation().add(x1,1,z1);
							               
							               float angle = (float) ticks;
							               
							               as.setHeadPose(new EulerAngle(0f, angle ,0f));
							               
							               as.getWorld().spawnParticle(Particle.REDSTONE, loc, 1, new Particle.DustOptions(org.bukkit.Color.fromRGB(50, 0, 0), 1));
							               as.getWorld().spawnParticle(Particle.REDSTONE, loc2, 1, new Particle.DustOptions(org.bukkit.Color.fromRGB(50, 0, 0), 1));  

							               ent.setVelocity(ent.getVelocity().clone().add(as.getLocation().clone().toVector().subtract(ent.getLocation().clone().toVector()).multiply(0.05)));
								
							               
							               
							               phi += Math.PI / 10;
							               
							               loc.add(0,0,-1);
							               
							               for( double t = 0; t <=2*Math.PI; t += Math.PI / 40) {
							            	   
							            	   double r = 1.5;
							            	   double x3 = r*Math.cos(t)*Math.sin(phi);
							            	   double y3 = r*Math.cos(phi) + 1.5;
							            	   double z3 = r*Math.sin(t)*Math.sin(phi);
							            	   
							            	   double x4 = 0.08*Math.cos(t);
							            	   double z4 = 0.08*Math.sin(t);
							            	   
							            	   
							            	   Location loc3 = loc.add(x4,0,z4);
							            	   
							            	   Location loc4 =  as.getLocation().add(x3,y3,z3);
							            	   
							            	   as.getWorld().spawnParticle(Particle.REDSTONE, loc3, 1, new Particle.DustOptions(org.bukkit.Color.fromRGB(50, 0, 0), 1));
							            	   as.getWorld().spawnParticle(Particle.REDSTONE, loc4, 1, new Particle.DustOptions(org.bukkit.Color.fromRGB(50, 0, 0), 1));
							            	   loc4.subtract(x3,y3,z3);
							            	   
							        }
								}
							}
						}
						
						if(ticks >= 200) {
							as.getLocation().getWorld().createExplosion(as.getLocation(), 4.0F);
							this.cancel();
							ticks = 0;
							as.remove();
						}
												
					}
				}.runTaskTimer(plugin, 1, 1);
			}
		}
	}

}
