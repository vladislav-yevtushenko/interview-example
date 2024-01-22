package org.example.repository;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;

public class InputRepository implements IRepository {

    private Dao<Object, UUID> dao;

    public InputRepository() throws SQLException {

        dao = DaoManager.createDao(new JdbcConnectionSource("jdbc:h2:mem:myDB"), Object.class);
    }

    public int save(Object object) {
        try {
            return dao.create(object);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Object getById(UUID uuid) {
        try {
            return dao.queryForId(uuid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Object> getAll() {
        try {
            return dao.queryForAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}