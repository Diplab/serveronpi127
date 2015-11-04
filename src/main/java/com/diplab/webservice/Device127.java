package com.diplab.webservice;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.xml.ws.Endpoint;

import com.diplab.service.CO2Service;
import com.diplab.service.COService;
import com.diplab.service.SmokeService;
import com.diplab.serviceImp.CO2ServiceImpl;
import com.diplab.serviceImp.COServiceImpl;
import com.diplab.serviceImp.SmokeServiceImpl;

@WebService
@SOAPBinding(style = Style.RPC)
public class Device127 {

	CO2Service co2Service = new CO2ServiceImpl();
	COService coService = new COServiceImpl();
	SmokeService smokeService = new SmokeServiceImpl();

	@WebMethod
	public double CO2ppm() {
		return co2Service.CO2ppm();
	}

	@WebMethod
	public double COppm() {
		return coService.COppm();
	}

	@WebMethod
	public double getSmokePpm() {
		return smokeService.getSmokePpm();
	}

	public static void main(String[] args) {
		Endpoint.publish("http://0.0.0.0:9005/webservice/Device127",
				new Device127());

		System.out.println("open webservice127 Escape");

	}

}
