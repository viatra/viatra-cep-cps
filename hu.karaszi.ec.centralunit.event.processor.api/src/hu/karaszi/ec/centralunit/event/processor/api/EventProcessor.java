package hu.karaszi.ec.centralunit.event.processor.api;

public interface EventProcessor {
	public void handleEvent(Object message, String eventType);
}
