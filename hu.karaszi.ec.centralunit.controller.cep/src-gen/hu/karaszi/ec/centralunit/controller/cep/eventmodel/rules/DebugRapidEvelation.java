package hu.karaszi.ec.centralunit.controller.cep.eventmodel.rules;

import com.google.common.collect.Lists;
import hu.karaszi.ec.centralunit.controller.cep.eventmodel.jobs.DebugRapidEvelation_Job;
import hu.karaszi.ec.centralunit.controller.cep.eventmodel.patterns.complex.RapidHighCriticalElevation_Pattern;
import hu.karaszi.ec.centralunit.controller.cep.eventmodel.patterns.complex.RapidHighFatalElevation_Pattern;
import hu.karaszi.ec.centralunit.controller.cep.eventmodel.patterns.complex.RapidLowCriticalElevation_Pattern;
import hu.karaszi.ec.centralunit.controller.cep.eventmodel.patterns.complex.RapidNormalElevation_Pattern;
import java.util.List;
import org.eclipse.incquery.runtime.evm.api.Job;
import org.eclipse.viatra.cep.core.api.evm.CepActivationStates;
import org.eclipse.viatra.cep.core.api.patterns.IObservableComplexEventPattern;
import org.eclipse.viatra.cep.core.api.rules.ICepRule;
import org.eclipse.viatra.cep.core.metamodels.events.EventPattern;

@SuppressWarnings("all")
public class DebugRapidEvelation implements ICepRule {
  private List<EventPattern> eventPatterns = Lists.newArrayList();
  
  private Job<IObservableComplexEventPattern> job = new DebugRapidEvelation_Job(CepActivationStates.ACTIVE);
  
  public DebugRapidEvelation() {
    eventPatterns.add(new RapidLowCriticalElevation_Pattern());
    eventPatterns.add(new RapidNormalElevation_Pattern());
    eventPatterns.add(new RapidNormalElevation_Pattern());
    eventPatterns.add(new RapidHighCriticalElevation_Pattern());
    eventPatterns.add(new RapidHighFatalElevation_Pattern());
  }
  
  @Override
  public List<EventPattern> getEventPatterns() {
    return this.eventPatterns;
  }
  
  @Override
  public Job<IObservableComplexEventPattern> getJob() {
    return this.job;
  }
}
