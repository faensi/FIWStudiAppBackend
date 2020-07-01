package de.fhws.fiw.fachschaft.studiapp.api.state.coffeeMachines;

import de.fhws.fiw.fachschaft.studiapp.database.config.DaoFactory;
import de.fhws.fiw.fachschaft.studiapp.models.CoffeeMachine;
import de.fhws.fiw.sutton.api.states.post.AbstractPostState;
import de.fhws.fiw.sutton.database.results.NoContentResult;

import javax.ws.rs.core.MediaType;

public class PostNewCoffeeMachine extends AbstractPostState<CoffeeMachine> {

    public PostNewCoffeeMachine( )
    {
    }

    @Override
    protected void configureState()
    {
        super.configureState();
        this.activateUserAuthentication=true;
    }

    @Override protected void defineTransitionLinks( )
    {
        addLink( CoffeeMachineUri.REL_PATH,CoffeeMachineRelTypes.GET_CURRENT_STATE, MediaType.APPLICATION_JSON,
                this.modelToStore.getId());
    }

    @Override protected NoContentResult saveModel( )
    {
        try {
            DaoFactory.getInstance( ).getCoffeemachineDAO().create( this.modelToStore );
            return new NoContentResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
