package hu.karaszi.ec.centralunit.controller.cep.eventmodel.events.queryresult;

import org.eclipse.viatra.cep.core.api.events.ParameterizableIncQueryPatternEventInstance;
import org.eclipse.viatra.cep.core.metamodels.events.EventSource;

@SuppressWarnings("all")
public class SensorEntersHighCriticalRange_Event extends ParameterizableIncQueryPatternEventInstance {
  private long id;
  
  public SensorEntersHighCriticalRange_Event(final EventSource eventSource) {
    super(eventSource);
    getParameters().add(id);
    
  }
  
  public long getId() {
    return this.id;
  }
  
  public void setId(final long id) {
    this.id = id;
    getParameters().set(0, id);
  }
}
