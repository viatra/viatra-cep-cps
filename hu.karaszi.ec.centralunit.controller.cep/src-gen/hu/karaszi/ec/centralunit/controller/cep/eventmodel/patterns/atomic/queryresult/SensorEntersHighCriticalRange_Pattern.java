package hu.karaszi.ec.centralunit.controller.cep.eventmodel.patterns.atomic.queryresult;

import hu.karaszi.ec.centralunit.controller.cep.eventmodel.events.queryresult.SensorEntersHighCriticalRange_Event;
import org.eclipse.viatra.cep.core.metamodels.events.impl.AtomicEventPatternImpl;

@SuppressWarnings("all")
public class SensorEntersHighCriticalRange_Pattern extends AtomicEventPatternImpl {
  public SensorEntersHighCriticalRange_Pattern() {
    super();
    setType(SensorEntersHighCriticalRange_Event.class.getCanonicalName());
    setId("hu.karaszi.ec.centralunit.controller.cep.eventmodel.patterns.atomic.queryresult.sensorentershighcriticalrange_pattern");
  }
  
  public boolean evaluateCheckExpression() {
    return true;
  }
}
