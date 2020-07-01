package de.fhws.fiw.fachschaft.studiapp.api.state.coffeeMachines;

import de.fhws.fiw.fachschaft.studiapp.Start;
public interface CoffeeMachineUri {

    String PATH_ELEMENT = "coffee-machine";
    String REL_PATH = Start.CONTEXT_PATH + "/api/" + PATH_ELEMENT;
    String REL_PATH_ID = REL_PATH + "/{id}";
}
