package ru.rishat.service;

import ru.rishat.repository.FileRepository;
import ru.rishat.repository.FileRepositoryImpl;

public class FileServiceImpl implements FileService {
    FileRepository fileRepository = new FileRepositoryImpl();
}
