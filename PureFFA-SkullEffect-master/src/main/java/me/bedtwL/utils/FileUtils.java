package me.bedtwL.utils;

import me.bedtwL.ffa.api.PureAPI;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FileUtils {
    @Deprecated
    public static List<String> getAvailableNbsFiles() {
        File[] files = new File(PureAPI.getPlugin().getDataFolder()+"/Addons/SkullEffect").listFiles((dir, name) -> name.endsWith(".nbs"));
        if (files == null) return Collections.emptyList();

        List<String> list = new ArrayList<>();
        for (File file : files) {
            list.add(file.getName());
        }
        return list;
    }
}
