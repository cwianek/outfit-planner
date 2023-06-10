package com.outfit.planner.system.outfit.service.utils;

import java.util.ArrayList;
import java.util.List;

public class Softmanx {
    public static List<Double> softmax(int size) {
        List<Integer> inputs = new ArrayList<>();
        for (int i = size; i >= 1; i--) {
            inputs.add(i);
        }

        List<Double> result = new ArrayList<>();
        double sum = 0.0;

        for (Integer input : inputs) {
            double exp = Math.exp(input);
            result.add(exp);
            sum += exp;
        }

        for (int i = 0; i < result.size(); i++) {
            result.set(i, result.get(i) / sum);
        }

        return result;
    }

}
