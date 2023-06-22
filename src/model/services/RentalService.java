package model.services;

import java.time.Duration;

import model.entities.CarRental;
import model.entities.Invoice;

public class RentalService {
	
	private Double pricePerHour;
	private Double pricePerDay;
	
	private TaxService taxService;

	public RentalService(Double pricePerHour, Double pricePerDay, TaxService tax) {
		this.pricePerHour = pricePerHour;
		this.pricePerDay = pricePerDay;
		this.taxService = tax;
	}

	public Double getPricePerHour() {
		return pricePerHour;
	}

	public void setPricePerHour(Double pricePerHour) {
		this.pricePerHour = pricePerHour;
	}

	public Double getPricePerDay() {
		return pricePerDay;
	}

	public void setPricePerDay(Double pricePerDay) {
		this.pricePerDay = pricePerDay;
	}

	public TaxService getTax() {
		return taxService;
	}

	public void setTax(BrasilTaxService tax) {
		this.taxService = tax;
	}
	
	public void processInvoice (CarRental carRental) {
		double minutes = Duration.between(carRental.getStart(), carRental.getFinish()).toMinutes();
		double duration = minutes/60;
		
		double basicPayment;
		
		if (duration <= 12.0) {
			basicPayment = Math.ceil(duration) * getPricePerHour(); 
		}
		else {
			basicPayment = Math.ceil(duration/24.0) * getPricePerDay();
		}	
		
		double tax = taxService.tax(basicPayment);
					
		carRental.setInvoice(new Invoice(basicPayment, tax));
	}
}
