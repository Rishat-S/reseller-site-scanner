package ru.rishat.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public interface FileService {
    public List<String> readAllLinesFromFile(File file) throws FileNotFoundException;
}
