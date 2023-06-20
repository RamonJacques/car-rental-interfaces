package application;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Scanner;

import model.entities.CarRental;
import model.entities.Vehicle;
import model.services.BrasilTaxService;
import model.services.RentalService;

public class Program {

	public static void main(String[] args) {

		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		
		DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		
		System.out.println("Enter with rent data.");
		System.out.print("Car model: ");
		String carModel = sc.nextLine();
		System.out.print("Start (dd/MM/yyyy HH:mm): ");
		LocalDateTime start = LocalDateTime.parse(sc.nextLine(),dtf1);
		System.out.print("Finish (dd/MM/yyyy HH:mm): ");
		LocalDateTime finish = LocalDateTime.parse(sc.nextLine(),dtf1);
		
		CarRental cr = new CarRental(start, finish, new Vehicle(carModel));
		
		System.out.print("Enter price per hour: ");
		Double pricePerHour = sc.nextDouble();
		System.out.print("Enter price per day: ");
		Double pricePerDay = sc.nextDouble();
		
		RentalService rentalService = new RentalService(pricePerHour, pricePerDay, new BrasilTaxService());
		rentalService.processInvoice(cr);
		
		System.out.println("FATURA:");
		System.out.println("Basic payment: "+ String.format("%.2f", cr.getInvoice().getBasicPayment()));
		System.out.println("Tax: "+ String.format("%.2f",cr.getInvoice().getTax()));
		System.out.println("Total payment: "+ String.format("%.2f",cr.getInvoice().getTotalPayment()));
		
		sc.close();
	}

}
