package hu.karaszi.ec.testadapter.rest;

import hu.karaszi.ec.testadapter.testlogic.Sensor;

public class SenderThread extends Thread {
	private RestAdapter adapter;
	private Sensor sensor;
	
	public SenderThread(RestAdapter adapter, Sensor sensor) {
		this.adapter = adapter;
		this.sensor = sensor;
	}

	@Override
	public void run() {
		while (true) {
			adapter.readSensor(sensor);
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
