package ru.rishat.controller;

import ru.rishat.service.FileService;
import ru.rishat.service.FileServiceImpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public class FileController {

    FileService fileService = new FileServiceImpl();
   public List<String> readAllLinesFromFile(File file) throws FileNotFoundException {
        return fileService.readAllLinesFromFile(file);
    }
}
