package com.diego.controldatos.persistencia;



import com.diego.controldatos.logica.Principal;
import com.diego.controldatos.persistencia.exceptions.NonexistentEntityException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControladoraPersistencia {

    PrincipalJpaController datosJPa = new PrincipalJpaController();

    public void agregarDatos(Principal person) {

        datosJPa.create(person);

    }

    public List<Principal> traerPersonas() {

        return datosJPa.findPrincipalEntities();

    }

    public void borrarDatos(int IdAuto) {
        try {
            datosJPa.destroy(IdAuto);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Principal traerDatos(int IdDatos) {
        
        return datosJPa.findPrincipal(IdDatos);
        
    }

    public void modificarDatos(Principal person) {
        try {
            datosJPa.edit(person);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
