package ru.rishat.repository;

import ru.rishat.entity.Position;

import java.io.InputStream;

public interface PositionRepository {
    void savePosition(Position position);

    void saveAllPositionsToFile();
}
