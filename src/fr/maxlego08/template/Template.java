package fr.maxlego08.template;

import fr.maxlego08.template.command.commands.CommandTemplate;
import fr.maxlego08.template.placeholder.LocalPlaceholder;
import fr.maxlego08.template.save.Config;
import fr.maxlego08.template.save.MessageLoader;
import fr.maxlego08.template.zcore.ZPlugin;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * System to create your plugins very simply Projet:
 * <a href="https://github.com/Maxlego08/TemplatePlugin">https://github.com/Maxlego08/TemplatePlugin</a>
 *
 * @author Maxlego08
 */
public class Template extends ZPlugin implements Listener {

    @Override
    public void onEnable() {

        LocalPlaceholder placeholder = LocalPlaceholder.getInstance();
        placeholder.setPrefix("template");

        this.preEnable();

        this.registerCommand("template", new CommandTemplate(this));

        this.addSave(Config.getInstance());
        this.addSave(new MessageLoader(this));

        this.loadFiles();

        this.postEnable();

        // Notify console
        getLogger().info("Template plugin is enabled and working! 2025-5-8 04:26:05VER");
        // Notify all online players
        getLogger().info("[DEBUG] Registering events...");
        Bukkit.getPluginManager().registerEvents(this, this);
        // Give all online players Haste II
        Bukkit.getOnlinePlayers().forEach(player -> {
            player.sendMessage("[TemplatePlugin DEBUG] Attempting to apply Haste II");
            getLogger().info("[DEBUG] Applying Haste II to " + player.getName());
            boolean result = player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, 1, true, false));
            getLogger().info("[DEBUG] addPotionEffect returned: " + result);
        });
    }

    @Override
    public void onDisable() {

        this.preDisable();

        this.saveFiles();

        this.postDisable();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        getLogger().info("[DEBUG] onPlayerJoin fired for " + event.getPlayer().getName());
        event.getPlayer().sendMessage("[TemplatePlugin DEBUG] Attempting to apply Haste II on join");
        getLogger().info("[DEBUG] Applying Haste II to " + event.getPlayer().getName() + " on join");
        boolean result = event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, 1, true, false));
        getLogger().info("[DEBUG] addPotionEffect returned: " + result);
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        getLogger().info("[DEBUG] onPlayerRespawn fired for " + event.getPlayer().getName());
        Bukkit.getScheduler().runTaskLater(this, () -> {
            event.getPlayer().sendMessage("[TemplatePlugin DEBUG] Attempting to apply Haste II on respawn");
            getLogger().info("[DEBUG] Applying Haste II to " + event.getPlayer().getName() + " on respawn");
            boolean result = event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, 1, true, false));
            getLogger().info("[DEBUG] addPotionEffect returned: " + result);
        }, 1L);
    }

}
