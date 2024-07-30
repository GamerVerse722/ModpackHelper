package net.gamerverse.modpack.config;

public enum QuoteMode {
    SINGLE_QUOTE("'"),
    DOUBLE_QUOTE("\"");

    public final String quote;
    QuoteMode(String quote) {
        this.quote = quote;
    }
}
