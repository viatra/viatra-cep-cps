package hu.karaszi.ec.centralunit.controller.cep;

public class ControlEventHandler extends EventHandlerBase {
	
	public static void handleReadFailed(String deviceId) {
		System.out.println("Reading device failed: " + deviceId);		
	}
}
