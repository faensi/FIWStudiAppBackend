package de.fhws.fiw.fachschaft.studiapp.database.config;

import de.fhws.fiw.fachschaft.studiapp.database.dao.CoffeeMachineDAO;
import de.fhws.fiw.fachschaft.studiapp.database.dao.NewsDao;
import de.fhws.fiw.fachschaft.studiapp.database.dao.UserDao;
import de.fhws.fiw.fachschaft.studiapp.database.dao.impl.CoffeeMachineDAOImpl;
import de.fhws.fiw.fachschaft.studiapp.database.dao.impl.NewsDaoImpl;
import de.fhws.fiw.fachschaft.studiapp.database.dao.impl.UserDaoImpl;
import lombok.Data;

@Data
public class DaoFactory {
    private static DaoFactory INSTANCE;

    public static final DaoFactory getInstance( )
    {
        if ( INSTANCE == null )
        {
            INSTANCE = new DaoFactory( );
        }

        return INSTANCE;
    }

    private final UserDao userDao;
    private final NewsDao newsDao;
    private final CoffeeMachineDAO coffeemachineDAO;

    private DaoFactory( )
    {
        this.userDao = new UserDaoImpl( );
        this.coffeemachineDAO = new CoffeeMachineDAOImpl();
        this.newsDao = new NewsDaoImpl();
    }
}
