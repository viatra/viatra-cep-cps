package hu.karaszi.ec.piadapter.conversion;

import java.io.IOException;

public class Main {
	public static void main(String[] args) {
		Adapter adapter = new Adapter();
		adapter.initialize();
		
		System.out.println("Press enter to stop the adapter.");
		try {
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		adapter.dispose();
	}
}
