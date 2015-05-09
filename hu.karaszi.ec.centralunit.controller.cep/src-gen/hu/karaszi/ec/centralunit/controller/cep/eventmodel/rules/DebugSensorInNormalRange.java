package hu.karaszi.ec.centralunit.controller.cep.eventmodel.rules;

import com.google.common.collect.Lists;
import hu.karaszi.ec.centralunit.controller.cep.eventmodel.jobs.DebugSensorInNormalRange_Job;
import hu.karaszi.ec.centralunit.controller.cep.eventmodel.patterns.complex.DebugInNormalRange_Pattern;
import java.util.List;
import org.eclipse.incquery.runtime.evm.api.Job;
import org.eclipse.viatra.cep.core.api.evm.CepActivationStates;
import org.eclipse.viatra.cep.core.api.patterns.IObservableComplexEventPattern;
import org.eclipse.viatra.cep.core.api.rules.ICepRule;
import org.eclipse.viatra.cep.core.metamodels.events.EventPattern;

@SuppressWarnings("all")
public class DebugSensorInNormalRange implements ICepRule {
  private List<EventPattern> eventPatterns = Lists.newArrayList();
  
  private Job<IObservableComplexEventPattern> job = new DebugSensorInNormalRange_Job(CepActivationStates.ACTIVE);
  
  public DebugSensorInNormalRange() {
    eventPatterns.add(new DebugInNormalRange_Pattern());
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
