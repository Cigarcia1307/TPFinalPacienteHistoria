/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package tpipacientehistoriaclinica;

import entities.GrupoSanguineo;
import entities.HistoriaClinica;
import entities.Paciente;
import java.time.LocalDate;

/**
 *
 * @author HP
 */
public class TPIPacienteHistoriaClinica {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // PRUEBA  DE CODIGO CON  PACIENTE E HISTORIA CLINICA
        HistoriaClinica historia= new HistoriaClinica (
                01L,
                "HC000",
                "Covid",
                "Paracetamol",
                "Problemas respiratorios",
                false,
                GrupoSanguineo.A_POSITIVO
        
        
        );
        
        Paciente paciente= new Paciente(
                02L,
                "Daniel",
                "Gomez",
                "23564543",
                LocalDate.of(1974,04,05),
                false,
                historia
        );
        
        //imprimir 
        System.out.println(paciente);
                
                
    }
    
}
