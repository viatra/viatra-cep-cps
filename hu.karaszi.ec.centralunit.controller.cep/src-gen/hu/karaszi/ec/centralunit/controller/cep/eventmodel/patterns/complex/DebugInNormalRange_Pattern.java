package hu.karaszi.ec.centralunit.controller.cep.eventmodel.patterns.complex;

import com.google.common.collect.Lists;
import hu.karaszi.ec.centralunit.controller.cep.eventmodel.patterns.atomic.queryresult.SensorEntersNormalRange_Pattern;
import org.eclipse.viatra.cep.core.api.patterns.ParameterizableComplexEventPattern;
import org.eclipse.viatra.cep.core.metamodels.events.EventsFactory;

@SuppressWarnings("all")
public class DebugInNormalRange_Pattern extends ParameterizableComplexEventPattern {
  public DebugInNormalRange_Pattern() {
    super();
    setOperator(EventsFactory.eINSTANCE.createFOLLOWS());
    
    // contained event patterns
    addEventPatternRefrence(new SensorEntersNormalRange_Pattern(), 1, Lists.newArrayList("id"));
    setId("hu.karaszi.ec.centralunit.controller.cep.eventmodel.patterns.complex.debuginnormalrange_pattern");
  }
}
