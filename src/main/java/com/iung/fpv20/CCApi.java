package com.iung.fpv20;

import com.iung.fpv20.globals.AllChannels;
import dan200.computercraft.api.ComputerCraftAPI;
import dan200.computercraft.api.lua.*;
import dan200.computercraft.core.filesystem.FileMount;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;
import java.util.Set;

public class CCApi {
    public static boolean isClassExist(String className, ClassLoader loader) {
        try {
            loader.loadClass(className);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public static void init_safe() {
        if (isClassExist("dan200.computercraft.api.ComputerCraftAPI", CCApi.class.getClassLoader()))
            ComputerCraftAPI.registerAPIFactory(Api::new);
    }
}


class Api implements ILuaAPI {
    static String[] TARGET_GLO = {"fpv20"};


    public Api(IComputerSystem iComputerSystem) {
    }


    @Override
    public String[] getNames() {
        return TARGET_GLO;
    }


    @LuaFunction
    public final String dbg(ILuaContext ctx, IArguments args) throws LuaException {
        return "ok";
    }

    @LuaFunction
    public final void fill_channel(String name, double value) {
        AllChannels.ALL_CHANNELS.put(name, new AllChannels.ChannelInfo((float) value));
    }

    @LuaFunction
    public final boolean channel_exist(String name) {
        return AllChannels.ALL_CHANNELS.containsKey(name);
    }

    @LuaFunction
    public final Set<String> channels() {
        return AllChannels.ALL_CHANNELS.keySet();
    }

    @LuaFunction
    public final float channel_value(String name) {
        var ci = AllChannels.ALL_CHANNELS.get(name);
        if (ci == null) {
            return 0;
        } else {
            return ci.value;
        }
    }

    @LuaFunction
    @Nullable
    public final String nb(ILuaContext ctx, IArguments arguments) throws LuaException {
        if (arguments.count() > 0) {
            return "args";
        }
        return null;
    }


    @Override
    public void startup() {
        ILuaAPI.super.startup();
    }
}