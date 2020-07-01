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

package de.fhws.fiw.sutton.api.queries;

import de.fhws.fiw.sutton.api.hyperlinks.Hyperlinks;
import de.fhws.fiw.sutton.database.results.CollectionModelResult;
import de.fhws.fiw.sutton.models.AbstractModel;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.LinkedList;
import java.util.List;

public class PagingBehaviorUsingOffsetSize<T extends AbstractModel> implements PagingBehavior<T>
{
	public static final int DEFAULT_PAGE_SIZE = 10;

	public static final String QUERY_PARAM_SIZE = "size";

	public static final String QUERY_PARAM_OFFSET = "offset";

	protected int offset;

	protected int size;

	protected String offsetQueryParamName = QUERY_PARAM_OFFSET;

	protected String sizeQueryParamName = QUERY_PARAM_SIZE;

	public PagingBehaviorUsingOffsetSize( )
	{
	}

	public PagingBehaviorUsingOffsetSize( final String offsetQueryParamName, final String sizeQueryParamName )
	{
		this.offsetQueryParamName = offsetQueryParamName;
		this.sizeQueryParamName = sizeQueryParamName;
	}

	public PagingBehaviorUsingOffsetSize( final String offsetQueryParamName,
		final String sizeQueryParamName,
		final int offset,
		final int size )
	{
		super( );
		this.sizeQueryParamName = sizeQueryParamName;
		this.offsetQueryParamName = offsetQueryParamName;

		setOffset( offset );
		setSize( size );
	}

	public PagingBehaviorUsingOffsetSize( final int offset, final int size )
	{
		this.offset = offset;
		this.size = size;
	}

	@Override public CollectionModelResult<T> page( final CollectionModelResult<T> fullResult )
	{
		final CollectionModelResult<T> returnValue = new CollectionModelResult<>( );
		final List<T> fullResultAsList = new LinkedList( fullResult.getResult( ) );
		returnValue.getResult( ).addAll( page( fullResultAsList, this.offset, this.size ) );
		returnValue.setTotalNumberOfResult( fullResult.getTotalNumberOfResult( ) );
		return returnValue;
	}

	private List<T> page( final List<T> result, final int offset, final int size )
	{
		final int fromIndex = Math.max( 0, offset );
		final int toIndex = Math.min( result.size( ), fromIndex + Math.max( size, 0 ) );

		if ( fromIndex < result.size( ) )
		{
			return result.subList( fromIndex, toIndex );
		}
		else
		{
			return new LinkedList<>( );
		}
	}

	@Override
	public void addSelfLink( final PagingContext pagingContext )
	{
		Hyperlinks.addLink( pagingContext.getResponseBuilder( ),
			getSelfUri( pagingContext.getUriInfo( ) ),
			"self",
			pagingContext.getMediaType( ) );
	}

	@Override
	public void addPrevPageLink( final PagingContext pagingContext )
	{
		if ( hasPrevLink( ) )
		{
			Hyperlinks.addLink( pagingContext.getResponseBuilder( ),
				getPrevUri( pagingContext.getUriInfo( ) ),
				"prev",
				pagingContext.getMediaType( ) );
		}
	}

	@Override
	public void addNextPageLink( final PagingContext pagingContext,
		final CollectionModelResult<?> databaseResult )
	{
		if ( hasNextLink( databaseResult ) )
		{
			Hyperlinks.addLink( pagingContext.getResponseBuilder( ),
				getNextUri( pagingContext.getUriInfo( ), databaseResult ),
				"next",
				pagingContext.getMediaType( ) );
		}
	}

	@Override
	public void addPageHeader( final PagingContext pagingContext, final CollectionModelResult<?> result )
	{
		// not implemented
	}

	public int getOffset( )
	{
		return this.offset;
	}

	public void setOffset( final int offset )
	{
		this.offset = Math.max( 0, offset );
	}

	public int getSize( )
	{
		return this.size;
	}

	public void setSize( final int size )
	{
		this.size = Math.max( 1, size );
	}

	private boolean hasNextLink( final CollectionModelResult<?> result )
	{
		return this.offset + this.size < result.getTotalNumberOfResult( );
	}

	private URI getNextUri( final UriInfo uriInfo, final CollectionModelResult<?> result )
	{
		final UriBuilder uriBuilder = createUriBuilder( uriInfo );

		final int newOffset = Math.min( this.offset + this.size, result.getTotalNumberOfResult( ) - 1 );
		final int newSize = Math.min( this.size, getDefaultPageSize( ) );

		return uriBuilder.build( newOffset, newSize );
	}

	private boolean hasPrevLink( )
	{
		return this.offset > 0;
	}

	private URI getPrevUri( final UriInfo uriInfo )
	{
		final UriBuilder uriBuilder = createUriBuilder( uriInfo );

		final int newOffset = Math.max( this.offset - this.size, 0 );
		final int newSize = Math.min( this.size, getDefaultPageSize( ) );

		return uriBuilder.build( newOffset, newSize );
	}

	protected int getDefaultPageSize( )
	{
		return DEFAULT_PAGE_SIZE;
	}

	private URI getSelfUri( final UriInfo uriInfo )
	{
		final UriBuilder uriBuilder = createUriBuilder( uriInfo );

		return uriBuilder.build( this.offset, this.size );
	}

	private UriBuilder createUriBuilder( final UriInfo uriInfo )
	{
		return uriInfo.getRequestUriBuilder( )
					  .replaceQueryParam( getOffsetParamName( ), getQueryParamOffsetAsTemplate( ) )
					  .replaceQueryParam( getSizeParamName( ), getQueryParamSizeAsTemplate( ) );
	}

	protected String getOffsetParamName( )
	{
		return this.offsetQueryParamName;
	}

	protected String getSizeParamName( )
	{
		return this.sizeQueryParamName;
	}

	protected final String getQueryParamOffsetAsTemplate( )
	{
		return "{" + getOffsetParamName( ) + "}";
	}

	protected final String getQueryParamSizeAsTemplate( )
	{
		return "{" + getSizeParamName( ) + "}";
	}

}
