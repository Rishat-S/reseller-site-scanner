package ru.rishat.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.stream.Collectors;

import static ru.rishat.constants.Constants.DATA_FROM_FILE;

public class GlobalVarConfig {

    public static void initializingVariables(File configFile) {
        DATA_FROM_FILE = readAllLinesFromFile(configFile);
    }

    private static List<String> readAllLinesFromFile(File file) {
        final BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return bufferedReader.lines().collect(Collectors.toList());
    }
}
