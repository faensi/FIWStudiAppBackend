package de.fhws.fiw.fachschaft.studiapp.api.state.news;

import de.fhws.fiw.fachschaft.studiapp.Start;

public interface NewsUri {
    String PATH_ELEMENT = "news";
    String REL_PATH = Start.CONTEXT_PATH + "/api/" + PATH_ELEMENT;
    String REL_PATH_ID = REL_PATH + "/{id}";
}
