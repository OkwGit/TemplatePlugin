package fr.groupez.template.commands;

import fr.groupez.api.command.VCommand;
import fr.groupez.api.zcore.ZPlugin;
import fr.groupez.api.zcore.enums.Permission;
import fr.groupez.api.zcore.utils.commands.CommandType;

public class CommandTemplate extends VCommand {

	public CommandTemplate(ZPlugin plugin) {
		super(plugin);
		this.setPermission(Permission.EXAMPLE_PERMISSION);
		this.addSubCommand(new CommandTemplateReload(plugin));
	}

	@Override
	protected CommandType perform(ZPlugin plugin) {
		syntaxMessage();
		return CommandType.SUCCESS;
	}

}
