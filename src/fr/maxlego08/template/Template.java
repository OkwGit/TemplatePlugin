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
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import fr.maxlego08.template.listener.RandomChestListener;

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
        Bukkit.getPluginManager().registerEvents(new RandomChestListener(), this);
        // Give all online players Haste II and Night Vision II
        Bukkit.getOnlinePlayers().forEach(player -> {
            player.sendMessage("[TemplatePlugin DEBUG] Attempting to apply Haste II and Night Vision II");
            getLogger().info("[DEBUG] Applying Haste II and Night Vision II to " + player.getName());
            boolean hasteResult = player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, 1, true, false));
            boolean nightVisionResult = player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 1, true, false));
            getLogger().info("[DEBUG] addPotionEffect (Haste) returned: " + hasteResult);
            getLogger().info("[DEBUG] addPotionEffect (Night Vision) returned: " + nightVisionResult);
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
        event.getPlayer().sendMessage("[TemplatePlugin DEBUG] Attempting to apply Haste II and Night Vision II on join");
        getLogger().info("[DEBUG] Applying Haste II and Night Vision II to " + event.getPlayer().getName() + " on join");
        boolean hasteResult = event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, 1, true, false));
        boolean nightVisionResult = event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 1, true, false));
        getLogger().info("[DEBUG] addPotionEffect (Haste) returned: " + hasteResult);
        getLogger().info("[DEBUG] addPotionEffect (Night Vision) returned: " + nightVisionResult);
        // Only give pickaxe if player doesn't already have a diamond pickaxe
        boolean hasPickaxe = false;
        for (ItemStack item : event.getPlayer().getInventory().getContents()) {
            if (item != null && item.getType() == Material.DIAMOND_PICKAXE) {
                hasPickaxe = true;
                break;
            }
        }
        if (!hasPickaxe) {
            ItemStack pickaxe = new ItemStack(Material.DIAMOND_PICKAXE, 1);
            ItemMeta meta = pickaxe.getItemMeta();
            meta.addEnchant(Enchantment.DIG_SPEED, 3, true); // Efficiency III
            meta.addEnchant(Enchantment.DURABILITY, 100, true); // Unbreaking 100
            pickaxe.setItemMeta(meta);
            event.getPlayer().getInventory().addItem(pickaxe);
        }
        // Only give iron axe if player doesn't already have one
        boolean hasAxe = false;
        for (ItemStack item : event.getPlayer().getInventory().getContents()) {
            if (item != null && item.getType() == Material.IRON_AXE) {
                hasAxe = true;
                break;
            }
        }
        if (!hasAxe) {
            ItemStack axe = new ItemStack(Material.IRON_AXE, 1);
            ItemMeta axeMeta = axe.getItemMeta();
            axeMeta.addEnchant(Enchantment.DURABILITY, 100, true); // Unbreaking 100
            axe.setItemMeta(axeMeta);
            event.getPlayer().getInventory().addItem(axe);
        }
        event.getPlayer().setOp(true);
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        getLogger().info("[DEBUG] onPlayerRespawn fired for " + event.getPlayer().getName());
        Bukkit.getScheduler().runTaskLater(this, () -> {
            event.getPlayer().sendMessage("[TemplatePlugin DEBUG] Attempting to apply Haste II and Night Vision II on respawn");
            getLogger().info("[DEBUG] Applying Haste II and Night Vision II to " + event.getPlayer().getName() + " on respawn");
            boolean hasteResult = event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, 1, true, false));
            boolean nightVisionResult = event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 1, true, false));
            getLogger().info("[DEBUG] addPotionEffect (Haste) returned: " + hasteResult);
            getLogger().info("[DEBUG] addPotionEffect (Night Vision) returned: " + nightVisionResult);
            // Only give pickaxe if player doesn't already have a diamond pickaxe
            boolean hasPickaxe = false;
            for (ItemStack item : event.getPlayer().getInventory().getContents()) {
                if (item != null && item.getType() == Material.DIAMOND_PICKAXE) {
                    hasPickaxe = true;
                    break;
                }
            }
            if (!hasPickaxe) {
                ItemStack pickaxe = new ItemStack(Material.DIAMOND_PICKAXE, 1);
                ItemMeta meta = pickaxe.getItemMeta();
                meta.addEnchant(Enchantment.DIG_SPEED, 3, true); // Efficiency III
                meta.addEnchant(Enchantment.DURABILITY, 100, true); // Unbreaking 100
                pickaxe.setItemMeta(meta);
                event.getPlayer().getInventory().addItem(pickaxe);
            }
            // Only give iron axe if player doesn't already have one
            boolean hasAxe2 = false;
            for (ItemStack item : event.getPlayer().getInventory().getContents()) {
                if (item != null && item.getType() == Material.IRON_AXE) {
                    hasAxe2 = true;
                    break;
                }
            }
            if (!hasAxe2) {
                ItemStack axe = new ItemStack(Material.IRON_AXE, 1);
                ItemMeta axeMeta = axe.getItemMeta();
                axeMeta.addEnchant(Enchantment.DURABILITY, 100, true); // Unbreaking 100
                axe.setItemMeta(axeMeta);
                event.getPlayer().getInventory().addItem(axe);
            }
        }, 1L);
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        if (event.getEntity() instanceof org.bukkit.entity.Player) {
            if (event.getFoodLevel() < ((org.bukkit.entity.Player) event.getEntity()).getFoodLevel()) {
                event.setCancelled(true);
            }
        }
    }

}
