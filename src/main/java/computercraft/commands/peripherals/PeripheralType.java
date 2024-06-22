package computercraft.commands.peripherals;

public enum PeripheralType {
    UNIVERSAL_SCANNER("universal_scanner");

    private String id;

    PeripheralType(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
