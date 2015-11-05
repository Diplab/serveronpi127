package com.diplab.service;

import com.pi4j.io.gpio.PinState;

public interface SwitchService {

	void off();

	void on();

	void toggle();

	PinState getState();

}
