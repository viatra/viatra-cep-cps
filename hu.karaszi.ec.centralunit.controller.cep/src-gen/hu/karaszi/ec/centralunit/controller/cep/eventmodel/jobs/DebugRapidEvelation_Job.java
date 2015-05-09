package hu.karaszi.ec.centralunit.controller.cep.eventmodel.jobs;

import org.eclipse.incquery.runtime.evm.api.Activation;
import org.eclipse.incquery.runtime.evm.api.Context;
import org.eclipse.incquery.runtime.evm.api.Job;
import org.eclipse.incquery.runtime.evm.api.event.ActivationState;
import org.eclipse.viatra.cep.core.api.patterns.IObservableComplexEventPattern;
import org.eclipse.viatra.cep.core.api.rules.IActionHandler;

@SuppressWarnings("all")
public class DebugRapidEvelation_Job extends Job<IObservableComplexEventPattern> {
  public DebugRapidEvelation_Job(final ActivationState activationState) {
    super(activationState);
  }
  
  @Override
  public void execute(final Activation<? extends IObservableComplexEventPattern> activation, final Context context) {
    IActionHandler actionHandler = new hu.karaszi.ec.centralunit.controller.cep.ThresholdEventHandler();
    actionHandler.handle(activation);
  }
  
  @Override
  public void handleError(final Activation<? extends IObservableComplexEventPattern> activation, final Exception exception, final Context context) {
    //not gonna happen
  }
}
