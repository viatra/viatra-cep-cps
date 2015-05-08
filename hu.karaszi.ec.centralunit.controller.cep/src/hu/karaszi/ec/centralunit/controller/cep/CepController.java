package hu.karaszi.ec.centralunit.controller.cep;

import org.eclipse.viatra.cep.core.api.helpers.DefaultApplication;
import org.eclipse.viatra.cep.core.metamodels.automaton.EventContext;
import org.osgi.service.component.ComponentContext;

public class CepController extends DefaultApplication {
	private QueryEngine2ViatraCep mapping;
	
	public CepController() {
		super(EventContext.CHRONICLE);
	}

	protected void activate(ComponentContext context) {
		getEngine().getLogger().debug("Starting");
		
	}
	
	protected void deactivate(ComponentContext context) {
		
		getEngine().getLogger().debug("Ending");
	}
	
	@Override
	protected void configureRules() {
		getEngine().addRules(CepFactory.getInstance().allRules());
	}

}
