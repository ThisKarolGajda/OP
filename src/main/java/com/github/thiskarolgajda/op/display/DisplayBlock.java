package com.github.thiskarolgajda.op.display;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Setter
@Getter
public class DisplayBlock {
    private String id;
    private StateBlock stateBlock;
    private float[] transformation;
    private List<DisplayBlock> passengers;

    public DisplayBlock() {
        this.passengers = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "DisplayBlock{" +
                "stateBlock=" + stateBlock +
                ", id='" + id + '\'' +
                ", transformation=" + Arrays.toString(transformation) +
                ", passengers=" + passengers +
                '}';
    }
}