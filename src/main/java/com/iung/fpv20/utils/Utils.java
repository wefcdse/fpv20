package com.iung.fpv20.utils;

import java.util.function.Function;

public class Utils {
    public static <Obj,T> T requireNonNullOr(Obj obj, Function<Obj,T> f,T default_value) {
        if (obj == null)
            return default_value;
        return f.apply(obj);
    }
}
