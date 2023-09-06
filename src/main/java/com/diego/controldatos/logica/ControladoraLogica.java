
package com.diego.controldatos.logica;

import com.diego.controldatos.persistencia.ControladoraPersistencia;
import java.util.List;

public class ControladoraLogica {
    
   ControladoraPersistencia controlPersis = new ControladoraPersistencia ();
    
  //Se crea para la consulta 
   public List<Principal> traerPersonas(){
   
       return controlPersis.traerPersonas();
   
   }

    public void agragarDato(String nombre, String apellido, int telefono, int edad) {
        
        Principal person = new Principal ();
        
        person.setNombre(nombre);
        person.setApellido(apellido);
        person.setTelefono(telefono);
        person.setEdad(edad);
        
        controlPersis.agregarDatos (person);
        
        
    }

    public void borrarDatos(int IdDatos) {
        controlPersis.borrarDatos(IdDatos);
    }

    public Principal traerDatos(int IdDatos) {
        
        return controlPersis.traerDatos(IdDatos);
        
    }

    public void modificarDatos(Principal person, String nombre, String apellido, int telefono, int edad) {
       
        person.setNombre(nombre);
        person.setApellido(apellido);
        person.setTelefono(telefono);
        person.setEdad(edad);
        
        //Le mando los datos a la persistencia
        controlPersis.modificarDatos(person);
        
        
    }

}
