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

package de.fhws.fiw.sutton.api.states.put;

import de.fhws.fiw.sutton.api.states.AbstractState;
import de.fhws.fiw.sutton.database.results.NoContentResult;
import de.fhws.fiw.sutton.database.results.SingleModelResult;
import de.fhws.fiw.sutton.models.AbstractModel;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

public abstract class AbstractPutState<T extends AbstractModel> extends AbstractState
{
	protected T modelToUpdate;

	protected SingleModelResult<T> resultAfterGet;

	protected T storedModel;

	protected NoContentResult resultAfterUpdate;

	protected AbstractPutState( )
	{
		super( );
	}

	public T getModelToUpdate( )
	{
		return this.modelToUpdate;
	}

	public AbstractPutState setModelToUpdate( final T modelToUpdate )
	{
		this.modelToUpdate = modelToUpdate;
		return this;
	}

	public SingleModelResult<T> getResultAfterGet( )
	{
		return this.resultAfterGet;
	}

	public void setResultAfterGet( final SingleModelResult<T> resultAfterGet )
	{
		this.resultAfterGet = resultAfterGet;
	}

	public T getStoredModel( )
	{
		return this.storedModel;
	}

	public void setStoredModel( final T storedModel )
	{
		this.storedModel = storedModel;
	}

	public NoContentResult getResultAfterUpdate( )
	{
		return this.resultAfterUpdate;
	}

	public void setResultAfterUpdate( final NoContentResult resultAfterUpdate )
	{
		this.resultAfterUpdate = resultAfterUpdate;
	}

	@Override
	protected Response buildInternal( )
	{
		configureState( );

		this.resultAfterGet = loadModel( );

		if ( this.resultAfterGet.isEmpty( ) )
		{
			return Response.status( Response.Status.NOT_FOUND )
						   .build( );
		}

		this.storedModel = this.resultAfterGet.getResult( );

		this.resultAfterUpdate = updateModel( );

		if ( this.resultAfterUpdate.hasError( ) )
		{
			return Response.serverError( )
						   .build( );
		}

		return createResponse( );
	}

	protected abstract SingleModelResult<T> loadModel( );

	protected abstract NoContentResult updateModel( );

	protected Response createResponse( )
	{
		defineResponseStatus( );

		defineHttpResponseBody( );

		defineSelfLink( );

		defineTransitionLinks( );

		return this.responseBuilder.build( );
	}

	private void defineResponseStatus( )
	{
		this.responseBuilder.status( Response.Status.NO_CONTENT );
	}

	private void defineHttpResponseBody( )
	{
		this.responseBuilder.entity( "" );
	}

	protected abstract void defineTransitionLinks( );

	protected void defineSelfLink( )
	{
		final UriBuilder builder = this.uriInfo.getAbsolutePathBuilder( );
		final URI self = builder.build( );

		this.responseBuilder.link( self, "self" );
	}
}
