package hu.karaszi.ec.piadapter.adc;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

public class ADCReader {
	// Note: "Mismatch" 23-24. The wiring says DOUT->#23, DIN->#24
	// 23: DOUT on the ADC is IN on the GPIO. ADC:Slave, GPIO:Master
	// 24: DIN on the ADC, OUT on the GPIO. Same reason as above.
	// SPI: Serial Peripheral Interface
	private static Pin spiClk = RaspiPin.GPIO_14; // Pin #18, clock
	private static Pin spiMiso = RaspiPin.GPIO_13; // Pin #23, data in. MISO:
													// Master In Slave Out
	private static Pin spiMosi = RaspiPin.GPIO_12; // Pin #24, data out. MOSI:
													// Master Out Slave In
	private static Pin spiCs = RaspiPin.GPIO_10; // Pin #25, Chip Select

	private static GpioPinDigitalInput misoInput = null;
	private static GpioPinDigitalOutput mosiOutput = null;
	private static GpioPinDigitalOutput clockOutput = null;
	private static GpioPinDigitalOutput chipSelectOutput = null;

	private GpioController gpio;

	public void initADC() {
		gpio = GpioFactory.getInstance();
		mosiOutput = gpio.provisionDigitalOutputPin(spiMosi, "MOSI", PinState.LOW);
		clockOutput = gpio.provisionDigitalOutputPin(spiClk, "CLK", PinState.LOW);
		chipSelectOutput = gpio.provisionDigitalOutputPin(spiCs, "CS", PinState.LOW);
		misoInput = gpio.provisionDigitalInputPin(spiMiso, "MISO");
	}

	public void shutdownADC() {
		gpio.shutdown();
	}

	public int readAdc(int channel) {
		chipSelectOutput.high();

		clockOutput.low();
		chipSelectOutput.low();

		channel |= 0x18; // 0x18: 00011000
		channel <<= 3;
		// Send 5 bits: 8 - 3. 8 input channels on the MCP3008.
		for (int i = 0; i < 5; i++) //
		{
			if ((channel & 0x80) != 0x0) // 0x80 = 0&10000000
				mosiOutput.high();
			else
				mosiOutput.low();
			channel <<= 1;
			clockOutput.high();
			clockOutput.low();
		}

		int adcOut = 0;
		for (int i = 0; i < 12; i++) // Read in one empty bit, one null bit and
										// 10 ADC bits
		{
			clockOutput.high();
			clockOutput.low();
			adcOut <<= 1;

			if (misoInput.isHigh()) {
				// Shift one bit on the adcOut
				adcOut |= 0x1;
			}
		}
		chipSelectOutput.high();

		adcOut >>= 1; // Drop first bit
		return adcOut;
	}
}