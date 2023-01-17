package ru.rishat.service;

import ru.rishat.repository.FileRepository;
import ru.rishat.repository.FileRepositoryImpl;

import java.io.File;
import java.util.List;

public class FileServiceImpl implements FileService {
    FileRepository fileRepository = new FileRepositoryImpl();

    @Override
    public List<String> readAllLinesFromFile(File file) {
        return fileRepository.readAllLinesFromFile(file);
    }
}
