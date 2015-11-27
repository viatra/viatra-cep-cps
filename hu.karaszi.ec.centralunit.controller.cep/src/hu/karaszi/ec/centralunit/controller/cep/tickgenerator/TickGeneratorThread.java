package hu.karaszi.ec.centralunit.controller.cep.tickgenerator;

import java.util.Date;

import hu.karaszi.ec.centralunit.event.forwarder.api.EventForwarder;
import hu.karaszi.ec.centralunit.event.forwarder.api.EventSource;
import hu.karaszi.ec.centralunit.event.forwarder.api.EventType;

public class TickGeneratorThread extends Thread {
	private EventForwarder forwarder;
	private long tickInterval;
	
	public TickGeneratorThread(long tickInterval, EventForwarder forwarder) {
		this.tickInterval = tickInterval;
		this.forwarder = forwarder;
	}
	
	@Override
	public void run() {
		while(true) {
			forwarder.sendEvent(new Date(), EventSource.INTERNAL, EventType.TICK);
			try {
				Thread.sleep(tickInterval);
			} catch (InterruptedException e) {
				return;
			}
		}
	}
}
