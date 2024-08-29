package com.github.thiskarolgajda.op.display;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DisplayBlockParser {

    public static @NotNull List<DisplayBlock> parse(@NotNull String command) {
        String passengersPart = command.substring(command.indexOf("{"));
        return parsePassengers(passengersPart);
    }

    public static @NotNull List<DisplayBlock> parse(String @NotNull ... commands) {
        List<DisplayBlock> displayBlocks = new ArrayList<>();
        for (String command : commands) {
            displayBlocks.addAll(parse(command));
        }

        return displayBlocks;
    }

    private static @NotNull List<DisplayBlock> parsePassengers(String passengersPart) {
        List<DisplayBlock> displayBlocks = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\{id:\"([^\"]+)\",block_state:\\{Name:\"([^\"]+)\",Properties:\\{([^}]*)\\}\\},transformation:\\[([^\\]]+)\\]");
        Matcher matcher = pattern.matcher(passengersPart);

        while (matcher.find()) {
            DisplayBlock displayBlock = new DisplayBlock();
            displayBlock.setId(matcher.group(1));

            StateBlock stateBlock = new StateBlock();
            stateBlock.setName(matcher.group(2));
            String propertiesString = matcher.group(3);
            if (!propertiesString.isEmpty()) {
                String[] propertiesArray = propertiesString.split(",");
                for (String property : propertiesArray) {
                    String[] keyValue = property.split(":");
                    if (keyValue.length == 2) {
                        stateBlock.getProperties().put(keyValue[0].trim(), keyValue[1].trim());
                    }
                }
            }
            displayBlock.setStateBlock(stateBlock);
            String[] transformationStrings = matcher.group(4).split(",");
            float[] transformation = new float[transformationStrings.length];
            for (int i = 0; i < transformationStrings.length; i++) {
                transformation[i] = Float.parseFloat(transformationStrings[i].trim().replace("f", ""));
            }
            displayBlock.setTransformation(transformation);
            displayBlocks.add(displayBlock);
        }

        return displayBlocks;
    }
}