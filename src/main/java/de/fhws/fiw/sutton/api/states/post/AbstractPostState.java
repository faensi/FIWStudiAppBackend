/*
 * Copyright 2019 University of Applied Sciences Würzburg-Schweinfurt, Germany
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.fhws.fiw.sutton.api.states.post;

import de.fhws.fiw.fachschaft.studiapp.models.CoffeeMachine;
import de.fhws.fiw.sutton.api.states.AbstractState;
import de.fhws.fiw.sutton.database.results.NoContentResult;
import de.fhws.fiw.sutton.models.AbstractModel;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

public abstract class AbstractPostState<T extends AbstractModel> extends AbstractState
{
	protected T modelToStore;

	protected NoContentResult resultAfterSave;

	protected AbstractPostState( )
	{
		super( );
	}

	public T getModelToStore( )
	{
		return this.modelToStore;
	}

	public AbstractPostState setModelToStore( final T modelToStore )
	{
		this.modelToStore = modelToStore;
		return this;
	}

	public NoContentResult getResultAfterSave( )
	{
		return this.resultAfterSave;
	}

	public void setResultAfterSave( final NoContentResult resultAfterSave )
	{
		this.resultAfterSave = resultAfterSave;
	}

	@Override
	protected Response buildInternal( )
	{
		configureState( );

		this.resultAfterSave = saveModel( );

		if ( this.resultAfterSave.hasError( ) )
		{
			return Response.serverError( )
						   .build( );
		}

		return createResponse( );
	}

	protected abstract NoContentResult saveModel( ) ;

	protected Response createResponse( )
	{
		defineLocationLink( );

		defineTransitionLinks( );

		return this.responseBuilder.build( );
	}

	protected abstract void defineTransitionLinks( );

	protected void defineLocationLink( )
	{
		final UriBuilder builder = this.uriInfo.getAbsolutePathBuilder( );
		final URI location = builder.path( Long.toString( this.modelToStore.getId( ) ) )
									.build( );
		this.responseBuilder.status( Response.Status.CREATED );
		this.responseBuilder.location( location );
	}
}
