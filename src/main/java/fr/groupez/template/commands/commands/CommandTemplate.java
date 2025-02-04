package fr.groupez.template.commands.commands;

import fr.groupez.template.ZTemplate;
import fr.groupez.template.commands.VCommand;
import fr.groupez.template.zcore.ZPlugin;
import fr.groupez.template.zcore.enums.Permission;
import fr.groupez.template.zcore.utils.commands.CommandType;

public class CommandTemplate extends VCommand {

	public CommandTemplate(ZPlugin plugin) {
		super(plugin);
		this.setPermission(Permission.EXAMPLE_PERMISSION);
		this.addSubCommand(new CommandTemplateReload(plugin));
	}

	@Override
	protected CommandType perform(ZTemplate plugin) {
		syntaxMessage();
		return CommandType.SUCCESS;
	}

}
