package com.diplab.device;

import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

public class RpiSwitchController {

	private static GpioPinDigitalOutput pin = GpioFactory.getInstance()
			.provisionDigitalOutputPin(RaspiPin.GPIO_01);

	public static void off() {
		pin.low();
		System.out.println("device should be off");
	}

	public static void on() {
		pin.high();
		System.out.println("device should be on");
	}

	public static void toggle() {
		pin.toggle();
		System.out.println("device should be toggle");
	}

	public static PinState getState() {
		return pin.getState();
	}

	public static void main(String[] args) throws InterruptedException {
		for (int i = 0; i < 6; i++) {
			toggle();
			Thread.sleep(5000);
		}
	}
}
