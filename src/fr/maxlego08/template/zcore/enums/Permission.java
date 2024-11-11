package fr.maxlego08.template.zcore.enums;

public enum Permission {

    TEMPLATE_USE,
    TEMPLATE_RELOAD,

    ;

    private final String permission;

    Permission() {
        this.permission = this.name().toLowerCase().replace("_", ".");
    }

    public String getPermission() {
        return permission;
    }

}
