package de.fhws.fiw.fachschaft.studiapp.api.service;

import de.fhws.fiw.fachschaft.studiapp.api.state.DispatcherState;
import de.fhws.fiw.fachschaft.studiapp.api.state.coffeeMachines.CoffeeMachineRelTypes;
import de.fhws.fiw.fachschaft.studiapp.api.state.coffeeMachines.CoffeeMachineUri;
import de.fhws.fiw.fachschaft.studiapp.models.CoffeeMachine;
import de.fhws.fiw.sutton.api.services.AbstractService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static de.fhws.fiw.sutton.api.hyperlinks.Hyperlinks.addLink;

@Path( "" )
public class DispatcherService extends AbstractService {
    @GET
    @Produces( MediaType.APPLICATION_JSON )
    public Response get( )
    {
        return new DispatcherState( ).setUriInfo( this.uriInfo )
                .setRequest( this.request )
                .setHttpServletRequest( this.httpServletRequest )
                .setContext( this.context )
                .build( );
    }
}
