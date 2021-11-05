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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
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
    ComboBox selectClase, selectAsignaturas, selectMinutados;
    
    @FXML
    TextArea txtMinutado;
    
    @FXML
    Label txtEstado;
    
    @FXML
    CheckBox checkAperturaVideos, checkAutoguardado, checkModoEdicion;
    
    @FXML
    Button btnNumGrabaciones;
            
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
    private void accionSelectClase(ActionEvent event) {
        //Recupero la asignatura
        String asignatura = (String) selectAsignaturas.getValue();
        
        String clase = (String) selectClase.getValue();
        
        //Creo ruta
        File rutaVideo = new File(rutaBase + File.separator + asignatura + File.separator + clase);
       
        if(checkAperturaVideos.isSelected()){
            f.abrirArchivo(rutaVideo);
        }
        
    }
    
      @FXML
    private void accionSelectMinutados(ActionEvent event) {
        //Recupero la asignatura
        String asignatura = (String) selectAsignaturas.getValue();
        
        String clase = (String) selectClase.getValue();
        String minutado = (String) selectMinutados.getValue();
        
        
        //Creo ruta
        File rutaMinutado = new File(rutaBase + File.separator + asignatura + File.separator + "minutado" + File.separator + minutado);
      
        
        if(rutaMinutado.exists()){
            compruebaEstadoCheckModoEdicion();
        }
        else{
            this.txtMinutado.setEditable(false);
        }
        
        //Le paso el archivo a abrir 
        txtMinutado.setText(f.recuperarMinutado(rutaMinutado));
        
        
    }
    
    @FXML
    private void accionSelectAsignaturas(ActionEvent event) {
        
        selectClase.getItems().clear();
        selectMinutados.getItems().clear();
        txtMinutado.clear();
        //Recupero la asignatura
        String asignatura = (String) selectAsignaturas.getValue();
        
        //Creo ruta
        File rutaClases = new File(rutaBase + File.separator + asignatura);
        File rutaMinutados = new File(rutaBase + File.separator + asignatura + File.separator + "minutado");
        
        //Escondo la carpeta minutados
        String [] recuperoMinutados = f.recuperarListadoArchivos(rutaMinutados);
        String [] videosClases = f.recuperarListadoArchivos(rutaClases);
        String [] resultado = new String [videosClases.length - 1];
        int contador = 0;
        for (int i = 0; i < videosClases.length; i++) {
            //resultado[i-1] =  archivos[i];
            if(!videosClases[i].contains("minutado")){
                resultado[contador] = videosClases[i];
                contador++;
            }
        }

        //Recupero los archivos que hay en la ruta y los guardo en un observable list
        
        ObservableList<String> clases =  FXCollections.observableArrayList(resultado);
        ObservableList<String> minutados =  FXCollections.observableArrayList(recuperoMinutados);
       
        //Añado lo archivos añadidos al ObservableList a mi lista.
        selectClase.getItems().addAll(clases);
        selectMinutados.getItems().addAll(minutados);
    }
    
    private void compruebaEstadoCheckModoEdicion(){
        if(checkModoEdicion.isSelected()){
            this.txtMinutado.setEditable(true);
        }
        else{
            this.txtMinutado.setEditable(false);
        }
    }
    
    @FXML
    private void clickModoEdicion(ActionEvent event) {
        compruebaEstadoCheckModoEdicion();
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
        selectAsignaturas.getItems().addAll(asignaturas);
        txtEstado.setText("");
        this.txtMinutado.setEditable(false);
        actualizaBtnGrabacion();
        
        
    }    
    
}
