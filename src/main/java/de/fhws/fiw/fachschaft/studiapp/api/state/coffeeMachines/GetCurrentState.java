package de.fhws.fiw.fachschaft.studiapp.api.state.coffeeMachines;

import de.fhws.fiw.fachschaft.studiapp.database.config.DaoFactory;
import de.fhws.fiw.fachschaft.studiapp.models.CoffeeMachine;
import de.fhws.fiw.sutton.api.states.get.AbstractGetState;
import de.fhws.fiw.sutton.database.results.SingleModelResult;


public class GetCurrentState extends AbstractGetState <CoffeeMachine> {

    public GetCurrentState( )
    {
    }
    @Override
    protected SingleModelResult<CoffeeMachine> loadModel( )
    {
        try {
            return DaoFactory.getInstance().getCoffeemachineDAO().getCurrentState();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @Override
    protected void defineTransitionLinks( )
    {
    }




}
