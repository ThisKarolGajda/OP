package com.github.thiskarolgajda.op.utils;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class RandomItemCollector {

    @Contract(value = " -> new", pure = true)
    public static <T> @NotNull Collector<T, ?, T> random() {
        return new Collector<T, List<T>, T>() {
            @Override
            public Supplier<List<T>> supplier() {
                return ArrayList::new;
            }

            @Override
            public BiConsumer<List<T>, T> accumulator() {
                return List::add;
            }

            @Override
            public BinaryOperator<List<T>> combiner() {
                return (left, right) -> {
                    left.addAll(right);
                    return left;
                };
            }

            @Override
            public Function<List<T>, T> finisher() {
                return list -> {
                    if (list.isEmpty()) {
                        return null;
                    }
                    Random random = new Random();
                    return list.get(random.nextInt(list.size()));
                };
            }

            @Override
            public Set<Characteristics> characteristics() {
                return Collections.emptySet();
            }
        };
    }

}
