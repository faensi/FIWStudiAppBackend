package de.fhws.fiw.fachschaft.studiapp.database.dao.impl;


import de.fhws.fiw.fachschaft.studiapp.database.config.ConnectionManager;
import de.fhws.fiw.fachschaft.studiapp.database.dao.UserDao;
import de.fhws.fiw.fachschaft.studiapp.models.CoffeeMachine;
import de.fhws.fiw.fachschaft.studiapp.models.Role;
import de.fhws.fiw.fachschaft.studiapp.models.User;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class UserDaoImplTest {

    // User user;
    @Test
    public void testCreate() throws Exception {
        UserDao userDao = new UserDaoImpl();
        User userN =  User.builder().kNumber("k41050").name("Julia").accessDate(LocalDateTime.now()).role(Role.ADMIN).isDeleted( false).build();
        User user = userDao.create(userN);
        assertEquals(user, userN);
    }

    @Test
    public void testReadById() throws Exception {
        UserDao userDao = new UserDaoImpl();
        User userN =  User.builder().id(1L).kNumber("k41050").name("Julia").accessDate(LocalDateTime.now()).role(Role.ADMIN).isDeleted( false).build();
        User user = userDao.readById(1L);
        userN.setStatusTime(user.getStatusTime());
        assertEquals(user, userN);
    }

    @Test
    public void testReadAll() throws Exception {
        UserDao userDao = new UserDaoImpl();
        List<User> allNews = userDao.readAll();
        assertFalse(allNews.isEmpty());
    }
}
