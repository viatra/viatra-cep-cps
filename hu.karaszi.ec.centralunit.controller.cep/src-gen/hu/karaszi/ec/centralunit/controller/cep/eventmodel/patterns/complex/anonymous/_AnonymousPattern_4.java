package hu.karaszi.ec.centralunit.controller.cep.eventmodel.patterns.complex.anonymous;

import com.google.common.collect.Lists;
import hu.karaszi.ec.centralunit.controller.cep.eventmodel.patterns.atomic.queryresult.SensorEntersNormalRange_Pattern;
import hu.karaszi.ec.centralunit.controller.cep.eventmodel.patterns.complex.anonymous._AnonymousPattern_3;
import org.eclipse.viatra.cep.core.api.patterns.ParameterizableComplexEventPattern;
import org.eclipse.viatra.cep.core.metamodels.events.EventsFactory;

@SuppressWarnings("all")
public class _AnonymousPattern_4 extends ParameterizableComplexEventPattern {
  public _AnonymousPattern_4() {
    super();
    setOperator(EventsFactory.eINSTANCE.createFOLLOWS());
    
    // contained event patterns
    addEventPatternRefrence(new SensorEntersNormalRange_Pattern(), 1, Lists.newArrayList("id"));
    addEventPatternRefrence(new _AnonymousPattern_3(), 1);
    setId("hu.karaszi.ec.centralunit.controller.cep.eventmodel.patterns.complex.anonymous._anonymouspattern_4");
  }
}
