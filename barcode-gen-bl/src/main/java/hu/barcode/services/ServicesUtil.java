package hu.barcode.services;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@Component
@ComponentScan
public class ServicesUtil extends ServiceBase{

	public Double getDatamatrixPrice(int charNum) {

		return Double.valueOf(1000);
	}

	public Double getGs1Price(int gs1Num) {

		return Double.valueOf(1000);
	}
	public String getOrderNumber() {
		return "12345678";
	}
}
