package me.greenmoon.addon.ffa;

import me.bedtwL.ffa.api.EffectAddon;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.UUID;

public class SkullKillEffectMain implements EffectAddon {

    @Override
    public Integer getAPIVer() {return 1;}

    @Override
    public void onEnable() {
        new SkullKillEffect().registerKillEffect(this);
    }

    @Override
    public void onDisable() {
    }

    public UUID authorUUID() {
        return UUID.fromString("53aaa7fb-569e-4391-9323-5762af38f255");
    }

    @Override
    public String getName() {
        return "SkullKillEffect";
    }

    @Override
    public String getAuthor() {
        return "GreenMoon";
    }
}
