package computercraft.commands;

public enum Peripherals {
    UNIVERSAL_SCANNER("universal_scanner");

    private String id;

    Peripherals(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
