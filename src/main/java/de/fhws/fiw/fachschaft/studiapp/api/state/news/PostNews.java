package de.fhws.fiw.fachschaft.studiapp.api.state.news;

import de.fhws.fiw.fachschaft.studiapp.database.config.DaoFactory;
import de.fhws.fiw.fachschaft.studiapp.models.News;
import de.fhws.fiw.sutton.api.states.post.AbstractPostState;
import de.fhws.fiw.sutton.database.results.NoContentResult;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.core.MediaType;

public class PostNews extends AbstractPostState<News> {
    public PostNews( )
    {
    }

    @Override protected void configureState( )
    {
        super.configureState( );
        this.activateUserAuthentication = true;
    }

    @Override protected NoContentResult saveModel( )
    {
        try {
            DaoFactory.getInstance( ).getNewsDao( ).create( this.modelToStore );
            return new NoContentResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override protected void defineTransitionLinks( )
    {
        addLink( NewsUri.REL_PATH, NewsRelTypes.GET_ALL_NEWS, MediaType.APPLICATION_JSON );
    }
}
