package de.fhws.fiw.fachschaft.studiapp.api.state.users;

import de.fhws.fiw.fachschaft.studiapp.database.config.DaoFactory;
import de.fhws.fiw.fachschaft.studiapp.models.User;
import de.fhws.fiw.sutton.api.states.put.AbstractPutState;
import de.fhws.fiw.sutton.database.results.NoContentResult;
import de.fhws.fiw.sutton.database.results.SingleModelResult;

import javax.ws.rs.core.MediaType;

public class DeleteSingleUser extends AbstractPutState<User> {
    public DeleteSingleUser() {
    }

    @Override
    protected SingleModelResult<User> loadModel() {
        try {
            return new SingleModelResult<>(DaoFactory.getInstance().getUserDao().readById(this.modelToUpdate.getId()));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected NoContentResult updateModel() {
        try {
            DaoFactory.getInstance().getUserDao().delete(this.modelToUpdate);
            return new NoContentResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void defineTransitionLinks() {
        addLink(UsersUri.REL_PATH_ID, UsersRelTypes.GET_ALL_USERS, MediaType.APPLICATION_JSON,
                this.modelToUpdate.getId());
    }

}
