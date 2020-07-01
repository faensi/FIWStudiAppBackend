package de.fhws.fiw.fachschaft.studiapp.api.state.users;

import de.fhws.fiw.fachschaft.studiapp.database.config.DaoFactory;
import de.fhws.fiw.fachschaft.studiapp.models.User;
import de.fhws.fiw.sutton.api.states.post.AbstractPostState;
import de.fhws.fiw.sutton.database.results.NoContentResult;

import javax.ws.rs.core.MediaType;

public class PostUser extends AbstractPostState<User> {
    public PostUser() {
    }

    @Override
    protected NoContentResult saveModel() {
        try {
            DaoFactory.getInstance().getUserDao().create(this.modelToStore);
            return new NoContentResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void defineTransitionLinks() {
        addLink(UsersUri.REL_PATH, UsersRelTypes.GET_ALL_USERS, MediaType.APPLICATION_JSON);
    }
}
