/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package damassistant;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import funciones.funciones;

/**
 * FXML Controller class
 *
 * @author juanj
 */
public class VistaInicialController implements Initializable {
    @FXML
    Label txtNumGrabaciones, txtUDPSP, txtUDDI, txtUDAD, txtUDSGE, txtUDPMDM,
          asig1, asig2, asig3, asig4, asig5, asig6, asig7, asig8, asig9, asig10, hora1, hora2, hora3, hora4,
          hora5, hora6, hora7, hora8, hora9, hora10, clase1, clase2, clase3, clase4, clase5, clase6, clase7, 
          clase8, clase9, clase10, dia1, dia2, dia3, dia4, dia5, dia6, dia7, dia8, dia9, dia10;
    
    //Creo objeto de la clase funciones, para traerme todas las funcionalidades
    funciones f = new funciones();
    
    //Método que hace recuento de las grabaciones
    private void actualizarNumGrabaciones(){
        txtNumGrabaciones.setText("--> " + String.valueOf(f.numClasesParaOrganizar()));
    }
    
    private void actualizarUltimasTareas(){
        txtUDPSP.setText(f.unidadActualAsignatura()[0]);
        txtUDDI.setText(f.unidadActualAsignatura()[1]);
        txtUDAD.setText(f.unidadActualAsignatura()[2]);
        txtUDSGE.setText(f.unidadActualAsignatura()[3]);
        txtUDPMDM.setText(f.unidadActualAsignatura()[4]);
    }
    private void limpiaHorario(){
    
        asig1.setText("");
        clase1.setText("");
        dia1.setText("");
        hora1.setText("");
        
       
        asig2.setText("");
        clase2.setText("");
        dia2.setText("");
        hora2.setText("");
        
        asig3.setText("");
        clase3.setText("");
        dia3.setText("");
        hora3.setText("");
        
        asig4.setText("");
        clase4.setText("");
        dia4.setText("");
        hora4.setText("");
        
        asig5.setText("");
        clase5.setText("");
        dia5.setText("");
        hora5.setText("");
        
        asig6.setText("");
        clase6.setText("");
        dia6.setText("");
        hora6.setText("");
        
        asig7.setText("");
        clase7.setText("");
        dia7.setText("");
        hora7.setText("");
        
        asig8.setText("");
        clase8.setText("");
        dia8.setText("");
        hora8.setText("");
       
        asig9.setText("");
        clase9.setText("");
        dia9.setText("");
        hora9.setText("");
        
        asig10.setText("");
        clase10.setText("");
        dia10.setText("");
        hora10.setText("");
        
    }
    
    private void cargarHorario(){
    
        asig1.setText(f.proximaClase()[0]);
        clase1.setText(f.proximaClase()[1]);
        dia1.setText(f.proximaClase()[2]);
        hora1.setText(f.proximaClase()[3]);
        
        
        asig2.setText(f.proximaClase()[4]);
        clase2.setText(f.proximaClase()[5]);
        dia2.setText(f.proximaClase()[6]);
        hora2.setText(f.proximaClase()[7]);
        
        asig3.setText(f.proximaClase()[8]);
        clase3.setText(f.proximaClase()[9]);
        dia3.setText(f.proximaClase()[10]);
        hora3.setText(f.proximaClase()[11]);
        
        asig4.setText(f.proximaClase()[12]);
        clase4.setText(f.proximaClase()[13]);
        dia4.setText(f.proximaClase()[14]);
        hora4.setText(f.proximaClase()[15]);
        
        asig5.setText(f.proximaClase()[16]);
        clase5.setText(f.proximaClase()[17]);
        dia5.setText(f.proximaClase()[18]);
        hora5.setText(f.proximaClase()[19]);
        /*
        asig6.setText(f.proximaClase()[20]);
        clase6.setText(f.proximaClase()[21]);
        dia6.setText(f.proximaClase()[22]);
        hora6.setText(f.proximaClase()[23]);
        */
        asig7.setText(f.proximaClase()[24]);
        clase7.setText(f.proximaClase()[25]);
        dia7.setText(f.proximaClase()[26]);
        hora7.setText(f.proximaClase()[27]);
        /*
        asig8.setText(f.proximaClase()[28]);
        clase8.setText(f.proximaClase()[29]);
        dia8.setText(f.proximaClase()[30]);
        hora8.setText(f.proximaClase()[31]);
        */
        asig9.setText(f.proximaClase()[32]);
        clase9.setText(f.proximaClase()[33]);
        dia9.setText(f.proximaClase()[34]);
        hora9.setText(f.proximaClase()[35]);
        /*
        asig10.setText(f.proximaClase()[36]);
        clase10.setText(f.proximaClase()[37]);
        dia10.setText(f.proximaClase()[38]);
        hora10.setText(f.proximaClase()[39]);
        */
        
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        actualizarNumGrabaciones();
        actualizarUltimasTareas();
        limpiaHorario();
        cargarHorario();
    }    
    
}
