package view;

import controller.LoginController;

import java.util.Scanner;

public class LoginView {
    public static void main(String[] args) {
        try {
            LoginController loginController = new LoginController();
            Scanner scanner = new Scanner(System.in);

            System.out.println("=== Login ===");
            System.out.print("Email: ");
            String email = scanner.nextLine();

            System.out.print("Contraseña: ");
            String password = scanner.nextLine();

            String result = loginController.login(email, password);
            System.out.println(result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

