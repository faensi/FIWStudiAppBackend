package de.fhws.fiw.fachschaft.studiapp.api.service;

import de.fhws.fiw.fachschaft.studiapp.api.state.news.*;
import de.fhws.fiw.fachschaft.studiapp.models.News;
import de.fhws.fiw.sutton.api.queries.PagingBehaviorUsingOffsetSize;
import de.fhws.fiw.sutton.api.services.AbstractService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("news")
public class NewsService extends AbstractService {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllNews() {
        final GetAllNews.GetAllNewsQuery query = new GetAllNews.GetAllNewsQuery();
        query.setPagingBehavior( new PagingBehaviorUsingOffsetSize( 0, 100 ) );

        return new GetAllNews().setQuery(query)
                .setUriInfo(this.uriInfo)
                .setRequest(this.request)
                .setHttpServletRequest(this.httpServletRequest)
                .setContext(this.context)
                .build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSingleNews(@PathParam("id") final long id) {
        return new GetSingleNews().setRequestedId(id)
                .setUriInfo(this.uriInfo)
                .setRequest(this.request)
                .setHttpServletRequest(this.httpServletRequest)
                .setContext(this.context)
                .build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createSingleNews(final News news) {
        return new PostNews().setModelToStore(news)
                .setUriInfo(this.uriInfo)
                .setRequest(this.request)
                .setHttpServletRequest(this.httpServletRequest)
                .setContext(this.context)
                .build();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateSingleNews(@PathParam("id") final long id, final News news) {
        news.setId(id);
        return new UpdateNews().setModelToUpdate(news)
                .setUriInfo(this.uriInfo)
                .setRequest(this.request)
                .setHttpServletRequest(this.httpServletRequest)
                .setContext(this.context)
                .build();
    }

    @DELETE
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteSingleNews(@PathParam("id") final long id, final News news) {
        news.setId(id);
        return new DeleteSingleNews().setModelToUpdate(news)
                .setUriInfo(this.uriInfo)
                .setRequest(this.request)
                .setHttpServletRequest(this.httpServletRequest)
                .setContext(this.context)
                .build();
    }
}
