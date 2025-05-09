package fr.maxlego08.template.listener;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.Random;

public class RandomChestListener implements Listener {
    private final Random random = new Random();

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        if (block.getType() == Material.STONE) {
            // 10% chance to spawn a chest
            if (random.nextDouble() < 0.1) {
                Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugins()[0], () -> {
                    block.setType(Material.CHEST);
                    BlockState state = block.getState();
                    if (state instanceof org.bukkit.block.Chest) {
                        Inventory chestInv = ((org.bukkit.block.Chest) state).getBlockInventory();
                        // Optionally add a random item
                        ItemStack reward = new ItemStack(Material.DIAMOND, 1);
                        ItemMeta meta = reward.getItemMeta();
                        meta.setDisplayName("§bLucky Chest Reward");
                        reward.setItemMeta(meta);
                        chestInv.addItem(reward);
                    }
                }, 5L); // 2 ticks = 100ms
            }
        }
    }
}   