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

package de.fhws.fiw.sutton.api.states.delete;

import de.fhws.fiw.sutton.api.states.AbstractState;
import de.fhws.fiw.sutton.database.results.NoContentResult;
import de.fhws.fiw.sutton.database.results.SingleModelResult;
import de.fhws.fiw.sutton.models.AbstractModel;

import javax.ws.rs.core.Response;

public abstract class AbstractDeleteState<T extends AbstractModel> extends AbstractState
{
	protected long modelIdToDelete;

	protected SingleModelResult<T> modelToDelete;

	protected NoContentResult resultAfterDelete;

	public long getModelIdToDelete( )
	{
		return this.modelIdToDelete;
	}

	public AbstractDeleteState setModelIdToDelete( final long modelIdToDelete )
	{
		this.modelIdToDelete = modelIdToDelete;
		return this;
	}

	@Override
	protected Response buildInternal( )
	{
		configureState( );

		this.modelToDelete = loadModel( );

		if ( this.modelToDelete.isEmpty( ) )
		{
			return Response.status( Response.Status.NOT_FOUND )
						   .build( );
		}

		this.resultAfterDelete = deleteModel( );

		if ( this.resultAfterDelete.hasError( ) )
		{
			return Response.serverError( )
						   .build( );
		}

		return createResponse( );
	}

	protected abstract SingleModelResult<T> loadModel( );

	protected abstract NoContentResult deleteModel( );

	protected Response createResponse( )
	{
		defineResponseStatus( );

		defineHttpResponseBody( );

		defineTransitionLinks( );

		return this.responseBuilder.build( );
	}

	private void defineResponseStatus( )
	{
		this.responseBuilder.status( Response.Status.OK );
	}

	private void defineHttpResponseBody( )
	{
		this.responseBuilder.entity( this.modelToDelete.getResult( ) );
	}

	protected abstract void defineTransitionLinks( );
}
