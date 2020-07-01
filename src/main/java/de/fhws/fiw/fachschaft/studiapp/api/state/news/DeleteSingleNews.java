package de.fhws.fiw.fachschaft.studiapp.api.state.news;

import de.fhws.fiw.fachschaft.studiapp.database.config.DaoFactory;
import de.fhws.fiw.fachschaft.studiapp.models.News;
import de.fhws.fiw.sutton.api.states.put.AbstractPutState;
import de.fhws.fiw.sutton.database.results.NoContentResult;
import de.fhws.fiw.sutton.database.results.SingleModelResult;

import javax.ws.rs.core.MediaType;

public class DeleteSingleNews extends AbstractPutState<News> {
    public DeleteSingleNews() {
    }

    @Override
    protected SingleModelResult<News> loadModel() {
        try {
            return new SingleModelResult<>(DaoFactory.getInstance().getNewsDao().read(this.modelToUpdate.getId()));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected NoContentResult updateModel() {
        try {
             DaoFactory.getInstance().getNewsDao().delete(this.modelToUpdate);
            return new NoContentResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void defineTransitionLinks() {
        addLink(NewsUri.REL_PATH_ID, NewsRelTypes.GET_ALL_NEWS, MediaType.APPLICATION_JSON,
                this.modelToUpdate.getId());
    }

}
