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

package de.fhws.fiw.sutton.api.filter;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
@UserAuthorization
public class AuthFilter implements ContainerRequestFilter
{
	@Override
	public void filter( final ContainerRequestContext requestContext ) throws IOException
	{
		final String authHeader = requestContext.getHeaderString( HttpHeaders.AUTHORIZATION );
		if ( authHeader == null )
		{
			requestContext.abortWith( Response.status( Response.Status.UNAUTHORIZED )
											  .header( HttpHeaders.WWW_AUTHENTICATE, "Bearer realm=\"zikzak.com\"" )
											  .entity( "Page requires login." )
											  .build( ) );
		}
		else
		{
			final String withoutBearer = authHeader.replaceFirst( "[Bb]earer ", "" );

			requestContext.setProperty( "userid", withoutBearer );
		}
	}
}
