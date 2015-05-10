package hu.karaszi.ec.centralunit.controller.cep.eventmodel;

import com.google.common.collect.Lists;
import hu.karaszi.ec.centralunit.controller.cep.eventmodel.events.queryresult.SensorEntersHighCriticalRange_Event;
import hu.karaszi.ec.centralunit.controller.cep.eventmodel.events.queryresult.SensorEntersHighFatalRange_Event;
import hu.karaszi.ec.centralunit.controller.cep.eventmodel.events.queryresult.SensorEntersLowCriticalRange_Event;
import hu.karaszi.ec.centralunit.controller.cep.eventmodel.events.queryresult.SensorEntersLowFatalRange_Event;
import hu.karaszi.ec.centralunit.controller.cep.eventmodel.events.queryresult.SensorEntersNormalRange_Event;
import hu.karaszi.ec.centralunit.controller.cep.eventmodel.patterns.atomic.queryresult.SensorEntersHighCriticalRange_Pattern;
import hu.karaszi.ec.centralunit.controller.cep.eventmodel.patterns.atomic.queryresult.SensorEntersHighFatalRange_Pattern;
import hu.karaszi.ec.centralunit.controller.cep.eventmodel.patterns.atomic.queryresult.SensorEntersLowCriticalRange_Pattern;
import hu.karaszi.ec.centralunit.controller.cep.eventmodel.patterns.atomic.queryresult.SensorEntersLowFatalRange_Pattern;
import hu.karaszi.ec.centralunit.controller.cep.eventmodel.patterns.atomic.queryresult.SensorEntersNormalRange_Pattern;
import hu.karaszi.ec.centralunit.controller.cep.eventmodel.patterns.complex.RapidElevation_Pattern;
import hu.karaszi.ec.centralunit.controller.cep.eventmodel.patterns.complex.RapidHighFatalElevation_Pattern;
import hu.karaszi.ec.centralunit.controller.cep.eventmodel.patterns.complex.RapidLowCriticalElevation_Pattern;
import hu.karaszi.ec.centralunit.controller.cep.eventmodel.patterns.complex.RapidNormalElevation_Pattern;
import hu.karaszi.ec.centralunit.controller.cep.eventmodel.rules.DebugRapidEvelation;
import java.util.List;
import org.eclipse.viatra.cep.core.api.rules.ICepRule;
import org.eclipse.viatra.cep.core.metamodels.events.EventSource;

@SuppressWarnings("all")
public class CepFactory {
  private static CepFactory instance;
  
  public static CepFactory getInstance() {
    if(instance == null){
    	instance = new CepFactory();
    }
    return instance;
  }
  
  /**
   * Factory method for event class {@link SensorEntersLowFatalRange_Event}.
   */
  public SensorEntersLowFatalRange_Event createSensorEntersLowFatalRange_Event(final EventSource eventSource) {
    return new SensorEntersLowFatalRange_Event(eventSource);
  }
  
  /**
   * Factory method for event class {@link SensorEntersLowFatalRange_Event}.
   */
  public SensorEntersLowFatalRange_Event createSensorEntersLowFatalRange_Event() {
    return new SensorEntersLowFatalRange_Event(null);
  }
  
  /**
   * Factory method for event class {@link SensorEntersLowCriticalRange_Event}.
   */
  public SensorEntersLowCriticalRange_Event createSensorEntersLowCriticalRange_Event(final EventSource eventSource) {
    return new SensorEntersLowCriticalRange_Event(eventSource);
  }
  
  /**
   * Factory method for event class {@link SensorEntersLowCriticalRange_Event}.
   */
  public SensorEntersLowCriticalRange_Event createSensorEntersLowCriticalRange_Event() {
    return new SensorEntersLowCriticalRange_Event(null);
  }
  
  /**
   * Factory method for event class {@link SensorEntersNormalRange_Event}.
   */
  public SensorEntersNormalRange_Event createSensorEntersNormalRange_Event(final EventSource eventSource) {
    return new SensorEntersNormalRange_Event(eventSource);
  }
  
  /**
   * Factory method for event class {@link SensorEntersNormalRange_Event}.
   */
  public SensorEntersNormalRange_Event createSensorEntersNormalRange_Event() {
    return new SensorEntersNormalRange_Event(null);
  }
  
  /**
   * Factory method for event class {@link SensorEntersHighCriticalRange_Event}.
   */
  public SensorEntersHighCriticalRange_Event createSensorEntersHighCriticalRange_Event(final EventSource eventSource) {
    return new SensorEntersHighCriticalRange_Event(eventSource);
  }
  
  /**
   * Factory method for event class {@link SensorEntersHighCriticalRange_Event}.
   */
  public SensorEntersHighCriticalRange_Event createSensorEntersHighCriticalRange_Event() {
    return new SensorEntersHighCriticalRange_Event(null);
  }
  
  /**
   * Factory method for event class {@link SensorEntersHighFatalRange_Event}.
   */
  public SensorEntersHighFatalRange_Event createSensorEntersHighFatalRange_Event(final EventSource eventSource) {
    return new SensorEntersHighFatalRange_Event(eventSource);
  }
  
  /**
   * Factory method for event class {@link SensorEntersHighFatalRange_Event}.
   */
  public SensorEntersHighFatalRange_Event createSensorEntersHighFatalRange_Event() {
    return new SensorEntersHighFatalRange_Event(null);
  }
  
  /**
   * Factory method for atomic query result event pattern {@link SensorEntersLowFatalRange_Pattern}.
   */
  public SensorEntersLowFatalRange_Pattern createSensorEntersLowFatalRange_Pattern() {
    return new SensorEntersLowFatalRange_Pattern();
  }
  
  /**
   * Factory method for atomic query result event pattern {@link SensorEntersLowCriticalRange_Pattern}.
   */
  public SensorEntersLowCriticalRange_Pattern createSensorEntersLowCriticalRange_Pattern() {
    return new SensorEntersLowCriticalRange_Pattern();
  }
  
  /**
   * Factory method for atomic query result event pattern {@link SensorEntersNormalRange_Pattern}.
   */
  public SensorEntersNormalRange_Pattern createSensorEntersNormalRange_Pattern() {
    return new SensorEntersNormalRange_Pattern();
  }
  
  /**
   * Factory method for atomic query result event pattern {@link SensorEntersHighCriticalRange_Pattern}.
   */
  public SensorEntersHighCriticalRange_Pattern createSensorEntersHighCriticalRange_Pattern() {
    return new SensorEntersHighCriticalRange_Pattern();
  }
  
  /**
   * Factory method for atomic query result event pattern {@link SensorEntersHighFatalRange_Pattern}.
   */
  public SensorEntersHighFatalRange_Pattern createSensorEntersHighFatalRange_Pattern() {
    return new SensorEntersHighFatalRange_Pattern();
  }
  
  /**
   * Factory method for complex event pattern {@link RapidLowCriticalElevation_Pattern}.
   */
  public RapidLowCriticalElevation_Pattern createRapidLowCriticalElevation_Pattern() {
    return new RapidLowCriticalElevation_Pattern();
  }
  
  /**
   * Factory method for complex event pattern {@link RapidNormalElevation_Pattern}.
   */
  public RapidNormalElevation_Pattern createRapidNormalElevation_Pattern() {
    return new RapidNormalElevation_Pattern();
  }
  
  /**
   * Factory method for complex event pattern {@link RapidElevation_Pattern}.
   */
  public RapidElevation_Pattern createRapidElevation_Pattern() {
    return new RapidElevation_Pattern();
  }
  
  /**
   * Factory method for complex event pattern {@link RapidHighFatalElevation_Pattern}.
   */
  public RapidHighFatalElevation_Pattern createRapidHighFatalElevation_Pattern() {
    return new RapidHighFatalElevation_Pattern();
  }
  
  /**
   * Factory method for rule {@link DebugRapidEvelation}.
   */
  public DebugRapidEvelation createDebugRapidEvelation() {
    return new DebugRapidEvelation();
  }
  
  /**
   * Factory method for instantiating every defined rule.
   */
  public List<ICepRule> allRules() {
    List<ICepRule> rules = Lists.newArrayList();
    rules.add(new DebugRapidEvelation());
    return rules;
  }
}
