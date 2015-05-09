package hu.karaszi.ec.centralunit.controller.cep.eventmodel.patterns.atomic.queryresult;

import hu.karaszi.ec.centralunit.controller.cep.eventmodel.events.queryresult.SensorEntersLowCriticalRange_Event;
import org.eclipse.viatra.cep.core.metamodels.events.impl.AtomicEventPatternImpl;

@SuppressWarnings("all")
public class SensorEntersLowCriticalRange_Pattern extends AtomicEventPatternImpl {
  public SensorEntersLowCriticalRange_Pattern() {
    super();
    setType(SensorEntersLowCriticalRange_Event.class.getCanonicalName());
    setId("hu.karaszi.ec.centralunit.controller.cep.eventmodel.patterns.atomic.queryresult.sensorenterslowcriticalrange_pattern");
  }
  
  public boolean evaluateCheckExpression() {
    return true;
  }
}
