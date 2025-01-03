package com.leveltrack.view;

import  com.leveltrack.controller.RegisterController;

import java.util.Scanner;

public class RegisterView {
    public static void main(String[] args) {
        try {
            RegisterController registerController = new RegisterController();
            Scanner scanner = new Scanner(System.in);

            System.out.println("=== Registro de Usuario ===");
            System.out.print("Nombre: ");
            String name = scanner.nextLine();

            System.out.print("Email: ");
            String email = scanner.nextLine();

            System.out.print("Contraseña: ");
            String password = scanner.nextLine();

            System.out.print("Rol (ADMINISTRADOR, MODERADOR, USUARIO_REGULAR): ");
            String role = scanner.nextLine().toUpperCase();

            String result = registerController.register(name, email, password, role);
            System.out.println(result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
