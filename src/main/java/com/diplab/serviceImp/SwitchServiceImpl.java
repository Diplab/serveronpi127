package com.diplab.serviceImp;

import com.diplab.device.RpiSwitchController;
import com.diplab.service.SwitchService;
import com.pi4j.io.gpio.PinState;

public class SwitchServiceImpl implements SwitchService {

	@Override
	public void off() {
		RpiSwitchController.off();
		return;

	}

	@Override
	public void on() {
		RpiSwitchController.on();
		return;
	}

	@Override
	public void toggle() {
		RpiSwitchController.toggle();
		return;
	}

	@Override
	public PinState getState() {
		return RpiSwitchController.getState();
	}

}
