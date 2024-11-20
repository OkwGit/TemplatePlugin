package fr.groupez.template.commands.commands;

import fr.groupez.template.commands.VCommand;
import fr.groupez.template.zcore.ZPlugin;
import fr.groupez.template.messages.Message;
import fr.groupez.template.zcore.enums.Permission;
import fr.groupez.template.zcore.utils.commands.CommandType;

public class CommandTemplateReload extends VCommand {

	public CommandTemplateReload(ZPlugin plugin) {
		super(plugin);
		this.setPermission(Permission.EXAMPLE_PERMISSION_RELOAD);
		this.addSubCommand("reload", "rl");
		this.setDescription(Message.DESCRIPTION_RELOAD);
	}

	@Override
	protected CommandType perform(ZPlugin plugin) {
		
		plugin.reloadFiles();
		message(sender, Message.RELOAD);
		
		return CommandType.SUCCESS;
	}

}
