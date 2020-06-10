package communication;

import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class SpringBootApp extends SpringBootServletInitializer {
	public static void main(String[] args) {
		SpringApplication.run(SpringBootApp.class, args);
		GreetingController gc = new GreetingController();

		while (true) {
			Scanner scanner = new Scanner(System.in);
			String inputString = scanner.nextLine();
			System.out.println("String read from console is : \n" + inputString);

			try {
				gc.send(new Message("Momo", "test"));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}