package com.diplab.device;

import com.pi4j.wiringpi.Spi;

public class RpiCO2 {

	/************************ Hardware Related Macros ************************************/
	private static final double DC_GAIN = 3;
	private static final int MG_PIN = 0;

	/*********************** Software Related Macros ************************************/
	private static final int READ_SAMPLE_TIMES = 15;
	private static final long READ_SAMPLE_INTERVAL = 100;

	/********************** Application Related Macros **********************************/
	private static final double ZERO_POINT_VOLTAGE = 0.49657;

	/***************************** Globals ***********************************************/
	// {x, y, slope} > (x,y) = (0.280,log(4000)), (0.324, log(400)) slope =
	// -0.044
	private static double[] CO2Curve = { 2.602, ZERO_POINT_VOLTAGE, -0.044 };

	static {
		Spi.wiringPiSPISetup(Spi.CHANNEL_0, 500000);
	}

	public static void main(String[] args) throws InterruptedException {

		while (true) {
			System.out.format("co2 = %-10.3f ppm%n", RpiCO2.get());
			Thread.sleep(1000);

		}
	}

	static double readadc_MG811(int ch) {

		byte data[] = { 1, (byte) ((8 + ch) << 4), 0 };
		Spi.wiringPiSPIDataRW(Spi.CHANNEL_0, data);

		return ((data[1] & 0x03) << 8) + data[2];

	}

	/******************************* MGRead *********************************************
	 * Input: mg_pin - analog channel
	 * Output: output of SEN-000007
	 * Remarks: This function reads the output of SEN-000007
	 ************************************************************************************/
	static double MGRead(int mg_pin) {

		double v = 0;

		for (int i = 0; i < READ_SAMPLE_TIMES; i++) {
			v += readadc_MG811(mg_pin);
			try {
				Thread.sleep(READ_SAMPLE_INTERVAL);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		v = (v / READ_SAMPLE_TIMES) * 5 / 1024;
		return v;

	}

	static double MGGetPpm(double volts, double[] pcurve) {
		volts = volts / DC_GAIN;
		if (volts >= ZERO_POINT_VOLTAGE) {
			return -1;
		} else {
			return Math.pow(10, (volts - pcurve[1]) / pcurve[2] + pcurve[0]);
		}
	}

	static public double get() {
		double volts = MGRead(MG_PIN);
		System.out.println(volts + "V");
		double ppm = MGGetPpm(volts, CO2Curve);

		if (ppm == -1)
			ppm = 400;

		return ppm;
	}
}
