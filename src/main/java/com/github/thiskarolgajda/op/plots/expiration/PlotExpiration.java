package com.github.thiskarolgajda.op.plots.expiration;

import lombok.Data;

@Data
public class PlotExpiration {
    private long expirationUnix;

    public void addExpiration(long expiration) {
        this.expirationUnix += expiration;
    }
}
