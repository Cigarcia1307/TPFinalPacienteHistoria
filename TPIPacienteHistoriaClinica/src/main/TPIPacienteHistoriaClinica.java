package main;

import entities.GrupoSanguineo;
import entities.HistoriaClinica;
import entities.Paciente;
import java.time.LocalDate;

public class TPIPacienteHistoriaClinica {

    public static void main(String[] args) {
        // PRUEBA  DE CODIGO CON  PACIENTE E HISTORIA CLINICA
        HistoriaClinica historia = new HistoriaClinica(
                1L,
                "HC000",
                "Covid",
                "Paracetamol",
                "Problemas respiratorios",
                false,
                GrupoSanguineo.A_POSITIVO
        );

        Paciente paciente = new Paciente(
                2L,
                "Daniel",
                "Gomez",
                "23564543",
                LocalDate.of(1974, 4, 5),
                false,
                historia
        );

        System.out.println(paciente);
    }
}