/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dammanager;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 *
 * @author joan-
 */
public class InterfazController implements Initializable {
    @FXML 
    Label numGrabaciones = new Label();
    
    @FXML
    private void organizacionGrabaciones(ActionEvent event) {
        try {
            Process p2 = new ProcessBuilder("java", "-jar", "AutoGuardadoVideosDAM.jar").inheritIO().start();
        } catch (IOException ex) {
            Logger.getLogger(InterfazController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    @FXML
    private void creacionTareas(ActionEvent event) {
        try {
            Process p = new ProcessBuilder("java", "-jar", "AutoCreacionTareasDAM.jar").inheritIO().start();
            
            
        } catch (Exception ex) {
            Logger.getLogger(InterfazController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void cuentaGrabaciones(){
        File path = new File("\\\\192.168.1.220\\nukistorage\\Estudios\\DAM\\2_DAM\\Clases");
        String [] archivos = path.list();
        int contadorGrabaciones = 0;
        for (int i = 0; i < archivos.length-1; i++) {
            System.out.println(archivos[i]);
            if(!archivos[i].equalsIgnoreCase("DI") && !archivos[i].equalsIgnoreCase("PMDM") && !archivos[i].equalsIgnoreCase("PSP") && !archivos[i].equalsIgnoreCase("SGE") && !archivos[i].equalsIgnoreCase("AutoGuardadoVideosDAM.jar")){
                contadorGrabaciones++;
            }
        }
        
        numGrabaciones.setText(String.valueOf(contadorGrabaciones));
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cuentaGrabaciones();
    }    
    
}
