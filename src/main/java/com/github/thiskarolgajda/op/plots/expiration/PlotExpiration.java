package com.github.thiskarolgajda.op.plots.expiration;

import lombok.Data;
import me.opkarol.oplibrary.tools.TimeUtils;

import static com.github.thiskarolgajda.op.plots.config.PlotConfig.defaultPlotExpirationDays;

@Data
public class PlotExpiration {
    private long expirationUnix = System.currentTimeMillis() + TimeUtils.TimeUnit.DAY.toMilliseconds() * defaultPlotExpirationDays;

    public void addExpiration(long expiration) {
        this.expirationUnix += expiration;
    }

    public String getTimeLeft() {
        return ExpirationConverter.getTimeLeftString(expirationUnix);
    }
}
