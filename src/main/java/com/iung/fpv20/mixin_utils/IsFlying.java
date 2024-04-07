package com.iung.fpv20.mixin_utils;

public interface IsFlying {
    default boolean get_is_flying(){
        return false;
    }
    default void set_is_flying(boolean v){

    }

    Object get_obj();
    void set_obj(Object obj);
}
