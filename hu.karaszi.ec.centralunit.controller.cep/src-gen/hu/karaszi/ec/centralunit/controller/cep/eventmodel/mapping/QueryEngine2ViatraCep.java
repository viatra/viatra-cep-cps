package hu.karaszi.ec.centralunit.controller.cep.eventmodel.mapping;

import hu.karaszi.ec.centralunit.controller.cep.eventmodel.events.queryresult.SensorEntersHighCriticalRange_Event;
import hu.karaszi.ec.centralunit.controller.cep.eventmodel.events.queryresult.SensorEntersHighFatalRange_Event;
import hu.karaszi.ec.centralunit.controller.cep.eventmodel.events.queryresult.SensorEntersLowCriticalRange_Event;
import hu.karaszi.ec.centralunit.controller.cep.eventmodel.events.queryresult.SensorEntersLowFatalRange_Event;
import hu.karaszi.ec.centralunit.controller.cep.eventmodel.events.queryresult.SensorEntersNormalRange_Event;
import hu.karaszi.ec.centralunit.controller.model.incquery.SensorInHighCriticalRangeMatch;
import hu.karaszi.ec.centralunit.controller.model.incquery.SensorInHighCriticalRangeMatcher;
import hu.karaszi.ec.centralunit.controller.model.incquery.SensorInHighFatalRangeMatch;
import hu.karaszi.ec.centralunit.controller.model.incquery.SensorInHighFatalRangeMatcher;
import hu.karaszi.ec.centralunit.controller.model.incquery.SensorInLowCriticalRangeMatch;
import hu.karaszi.ec.centralunit.controller.model.incquery.SensorInLowCriticalRangeMatcher;
import hu.karaszi.ec.centralunit.controller.model.incquery.SensorInLowFatalRangeMatch;
import hu.karaszi.ec.centralunit.controller.model.incquery.SensorInLowFatalRangeMatcher;
import hu.karaszi.ec.centralunit.controller.model.incquery.SensorInNormalRangeMatch;
import hu.karaszi.ec.centralunit.controller.model.incquery.SensorInNormalRangeMatcher;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.incquery.runtime.api.IMatchProcessor;
import org.eclipse.incquery.runtime.emf.EMFScope;
import org.eclipse.incquery.runtime.evm.specific.Lifecycles;
import org.eclipse.incquery.runtime.evm.specific.event.IncQueryActivationStateEnum;
import org.eclipse.incquery.runtime.exception.IncQueryException;
import org.eclipse.viatra.cep.core.streams.EventStream;
import org.eclipse.viatra.emf.runtime.rules.EventDrivenTransformationRuleGroup;
import org.eclipse.viatra.emf.runtime.rules.eventdriven.EventDrivenTransformationRule;
import org.eclipse.viatra.emf.runtime.rules.eventdriven.EventDrivenTransformationRuleFactory;
import org.eclipse.viatra.emf.runtime.transformation.eventdriven.EventDrivenTransformation;
import org.eclipse.viatra.emf.runtime.transformation.eventdriven.InconsistentEventSemanticsException;

@SuppressWarnings("all")
public class QueryEngine2ViatraCep {
  private EventStream eventStream;
  
  private ResourceSet resourceSet;
  
  private EventDrivenTransformation transformation;
  
  private QueryEngine2ViatraCep(final ResourceSet resourceSet, final EventStream eventStream) {
    this.resourceSet = resourceSet;
    this.eventStream = eventStream;
    registerRules();
  }
  
  public static QueryEngine2ViatraCep register(final ResourceSet resourceSet, final EventStream eventStream) {
    return new QueryEngine2ViatraCep(resourceSet, eventStream);
  }
  
  public EventDrivenTransformationRuleGroup getRules() {
    EventDrivenTransformationRuleGroup ruleGroup = new EventDrivenTransformationRuleGroup(
    	createsensorInHighCriticalRange_MappingRule(), 
    	createsensorInLowFatalRange_MappingRule(), 
    	createsensorInLowCriticalRange_MappingRule(), 
    	createsensorInNormalRange_MappingRule(), 
    	createsensorInHighFatalRange_MappingRule()
    );
    return ruleGroup;
  }
  
  private void registerRules() {
    try {
    	transformation = EventDrivenTransformation.forScope(new EMFScope(resourceSet)).addRules(getRules()).create();
    } catch (IncQueryException e) {
    	e.printStackTrace();
    }
  }
  
  public EventDrivenTransformationRule<SensorInHighCriticalRangeMatch, SensorInHighCriticalRangeMatcher> createsensorInHighCriticalRange_MappingRule() {
    try{
      EventDrivenTransformationRuleFactory.EventDrivenTransformationBuilder<SensorInHighCriticalRangeMatch, SensorInHighCriticalRangeMatcher> builder = new EventDrivenTransformationRuleFactory().createRule();
      builder.addLifeCycle(Lifecycles.getDefault(false, true));
      builder.precondition(SensorInHighCriticalRangeMatcher.querySpecification());
      
      IMatchProcessor<SensorInHighCriticalRangeMatch> actionOnAppear_0 = new IMatchProcessor<SensorInHighCriticalRangeMatch>() {
        public void process(final SensorInHighCriticalRangeMatch matchedPattern) {
          SensorEntersHighCriticalRange_Event event = new SensorEntersHighCriticalRange_Event(null);event.setId((long)matchedPattern.get(1));
          
          event.setIncQueryPattern(matchedPattern);
          eventStream.push(event);
        }
      };
      builder.action(IncQueryActivationStateEnum.APPEARED, actionOnAppear_0);
      
      IMatchProcessor<SensorInHighCriticalRangeMatch> actionOnDisappear_0 = new IMatchProcessor<SensorInHighCriticalRangeMatch>() {
        public void process(final SensorInHighCriticalRangeMatch matchedPattern) {
        }
      };
      builder.action(IncQueryActivationStateEnum.DISAPPEARED, actionOnDisappear_0);
      
      return builder.build();
    } catch (IncQueryException e) {
      e.printStackTrace();
    } catch (InconsistentEventSemanticsException e) {
      e.printStackTrace();
    }
    return null;
  }
  
  public EventDrivenTransformationRule<SensorInLowFatalRangeMatch, SensorInLowFatalRangeMatcher> createsensorInLowFatalRange_MappingRule() {
    try{
      EventDrivenTransformationRuleFactory.EventDrivenTransformationBuilder<SensorInLowFatalRangeMatch, SensorInLowFatalRangeMatcher> builder = new EventDrivenTransformationRuleFactory().createRule();
      builder.addLifeCycle(Lifecycles.getDefault(false, true));
      builder.precondition(SensorInLowFatalRangeMatcher.querySpecification());
      
      IMatchProcessor<SensorInLowFatalRangeMatch> actionOnAppear_0 = new IMatchProcessor<SensorInLowFatalRangeMatch>() {
        public void process(final SensorInLowFatalRangeMatch matchedPattern) {
          SensorEntersLowFatalRange_Event event = new SensorEntersLowFatalRange_Event(null);event.setId((long)matchedPattern.get(1));
          
          event.setIncQueryPattern(matchedPattern);
          eventStream.push(event);
        }
      };
      builder.action(IncQueryActivationStateEnum.APPEARED, actionOnAppear_0);
      
      IMatchProcessor<SensorInLowFatalRangeMatch> actionOnDisappear_0 = new IMatchProcessor<SensorInLowFatalRangeMatch>() {
        public void process(final SensorInLowFatalRangeMatch matchedPattern) {
        }
      };
      builder.action(IncQueryActivationStateEnum.DISAPPEARED, actionOnDisappear_0);
      
      return builder.build();
    } catch (IncQueryException e) {
      e.printStackTrace();
    } catch (InconsistentEventSemanticsException e) {
      e.printStackTrace();
    }
    return null;
  }
  
  public EventDrivenTransformationRule<SensorInLowCriticalRangeMatch, SensorInLowCriticalRangeMatcher> createsensorInLowCriticalRange_MappingRule() {
    try{
      EventDrivenTransformationRuleFactory.EventDrivenTransformationBuilder<SensorInLowCriticalRangeMatch, SensorInLowCriticalRangeMatcher> builder = new EventDrivenTransformationRuleFactory().createRule();
      builder.addLifeCycle(Lifecycles.getDefault(false, true));
      builder.precondition(SensorInLowCriticalRangeMatcher.querySpecification());
      
      IMatchProcessor<SensorInLowCriticalRangeMatch> actionOnAppear_0 = new IMatchProcessor<SensorInLowCriticalRangeMatch>() {
        public void process(final SensorInLowCriticalRangeMatch matchedPattern) {
          SensorEntersLowCriticalRange_Event event = new SensorEntersLowCriticalRange_Event(null);event.setId((long)matchedPattern.get(1));
          
          event.setIncQueryPattern(matchedPattern);
          eventStream.push(event);
        }
      };
      builder.action(IncQueryActivationStateEnum.APPEARED, actionOnAppear_0);
      
      IMatchProcessor<SensorInLowCriticalRangeMatch> actionOnDisappear_0 = new IMatchProcessor<SensorInLowCriticalRangeMatch>() {
        public void process(final SensorInLowCriticalRangeMatch matchedPattern) {
        }
      };
      builder.action(IncQueryActivationStateEnum.DISAPPEARED, actionOnDisappear_0);
      
      return builder.build();
    } catch (IncQueryException e) {
      e.printStackTrace();
    } catch (InconsistentEventSemanticsException e) {
      e.printStackTrace();
    }
    return null;
  }
  
  public EventDrivenTransformationRule<SensorInNormalRangeMatch, SensorInNormalRangeMatcher> createsensorInNormalRange_MappingRule() {
    try{
      EventDrivenTransformationRuleFactory.EventDrivenTransformationBuilder<SensorInNormalRangeMatch, SensorInNormalRangeMatcher> builder = new EventDrivenTransformationRuleFactory().createRule();
      builder.addLifeCycle(Lifecycles.getDefault(false, true));
      builder.precondition(SensorInNormalRangeMatcher.querySpecification());
      
      IMatchProcessor<SensorInNormalRangeMatch> actionOnAppear_0 = new IMatchProcessor<SensorInNormalRangeMatch>() {
        public void process(final SensorInNormalRangeMatch matchedPattern) {
          SensorEntersNormalRange_Event event = new SensorEntersNormalRange_Event(null);event.setId((long)matchedPattern.get(1));
          
          event.setIncQueryPattern(matchedPattern);
          eventStream.push(event);
        }
      };
      builder.action(IncQueryActivationStateEnum.APPEARED, actionOnAppear_0);
      
      IMatchProcessor<SensorInNormalRangeMatch> actionOnDisappear_0 = new IMatchProcessor<SensorInNormalRangeMatch>() {
        public void process(final SensorInNormalRangeMatch matchedPattern) {
        }
      };
      builder.action(IncQueryActivationStateEnum.DISAPPEARED, actionOnDisappear_0);
      
      return builder.build();
    } catch (IncQueryException e) {
      e.printStackTrace();
    } catch (InconsistentEventSemanticsException e) {
      e.printStackTrace();
    }
    return null;
  }
  
  public EventDrivenTransformationRule<SensorInHighFatalRangeMatch, SensorInHighFatalRangeMatcher> createsensorInHighFatalRange_MappingRule() {
    try{
      EventDrivenTransformationRuleFactory.EventDrivenTransformationBuilder<SensorInHighFatalRangeMatch, SensorInHighFatalRangeMatcher> builder = new EventDrivenTransformationRuleFactory().createRule();
      builder.addLifeCycle(Lifecycles.getDefault(false, true));
      builder.precondition(SensorInHighFatalRangeMatcher.querySpecification());
      
      IMatchProcessor<SensorInHighFatalRangeMatch> actionOnAppear_0 = new IMatchProcessor<SensorInHighFatalRangeMatch>() {
        public void process(final SensorInHighFatalRangeMatch matchedPattern) {
          SensorEntersHighFatalRange_Event event = new SensorEntersHighFatalRange_Event(null);event.setId((long)matchedPattern.get(1));
          
          event.setIncQueryPattern(matchedPattern);
          eventStream.push(event);
        }
      };
      builder.action(IncQueryActivationStateEnum.APPEARED, actionOnAppear_0);
      
      IMatchProcessor<SensorInHighFatalRangeMatch> actionOnDisappear_0 = new IMatchProcessor<SensorInHighFatalRangeMatch>() {
        public void process(final SensorInHighFatalRangeMatch matchedPattern) {
        }
      };
      builder.action(IncQueryActivationStateEnum.DISAPPEARED, actionOnDisappear_0);
      
      return builder.build();
    } catch (IncQueryException e) {
      e.printStackTrace();
    } catch (InconsistentEventSemanticsException e) {
      e.printStackTrace();
    }
    return null;
  }
  
  public void dispose() {
    this.transformation = null;
  }
}
