package de.fhws.fiw.fachschaft.studiapp.api.state;

import de.fhws.fiw.fachschaft.studiapp.api.state.coffeeMachines.CoffeeMachineRelTypes;
import de.fhws.fiw.fachschaft.studiapp.api.state.coffeeMachines.CoffeeMachineUri;
import de.fhws.fiw.fachschaft.studiapp.api.state.news.NewsRelTypes;
import de.fhws.fiw.fachschaft.studiapp.api.state.news.NewsUri;
import de.fhws.fiw.fachschaft.studiapp.api.state.users.UsersRelTypes;
import de.fhws.fiw.fachschaft.studiapp.api.state.users.UsersUri;
import de.fhws.fiw.fachschaft.studiapp.models.User;
import de.fhws.fiw.sutton.api.states.get.AbstractGetDispatcherState;

import javax.ws.rs.core.MediaType;

public class DispatcherState  extends AbstractGetDispatcherState {
    @Override
    protected void defineTransitionLinks() {
        addLink( NewsUri.REL_PATH, NewsRelTypes.CREATE_NEWS, MediaType.APPLICATION_JSON);
        addLink(NewsUri.REL_PATH, NewsRelTypes.GET_ALL_NEWS, MediaType.APPLICATION_JSON);
        addLink( UsersUri.REL_PATH, UsersRelTypes.CREATE_USER, MediaType.APPLICATION_JSON);
        addLink(UsersUri.REL_PATH, UsersRelTypes.GET_ALL_USERS, MediaType.APPLICATION_JSON);
        addLink( CoffeeMachineUri.REL_PATH, CoffeeMachineRelTypes.CREATE_COFFEE_MACHINE, MediaType.APPLICATION_JSON );
        addLink( CoffeeMachineUri.REL_PATH, CoffeeMachineRelTypes.GET_ALL_STATES, MediaType.APPLICATION_JSON );
    }
}
