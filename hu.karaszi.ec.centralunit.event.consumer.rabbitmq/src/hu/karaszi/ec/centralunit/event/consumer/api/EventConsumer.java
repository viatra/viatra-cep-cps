package hu.karaszi.ec.centralunit.event.consumer.api;

import hu.karaszi.ec.centralunit.event.processor.api.EventProcessor;

public interface EventConsumer {
	public void activate(EventProcessor handler, String[] topics);
	public void deactivate();
}
