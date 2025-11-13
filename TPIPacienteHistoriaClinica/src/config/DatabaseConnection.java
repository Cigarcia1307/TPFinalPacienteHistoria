/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 *
 * @author HP
 */
public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/BDPacienteHistoriaClinica";
    private static final String USER = "root";
    private static final String PASSWORD = "PuntaCana2030";// cambiar por contraseña esta es la mia Cintia
    
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Error al conectar con la base de datos", e);
        }
    }
}
   

