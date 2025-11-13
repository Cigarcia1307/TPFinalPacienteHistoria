/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package entities;

/**
 *
 * @author HP
 */
public enum GrupoSanguineo {
     A_POSITIVO ("A+"),
     A_NEGATIVO ("A-"),
     B_POSITIVO ("B+"), 
     B_NEGATIVO ("B-"), 
     AB_POSITIVO ("AB+"),
     AB_NEGATIVO ("AB-"),
     O_POSITIVO ("O+"),
     O_NEGATIVO ("O-");
     
     private final String simbolo;
     
     GrupoSanguineo(String simbolo){
         this.simbolo =simbolo;
     }

    @Override
    public String toString() {
        return simbolo;
    }
     
     
    
}
