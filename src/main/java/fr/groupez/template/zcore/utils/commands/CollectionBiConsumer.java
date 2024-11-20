package fr.groupez.template.zcore.utils.commands;

import org.bukkit.command.CommandSender;

import java.util.List;

@FunctionalInterface
public interface CollectionBiConsumer {
	
	/**
	 * Accept consumer
	 * 
	 * @param sender
	 * @param args
	 * @return list of string
	 */
	List<String> accept(CommandSender sender, String[] args);
	
}
