package de.fhws.fiw.fachschaft.studiapp.api.state.users;

import de.fhws.fiw.fachschaft.studiapp.Start;

public interface UsersUri {
    String PATH_ELEMENT = "users";
    String REL_PATH = Start.CONTEXT_PATH + "/api/" + PATH_ELEMENT;
    String REL_PATH_ID = REL_PATH + "/{id}";
}
