package com.leveltrack.view;


import com.leveltrack.controller.UserController;


import java.util.Scanner;


public class UserManagementView {
    public static void main(String[] args) {
        try {
            UserController controller = new UserController();
            Scanner scanner = new Scanner(System.in);


            System.out.println("=== Gestión de Perfil ===");


            // Solicitar email o nombre de usuario
            System.out.print("Email o Usuario: ");
            String identifier = scanner.nextLine();


            // Solicitar contraseña para verificar identidad
            System.out.print("Contraseña: ");
            String password = scanner.nextLine();


            // Solicitar nuevos datos
            System.out.print("Nuevo Nombre: ");
            String name = scanner.nextLine();


            System.out.print("Nuevo Email: ");
            String newEmail = scanner.nextLine();


            System.out.print("Nueva Contraseña: ");
            String newPassword = scanner.nextLine();


            // Llamar al controlador para procesar los cambios
            String result = controller.updateProfile(identifier, password, name, newEmail, newPassword);
            System.out.println(result);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}





