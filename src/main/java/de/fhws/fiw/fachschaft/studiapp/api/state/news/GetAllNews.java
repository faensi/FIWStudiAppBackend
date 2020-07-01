package de.fhws.fiw.fachschaft.studiapp.api.state.news;

import de.fhws.fiw.fachschaft.studiapp.database.config.DaoFactory;
import de.fhws.fiw.fachschaft.studiapp.models.News;
import de.fhws.fiw.sutton.api.queries.AbstractQuery;
import de.fhws.fiw.sutton.api.states.get.AbstractGetCollectionState;
import de.fhws.fiw.sutton.database.DatabaseException;
import de.fhws.fiw.sutton.database.results.CollectionModelResult;

import javax.ws.rs.core.MediaType;
import java.util.Collections;

public class GetAllNews extends AbstractGetCollectionState<News>
{
	public GetAllNews( )
	{
	}

	@Override protected void defineTransitionLinks( )
	{
		addLink( NewsUri.REL_PATH, NewsRelTypes.CREATE_NEWS, MediaType.APPLICATION_JSON );
		addLink( NewsUri.REL_PATH, NewsRelTypes.GET_SINGLE_NEWS, MediaType.APPLICATION_JSON );
	}

	public static class GetAllNewsQuery extends AbstractQuery<News>
	{
		@Override protected CollectionModelResult<News> doExecuteQuery( ) throws DatabaseException
		{
			try
			{
				return new CollectionModelResult<>(
					Collections.checkedCollection( DaoFactory.getInstance( ).getNewsDao( ).getAllNews( ),
						News.class ) );
			}
			catch ( Exception e )
			{
				e.printStackTrace( );
				return null;
			}
		}
	}
}
