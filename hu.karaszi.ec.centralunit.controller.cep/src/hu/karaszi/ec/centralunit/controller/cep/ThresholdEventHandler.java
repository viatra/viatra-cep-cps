package hu.karaszi.ec.centralunit.controller.cep;

import org.eclipse.incquery.runtime.evm.api.Activation;
import org.eclipse.viatra.cep.core.api.patterns.IObservableComplexEventPattern;
import org.eclipse.viatra.cep.core.api.rules.IActionHandler;

public class ThresholdEventHandler implements IActionHandler {

	@Override
	public void handle(Activation<? extends IObservableComplexEventPattern> activation) {
		//System.out.println(activation.toString());
		System.out.println("Rapid high critical range elevation detected.");
	}

}
