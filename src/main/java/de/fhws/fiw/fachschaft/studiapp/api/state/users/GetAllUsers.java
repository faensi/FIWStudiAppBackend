package de.fhws.fiw.fachschaft.studiapp.api.state.users;

import de.fhws.fiw.fachschaft.studiapp.database.config.DaoFactory;
import de.fhws.fiw.fachschaft.studiapp.models.User;
import de.fhws.fiw.sutton.api.queries.AbstractQuery;
import de.fhws.fiw.sutton.api.states.get.AbstractGetCollectionState;
import de.fhws.fiw.sutton.database.DatabaseException;
import de.fhws.fiw.sutton.database.results.CollectionModelResult;

import javax.ws.rs.core.MediaType;
import java.util.Collections;

public class GetAllUsers extends AbstractGetCollectionState<User> {
    public GetAllUsers() {
    }

    @Override
    protected void defineTransitionLinks() {
        addLink(UsersUri.REL_PATH, UsersRelTypes.CREATE_USER, MediaType.APPLICATION_JSON);
        addLink(UsersUri.REL_PATH, UsersRelTypes.GET_SINGLE_USER, MediaType.APPLICATION_JSON);
    }

    public static class GetAllUsersQuery extends AbstractQuery<User> {
        @Override
        protected CollectionModelResult<User> doExecuteQuery() throws DatabaseException {
            try {
                return new CollectionModelResult<>(Collections.checkedCollection(DaoFactory.getInstance().getUserDao().readAll(), User.class));
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}
