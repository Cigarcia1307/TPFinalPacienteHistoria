/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.time.LocalDate;

/**
 *
 * @author HP
 */
public class Paciente {
    //Ingresamos los atributos de Paciente
    private Long id;
    private String nombre;
    private String apellido;
    private String dni;
    private LocalDate fechaNacimiento;
    private Boolean eliminado;
    private HistoriaClinica historiaClinica;
    
    public Paciente(){
    }
    
    //constructor completo

    public Paciente(Long id, String nombre, String apellido, String dni, LocalDate fechaNacimiento, Boolean eliminado, HistoriaClinica historiaClinia) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.fechaNacimiento = fechaNacimiento;
        this.eliminado = eliminado;
        this.historiaClinica = historiaClinica;
    }
    //getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Boolean getEliminado() {
        return eliminado;
    }

    public void setEliminado(Boolean eliminado) {
        this.eliminado = eliminado;
    }

    public HistoriaClinica getHistoriaClinica() {
        return historiaClinica;
    }

    public void setHistoriaClinia(HistoriaClinica historiaClinica) {
        this.historiaClinica = historiaClinica;
    }
    //toString
     @Override
     public String toString(){
        return "Paciente {" +
                "id:" + id + " , " +
                "Nombre:" + nombre + " , " +
                "Apellido:" + apellido + " , " +
                "Dni:" + dni + " , " +
                "Fecha_Nacimiento:"+ fechaNacimiento + " , " +
                "eliminado:"+ eliminado + " , " +
                "Historia_Clinica:" + historiaClinica + 
                '}';
                
     }
    
    //equals
     @Override
     public boolean equals (Object obj){
         if (this == obj) return true;
         if (obj == null || getClass () != obj.getClass())return false;
         Paciente paciente = (Paciente) obj;
         return id != null && id.equals(paciente.id);
         
         
     }
    
    
    
    
    
    
}
