package hu.karaszi.ec.centralunit.controller.cep.eventmodel.patterns.atomic.queryresult;

import hu.karaszi.ec.centralunit.controller.cep.eventmodel.events.queryresult.SensorEntersHighFatalRange_Event;
import org.eclipse.viatra.cep.core.metamodels.events.impl.AtomicEventPatternImpl;

@SuppressWarnings("all")
public class SensorEntersHighFatalRange_Pattern extends AtomicEventPatternImpl {
  public SensorEntersHighFatalRange_Pattern() {
    super();
    setType(SensorEntersHighFatalRange_Event.class.getCanonicalName());
    setId("hu.karaszi.ec.centralunit.controller.cep.eventmodel.patterns.atomic.queryresult.sensorentershighfatalrange_pattern");
  }
  
  public boolean evaluateCheckExpression() {
    return true;
  }
}
