package hu.karaszi.ec.centralunit.controller.cep.eventmodel.patterns.complex.anonymous;

import com.google.common.collect.Lists;
import hu.karaszi.ec.centralunit.controller.cep.eventmodel.patterns.atomic.queryresult.SensorEntersHighCriticalRange_Pattern;
import hu.karaszi.ec.centralunit.controller.cep.eventmodel.patterns.atomic.queryresult.SensorEntersNormalRange_Pattern;
import org.eclipse.viatra.cep.core.api.patterns.ParameterizableComplexEventPattern;
import org.eclipse.viatra.cep.core.metamodels.events.EventsFactory;

@SuppressWarnings("all")
public class _AnonymousPattern_3 extends ParameterizableComplexEventPattern {
  public _AnonymousPattern_3() {
    super();
    setOperator(EventsFactory.eINSTANCE.createFOLLOWS());
    
    // contained event patterns
    addEventPatternRefrence(new SensorEntersNormalRange_Pattern(), 1, Lists.newArrayList("id"));
    addEventPatternRefrence(new SensorEntersHighCriticalRange_Pattern(), 1, Lists.newArrayList("id"));
    setId("hu.karaszi.ec.centralunit.controller.cep.eventmodel.patterns.complex.anonymous._anonymouspattern_3");
  }
}
