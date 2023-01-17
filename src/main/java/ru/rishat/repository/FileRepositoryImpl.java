package ru.rishat.repository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.stream.Collectors;

public class FileRepositoryImpl implements FileRepository {
    @Override
    public List<String> readAllLinesFromFile(File file) {
        final BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return bufferedReader.lines().collect(Collectors.toList());
    }
}
