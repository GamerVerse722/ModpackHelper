package net.gamerverse.client.blocktypes;

public enum BlockTypes {
    BLOCK("block"),
    FLUID("fluid");

    public final String name;

    BlockTypes(String name) {
        this.name = name;
    }

}
