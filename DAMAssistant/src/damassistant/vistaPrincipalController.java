/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package damassistant;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

/**
 *
 * @author juanj
 */
public class vistaPrincipalController implements Initializable {
    
    @FXML
    private VBox contenido;
    
    @FXML
    private void btnTareas(ActionEvent event) {
        cargarPanel("Tareas.fxml");
    }
    
    //Método para cargar paneles
    private void cargarPanel(String panel){
        contenido.getChildren().clear();
        
        URL url = getClass().getResource(panel);
        System.out.println(url);
        try{
            Node nodo = FXMLLoader.load(url);
            
            contenido.getChildren().add(nodo);
            
            System.out.println("Panel cargado: " + panel);
        }
        catch (IOException ex){
            System.out.println("Error: No se ha podido cargar el panel: " + panel + "\n" + ex.toString());
            
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
