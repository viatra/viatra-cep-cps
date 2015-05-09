package hu.karaszi.ec.centralunit.controller.cep.eventmodel.patterns.complex;

import hu.karaszi.ec.centralunit.controller.cep.eventmodel.patterns.complex.anonymous._AnonymousPattern_2;
import org.eclipse.viatra.cep.core.api.patterns.ParameterizableComplexEventPattern;
import org.eclipse.viatra.cep.core.metamodels.events.EventsFactory;
import org.eclipse.viatra.cep.core.metamodels.events.Timewindow;

@SuppressWarnings("all")
public class RapidNormalElevation_Pattern extends ParameterizableComplexEventPattern {
  public RapidNormalElevation_Pattern() {
    super();
    setOperator(EventsFactory.eINSTANCE.createFOLLOWS());
    
    // contained event patterns
    addEventPatternRefrence(new _AnonymousPattern_2(), 1);
    						
    Timewindow timewindow = EventsFactory.eINSTANCE.createTimewindow();
    timewindow.setTime(100000);
    setTimewindow(timewindow);
    	
    setId("hu.karaszi.ec.centralunit.controller.cep.eventmodel.patterns.complex.rapidnormalelevation_pattern");
  }
}
