package me.greenmoon.addon.ffa;

import me.bedtwL.ffa.api.effect.PureKillEffect;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Skull;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.SkullType;

import java.util.Random;

public class SkullKillEffect extends PureKillEffect {
    @Override
    public void killEffect(Location location, Player victim, Player killer) {
        placeSkullAndWeb(location, victim.getName());
        playParticleAndSound(location);
    }

    @Override
    public ItemStack getItemBase() {
        return new ItemStack(Material.WEB, 1, (short) 3);
    }

    @Override
    public String getName() {
        return "skull";
    }

    @Override
    public String getItemNameKey() {
        return "skull";
    }

    private void placeSkullAndWeb(Location location, String playerName) {
        Location skullLoc = location.clone().add(0, 1, 0);
        Block skullBlock = skullLoc.getBlock();

        if (skullBlock.getType() == Material.AIR) {
            skullBlock.setType(Material.SKULL);
            if (skullBlock.getState() instanceof Skull) {
                Skull skull = (Skull) skullBlock.getState();
                skull.setSkullType(SkullType.PLAYER);
                skull.setOwner(playerName);
                skull.update();
            }
        }

        int webs = 0;
        int tries = 0;
        Random rand = new Random();
        while (webs < 4 && tries < 30) {
            int x = skullLoc.getBlockX() + rand.nextInt(3) - 1;
            int y = skullLoc.getBlockY() + rand.nextInt(3) - 1;
            int z = skullLoc.getBlockZ() + rand.nextInt(3) - 1;
            Block b = skullLoc.getWorld().getBlockAt(x, y, z);
            if (b.getType() == Material.AIR) {
                b.setType(Material.WEB);
                webs++;
            }
            tries++;
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                if (skullBlock.getType() == Material.SKULL) {
                    skullBlock.setType(Material.AIR);
                }
                for (int x = -1; x <= 1; x++) {
                    for (int y = -1; y <= 1; y++) {
                        for (int z = -1; z <= 1; z++) {
                            Block b = skullLoc.getWorld().getBlockAt(skullLoc.getBlockX() + x, skullLoc.getBlockY() + y, skullLoc.getBlockZ() + z);
                            if (b.getType() == Material.WEB) {
                                b.setType(Material.AIR);
                            }
                        }
                    }
                }
            }
        }.runTaskLater(Bukkit.getPluginManager().getPlugin("PureFFA"), 100);
    }

    private void playParticleAndSound(Location location) {
        Location effectLoc = location.clone().add(0, 1, 0);
        for (int i = 0; i < 5; i++) {
            effectLoc.getWorld().playEffect(effectLoc, Effect.SMOKE, 0);
            effectLoc.getWorld().playEffect(effectLoc, Effect.EXPLOSION_LARGE, 0);
        }
        effectLoc.getWorld().playSound(effectLoc, Sound.EXPLODE, 1.5f, 1.0f);
        effectLoc.getWorld().playSound(effectLoc, Sound.AMBIENCE_THUNDER, 1.0f, 0.5f);
    }
}
