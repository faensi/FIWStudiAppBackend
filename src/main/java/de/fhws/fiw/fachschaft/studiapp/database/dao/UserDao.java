package de.fhws.fiw.fachschaft.studiapp.database.dao;

import de.fhws.fiw.fachschaft.studiapp.models.News;
import de.fhws.fiw.fachschaft.studiapp.models.User;

import java.util.List;

public interface UserDao {
    public User create(User user) throws Exception;

    public User readById(Long id) throws Exception;

    public User readByUsername(String username) throws Exception;

    public List<User> readAll() throws Exception;

    public void deleteById(Long id) throws Exception;

    public void delete(User user) throws Exception;

    void update(User user) throws Exception;
}
