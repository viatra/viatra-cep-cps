package hu.karaszi.ec.centralunit.event.forwarder.api;

import java.io.Serializable;

public interface EventForwarder {
	public <T extends Serializable> void sendEvent(T item, EventSource source, EventType type);
	public <T extends Serializable> void sendEvent(T item, EventSource source, String type);
}
