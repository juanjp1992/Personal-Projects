/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minutados;

import funciones.funciones;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;

/**
 * FXML Controller class
 *
 * @author juanj
 */
public class MinutadosController implements Initializable {

    @FXML
    ComboBox selectMinutados, selectAsignaturas;
    
    @FXML
    TextArea txtMinutado;
    
    @FXML
    Label txtEstado;
            
    ObservableList<String> asignaturas =  FXCollections.observableArrayList("PSP", "DI", "SGE", "PMDM", "AD");
    
    
    //Ruta Base
    File rutaBase = new File("\\\\192.168.1.220\\nukistorage\\Estudios\\DAM\\2_DAM\\Clases");
    
    funciones f = new funciones();
    
    @FXML
    private void btnCrearNuevo(ActionEvent event) {
        
        List<String> asignaturas = new ArrayList<>();
            asignaturas.add("PSP");
            asignaturas.add("DI");
            asignaturas.add("SGE");
            asignaturas.add("PMDM");
            asignaturas.add("AD");

            ChoiceDialog<String> dialogoAsignatura = new ChoiceDialog<>("Elige una", asignaturas);
            dialogoAsignatura.setTitle("Elige Asignatura");
            dialogoAsignatura.setHeaderText("Elige para que Asignatura");
            
            String asignatura = null;
         
            Optional<String> resultadoAsignatura = dialogoAsignatura.showAndWait();
            if (resultadoAsignatura.isPresent()){
                asignatura = resultadoAsignatura.get();
            }
            
            File rutaAsignatura = new File(rutaBase + File.separator + asignatura + File.separator + "minutado");
            
            String [] archivos = f.recuperarListadoArchivos(rutaAsignatura);        
            
            int siguienteClase = -1;
            
            if(archivos.length != 0){
                for (int i = 0; i < archivos.length; i++) {
                    String num = archivos[i].substring(archivos[i].lastIndexOf("_")+1, archivos[i].lastIndexOf("."));
                    int comprobacion = Integer.parseInt(num);
                    System.out.println(archivos[i]);

                    if(comprobacion > siguienteClase){
                        siguienteClase = comprobacion; 
                    }
                }
                siguienteClase++;
            }
            else{
                siguienteClase = 1;
            }
            
            String archivoNuevo = asignatura + "_CLASE_" + siguienteClase;
        
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Creación Minutados");
            alert.setHeaderText("Que prefieres hacer");
            alert.setContentText("Elige la predicción \"" + archivoNuevo + "\" o escribelo manualmente.");

            ButtonType buttonTypeOne = new ButtonType("Predicción");
            ButtonType buttonTypeTwo = new ButtonType("Manual");
            ButtonType buttonTypeCancel = new ButtonType("Cancelar", ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo,  buttonTypeCancel);

            Optional<ButtonType> result = alert.showAndWait();
            
            if (result.get() == buttonTypeOne){
                
                File creacion = new File(rutaAsignatura + File.separator + archivoNuevo + ".txt");
                f.crearArchivo(creacion);


            } 
            else if (result.get() == buttonTypeTwo) {
                TextInputDialog dialog2 = new TextInputDialog(asignatura + "_CLASE_");
                dialogoAsignatura.setTitle("Minutado Manual");
                dialogoAsignatura.setHeaderText("Introducción el nombre (sin el \"txt\")");

                Optional<String> nombreArchivo = dialog2.showAndWait();
                
                if (result.isPresent()){
                    File creacion = new File(rutaAsignatura + File.separator + nombreArchivo.get() + ".txt");
                    f.crearArchivo(creacion);
                }
            } 
            else {

            }
        
        
    }
    
    @FXML
    private void btnGuardarCambios(ActionEvent event) {
        //Recupero la asignatura
        String asignatura = (String) selectAsignaturas.getValue();
        String minutado = (String) selectMinutados.getValue();
        
        //Creo ruta
        File eleccion = new File(rutaBase + File.separator + asignatura + File.separator + "minutado" + File.separator + minutado);
        
        //Guardo los cambios y devuelvo estado del guardado.
        txtEstado.setText(f.guardarMinutado(eleccion, txtMinutado.getText()));
    }
    
    @FXML
    private void accionSelectMinutados(ActionEvent event) {
        //Recupero la asignatura
        String asignatura = (String) selectAsignaturas.getValue();
        
        String minutado = (String) selectMinutados.getValue();
        
        //Creo ruta
        File eleccion = new File(rutaBase + File.separator + asignatura + File.separator + "minutado" + File.separator + minutado);
        
        //Le paso el archivo 
        txtMinutado.setText(f.recuperarMinutado(eleccion));
        
    }
    
    @FXML
    private void accionSelectAsignaturas(ActionEvent event) {
        
        //Recupero la asignatura
        String asignatura = (String) selectAsignaturas.getValue();
        
        //Creo ruta
        File eleccion = new File(rutaBase + File.separator + asignatura + File.separator + "minutado");

        //Recupero los archivos que hay en la ruta y los guardo en un observable list
        ObservableList<String> minutados =  FXCollections.observableArrayList(f.recuperarListadoArchivos(eleccion));
        
        //Limpio el listado
        selectMinutados.getItems().clear();
       
        //Añado lo archivos añadidos al ObservableList a mi lista.
        selectMinutados.getItems().addAll(minutados);
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        selectAsignaturas.getItems().addAll(asignaturas);
        txtEstado.setText("");
        
    }    
    
}
