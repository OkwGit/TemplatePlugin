package fr.groupez.template.zcore.enums;

public enum Permission {

    EXAMPLE_PERMISSION, EXAMPLE_PERMISSION_RELOAD,

    ;

    private final String permission;

    Permission() {
        this.permission = this.name().toLowerCase().replace("_", ".");
    }

    public String asPermission() {
        return permission;
    }

}
