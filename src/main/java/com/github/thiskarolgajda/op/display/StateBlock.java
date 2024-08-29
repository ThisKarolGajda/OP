package com.github.thiskarolgajda.op.display;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
public class StateBlock {
    private String name;
    private Map<String, String> properties = new HashMap<>();
}