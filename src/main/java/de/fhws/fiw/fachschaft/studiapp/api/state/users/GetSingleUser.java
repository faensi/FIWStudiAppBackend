package de.fhws.fiw.fachschaft.studiapp.api.state.users;

import de.fhws.fiw.fachschaft.studiapp.database.config.DaoFactory;
import de.fhws.fiw.fachschaft.studiapp.models.User;
import de.fhws.fiw.sutton.api.states.get.AbstractGetState;
import de.fhws.fiw.sutton.database.results.SingleModelResult;

import javax.ws.rs.core.MediaType;

public class GetSingleUser extends AbstractGetState<User> {

    public GetSingleUser() {
    }

    @Override
    protected SingleModelResult<User> loadModel() {
        try {
            return new SingleModelResult<>(DaoFactory.getInstance().getUserDao().readById(this.requestedId));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void defineTransitionLinks() {
        addLink(UsersUri.REL_PATH_ID, UsersRelTypes.UPDATE_SINGLE_USER, MediaType.APPLICATION_JSON,
                this.requestedId);
        addLink(UsersUri.REL_PATH_ID, UsersRelTypes.DELETE_SINGLE_USER, MediaType.APPLICATION_JSON,
                this.requestedId);
    }
}
