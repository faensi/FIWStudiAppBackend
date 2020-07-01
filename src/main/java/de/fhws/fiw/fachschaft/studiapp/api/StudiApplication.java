package de.fhws.fiw.fachschaft.studiapp.api;

import de.fhws.fiw.fachschaft.studiapp.api.service.CoffeeMachineService;
import de.fhws.fiw.fachschaft.studiapp.api.service.DispatcherService;
import de.fhws.fiw.fachschaft.studiapp.api.service.NewsService;
import de.fhws.fiw.fachschaft.studiapp.api.service.UserService;
import de.fhws.fiw.fachschaft.studiapp.models.CoffeeMachine;
import de.fhws.fiw.sutton.api.AbstractApplication;

import javax.ws.rs.ApplicationPath;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath( "api" )
public class StudiApplication extends AbstractApplication {
    @Override
    protected Set<Class<?>> getServiceClasses() {

        final Set<Class<?>> returnValue = new HashSet<>( );

        returnValue.add( UserService.class );
        returnValue.add( DispatcherService.class );
        returnValue.add( NewsService.class );
        returnValue.add( CoffeeMachineService.class );

        return returnValue;
    }
}
