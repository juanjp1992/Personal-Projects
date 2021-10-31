/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grabaciones;

import funciones.funciones;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import funciones.funciones;
import java.io.File;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

/**
 * FXML Controller class
 *
 * @author juanj
 */
public class VistaGrabacionesController implements Initializable {

    @FXML
    ComboBox selectAsignaturas;
    @FXML
    ListView listaGrabaciones;
    @FXML
    TextArea inputMinutado;
    @FXML
    Button btnNumGrabaciones;
   
    
    //Escribe las opciones en el ComboBox
    ObservableList<String> options =  FXCollections.observableArrayList("PSP", "DI", "SGE", "PMDM", "AD");
    
    funciones f = new funciones();

    
    @FXML
    private void eventoSeleccionAsignatura(ActionEvent event) {
        String [] archivosGrabaciones = f.grabacionesAsignaturas((String) selectAsignaturas.getValue());
        
        ObservableList<String> archivosMP4 = FXCollections.observableArrayList (archivosGrabaciones);
        
        listaGrabaciones.setItems(archivosMP4);
    }
    
    @FXML
    private void btnAbrirGrabaciones(ActionEvent event){
        File grabacion = new File("\\\\192.168.1.220\\nukistorage\\Estudios\\DAM\\2_DAM\\Clases\\"+ 
        (String) selectAsignaturas.getValue() + File.separator + listaGrabaciones.getSelectionModel().getSelectedItem());
        
        File minutado = new File("\\\\192.168.1.220\\nukistorage\\Estudios\\DAM\\2_DAM\\Clases\\"+ 
        (String) selectAsignaturas.getValue() + File.separator + "minutado"+ File.separator + (listaGrabaciones.getSelectionModel().getSelectedItem()).toString().replace(".mp4", ".txt"));
        
        //Abrimos el archivo txt.
        inputMinutado.setText(f.recuperarMinutado(minutado));
        
        //Abrimos la grabación
        f.abrirArchivo(grabacion);
        
        
    }
    
    @FXML
    private void btnGuardarGrabaciones(ActionEvent event){
        
        //Activo el asistente de guardado de videos
        f.guardarGrabaciones();
        
        //Actualiza el estado del botón.
        actualizaBtnGrabacion();
    }
    
    
    private void actualizaBtnGrabacion(){
        //Activa la cantidad de grabaciones
        btnNumGrabaciones.setText("Grabaciones por Almacenar: " + String.valueOf(f.numClasesParaOrganizar()));
        //Activa y desactiva según el número el botón de cantidad.
        if(f.numClasesParaOrganizar() == 0){
            btnNumGrabaciones.setDisable(true);
        }
        else{
            btnNumGrabaciones.setDisable(false);
        }
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        selectAsignaturas.getItems().addAll(options);
        actualizaBtnGrabacion();
        
        
    }    
    
}
