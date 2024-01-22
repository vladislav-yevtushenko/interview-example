package org.example.repository;

import java.util.List;
import java.util.UUID;

public interface IRepository {

    int save(Object object);

    Object getById(UUID uuid);

    List<Object> getAll();

}