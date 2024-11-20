package fr.groupez.template;


import fr.groupez.template.commands.commands.CommandTemplate;
import fr.groupez.template.placeholder.LocalPlaceholder;
import fr.groupez.template.save.Config;
import fr.groupez.template.zcore.ZPlugin;

/**
 * System to create your plugins very simple Projet:
 * <a href="https://github.com/Maxlego08/TemplatePlugin">https://github.com/Maxlego08/TemplatePlugin</a>
 *
 * @author Maxlego08
 */
public class ZTemplate extends ZPlugin {

    @Override
    public void onEnable() {

        LocalPlaceholder placeholder = LocalPlaceholder.getInstance();
        placeholder.setPrefix("template");

        this.preEnable();

        this.registerCommand("template", new CommandTemplate(this));

        this.addSave(Config.getInstance());

        this.loadFiles();

        this.postEnable();
    }

    @Override
    public void onDisable() {

        this.preDisable();

        this.saveFiles();

        this.postDisable();
    }

}
