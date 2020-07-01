package de.fhws.fiw.fachschaft.studiapp.api.state.news;

import de.fhws.fiw.fachschaft.studiapp.database.config.DaoFactory;
import de.fhws.fiw.fachschaft.studiapp.models.News;
import de.fhws.fiw.sutton.api.states.get.AbstractGetState;
import de.fhws.fiw.sutton.database.results.SingleModelResult;

import javax.ws.rs.core.MediaType;

public class GetSingleNews extends AbstractGetState<News> {

    public GetSingleNews( )
    {
    }

    @Override protected SingleModelResult<News> loadModel( )
    {
        try {
            return new SingleModelResult<>(DaoFactory.getInstance( ).getNewsDao( ).read( this.requestedId ));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override protected void defineTransitionLinks( )
    {
        addLink( NewsUri.REL_PATH_ID, NewsRelTypes.UPDATE_SINGLE_NEWS, MediaType.APPLICATION_JSON,
                this.requestedId );
        addLink( NewsUri.REL_PATH_ID, NewsRelTypes.DELETE_SINGLE_NEWS, MediaType.APPLICATION_JSON,
                this.requestedId );
    }
}
