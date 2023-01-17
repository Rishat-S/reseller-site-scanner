package ru.rishat.repository;

import java.io.File;
import java.util.List;

public interface FileRepository {
    List<String> readAllLinesFromFile(File file);
}
