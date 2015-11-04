package com.diplab.serviceImp;

import com.diplab.device.RpiCO2;
import com.diplab.service.CO2Service;

public class CO2ServiceImpl implements CO2Service {

	@Override
	public double CO2ppm() {
		return RpiCO2.get();
	}

}
