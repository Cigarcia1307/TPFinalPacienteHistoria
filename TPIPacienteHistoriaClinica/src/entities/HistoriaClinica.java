/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

/**
 *
 * @author HP
 */
public class HistoriaClinica {
      //Ingresamos los atributos de historiaClinica
    private Long id;
    private String nroHistoria;
    private String antecedentes;
    private String medicacionActual;
    private String observaciones;
    private Boolean eliminado;
    private GrupoSanguineo grupoSanguineo;
    
    //contrcutor vacio para HistoriaClinica
    public HistoriaClinica(){
        
    }
    //constructor completo

    public HistoriaClinica(Long id, String nroHistoria, String antecedentes, String medicacionActual, String observaciones, Boolean eliminado, GrupoSanguineo grupoSanguineo) {
        this.id = id;
        this.nroHistoria = nroHistoria;
        this.antecedentes = antecedentes;
        this.medicacionActual = medicacionActual;
        this.observaciones = observaciones;
        this.eliminado = eliminado;
        this.grupoSanguineo = grupoSanguineo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNroHistoria() {
        return nroHistoria;
    }

    public void setNroHistoria(String nroHistoria) {
        this.nroHistoria = nroHistoria;
    }

    public String getAntecedentes() {
        return antecedentes;
    }

    public void setAntecedentes(String antecedentes) {
        this.antecedentes = antecedentes;
    }

    public String getMedicacionActual() {
        return medicacionActual;
    }

    public void setMedicacionActual(String medicacionActual) {
        this.medicacionActual = medicacionActual;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Boolean getEliminado() {
        return eliminado;
    }

    public void setEliminado(Boolean eliminado) {
        this.eliminado = eliminado;
    }

    public GrupoSanguineo getGrupoSanguineo() {
        return grupoSanguineo;
    }

    public void setGrupoSanguineo(GrupoSanguineo grupoSanguineo) {
        this.grupoSanguineo = grupoSanguineo;
    }

    @Override
    public String toString() {
        return "HistoriaClinica {" +
                "id:" + id+
                "nroHistoria:" + nroHistoria +
                "antecedentes:" + antecedentes +
                "medicacionActual:" + medicacionActual +
                "observaciones: " + observaciones +
                "grupoSanguineo:" + grupoSanguineo +
                '}';
    }

    

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj ==null || getClass() != obj.getClass())return false;
        HistoriaClinica that =(HistoriaClinica) obj;
        return id !=null && id.equals(that.id);
    }
    
    
    
    
    
}
