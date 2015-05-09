package hu.karaszi.ec.centralunit.controller.cep.eventmodel.patterns.atomic.queryresult;

import hu.karaszi.ec.centralunit.controller.cep.eventmodel.events.queryresult.SensorEntersLowFatalRange_Event;
import org.eclipse.viatra.cep.core.metamodels.events.impl.AtomicEventPatternImpl;

@SuppressWarnings("all")
public class SensorEntersLowFatalRange_Pattern extends AtomicEventPatternImpl {
  public SensorEntersLowFatalRange_Pattern() {
    super();
    setType(SensorEntersLowFatalRange_Event.class.getCanonicalName());
    setId("hu.karaszi.ec.centralunit.controller.cep.eventmodel.patterns.atomic.queryresult.sensorenterslowfatalrange_pattern");
  }
  
  public boolean evaluateCheckExpression() {
    return true;
  }
}
