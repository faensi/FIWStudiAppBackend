package de.fhws.fiw.fachschaft.studiapp.api.state.coffeeMachines;

import de.fhws.fiw.fachschaft.studiapp.database.config.DaoFactory;
import de.fhws.fiw.fachschaft.studiapp.models.CoffeeMachine;
import de.fhws.fiw.sutton.api.queries.AbstractQuery;
import de.fhws.fiw.sutton.api.states.get.AbstractGetCollectionState;
import de.fhws.fiw.sutton.database.DatabaseException;
import de.fhws.fiw.sutton.database.results.CollectionModelResult;

import javax.ws.rs.core.MediaType;
import java.util.Collections;

public class GetAllStates extends AbstractGetCollectionState<CoffeeMachine>
{
    public GetAllStates( )
    {
    }

    @Override
    protected void defineTransitionLinks( )
    {
        addLink( CoffeeMachineUri.REL_PATH, CoffeeMachineRelTypes.GET_CURRENT_STATE, MediaType.APPLICATION_JSON );
    }

    public static class GetAllStatesQuery extends AbstractQuery<CoffeeMachine>
    {
        @Override protected CollectionModelResult<CoffeeMachine> doExecuteQuery( ) throws DatabaseException
        {
            try {
                return new CollectionModelResult<>(Collections.checkedCollection(DaoFactory.getInstance( ).getCoffeemachineDAO().getAllStates( ), CoffeeMachine.class));
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

}
