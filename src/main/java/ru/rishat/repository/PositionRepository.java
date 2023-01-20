package ru.rishat.repository;

import ru.rishat.entity.Position;

import java.io.InputStream;

public interface PositionRepository {
    void saveImageToFile(InputStream in, String photoName);

    void savePosition(Position position);

    void saveAllPositionsToFile();
}
