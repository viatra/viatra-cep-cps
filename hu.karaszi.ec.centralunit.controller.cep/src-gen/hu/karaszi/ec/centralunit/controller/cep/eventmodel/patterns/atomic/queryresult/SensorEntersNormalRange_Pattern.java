package hu.karaszi.ec.centralunit.controller.cep.eventmodel.patterns.atomic.queryresult;

import hu.karaszi.ec.centralunit.controller.cep.eventmodel.events.queryresult.SensorEntersNormalRange_Event;
import org.eclipse.viatra.cep.core.metamodels.events.impl.AtomicEventPatternImpl;

@SuppressWarnings("all")
public class SensorEntersNormalRange_Pattern extends AtomicEventPatternImpl {
  public SensorEntersNormalRange_Pattern() {
    super();
    setType(SensorEntersNormalRange_Event.class.getCanonicalName());
    setId("hu.karaszi.ec.centralunit.controller.cep.eventmodel.patterns.atomic.queryresult.sensorentersnormalrange_pattern");
  }
  
  public boolean evaluateCheckExpression() {
    return true;
  }
}
