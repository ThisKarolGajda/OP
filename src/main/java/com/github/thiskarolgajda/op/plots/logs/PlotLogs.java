package com.github.thiskarolgajda.op.plots.logs;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import me.opkarol.oplibrary.injection.config.Config;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class PlotLogs {
    @Config(path = "plot")
    private static int maxLogsSize = 50;
    private List<PlotLog> logs = new ArrayList<>();

    public void addLog(PlotLog log) {
        logs.addFirst(log);

        if (logs.size() > maxLogsSize) {
            logs = logs.subList(0, maxLogsSize);
        }
    }

    public void addLog(LocalDateTime time, PlotLogType type, String log) {
        addLog(new PlotLog(time, type, log));
    }

    public List<PlotLog> logs() {
        return logs;
    }
}
