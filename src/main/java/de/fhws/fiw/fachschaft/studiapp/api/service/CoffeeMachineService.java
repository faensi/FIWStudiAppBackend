package de.fhws.fiw.fachschaft.studiapp.api.service;

import de.fhws.fiw.fachschaft.studiapp.api.state.coffeeMachines.GetAllStates;
import de.fhws.fiw.fachschaft.studiapp.api.state.coffeeMachines.GetCurrentState;
import de.fhws.fiw.fachschaft.studiapp.api.state.coffeeMachines.PostNewCoffeeMachine;
import de.fhws.fiw.fachschaft.studiapp.models.CoffeeMachine;
import de.fhws.fiw.sutton.api.queries.PagingBehaviorUsingOffsetSize;
import de.fhws.fiw.sutton.api.services.AbstractService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("coffee-machine")
public class CoffeeMachineService extends AbstractService {
    @GET
    @Path( "/{id}" )
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCurrentState() {
        return new GetCurrentState()
                .setUriInfo(this.uriInfo)
                .setRequest(this.request)
                .setHttpServletRequest(this.httpServletRequest)
                .setContext(this.context)
                .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllStates() {

		final GetAllStates.GetAllStatesQuery query = new GetAllStates.GetAllStatesQuery( );
		query.setPagingBehavior( new PagingBehaviorUsingOffsetSize( 0, 10 ) );

        return new GetAllStates()
				.setQuery( query)
                .setUriInfo(this.uriInfo)
                .setRequest(this.request)
                .setHttpServletRequest(this.httpServletRequest)
                .setContext(this.context)
                .build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(final CoffeeMachine coffeeMachine) {
        return new PostNewCoffeeMachine().setModelToStore(coffeeMachine)
                .setUriInfo(this.uriInfo)
                .setRequest(this.request)
                .setHttpServletRequest(this.httpServletRequest)
                .setContext(this.context)
                .build();
    }
}

