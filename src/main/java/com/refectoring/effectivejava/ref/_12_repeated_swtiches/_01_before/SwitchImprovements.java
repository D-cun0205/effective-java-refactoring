package com.refectoring.effectivejava.ref._12_repeated_swtiches._01_before;

public class SwitchImprovements {

    public int vacationHours(String type) {
        int result;
        switch (type) {
            case "full-time": result = 120;
            case "part-time": result = 80;
            case "temporal": result = 32;
            default: result = 0;
        }
        return result;
    }
}
