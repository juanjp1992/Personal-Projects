/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tareas;

import funciones.funciones;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author juanj
 */
public class TareasController implements Initializable {
       

    @FXML
    Button btnAD, btnPMDM, btnSGE, btnDI, btnPSP, btnMenosEstado, btnMasEstado, btnFusionarPDF;
    
    @FXML
    ComboBox selectUnidad, selectAsignaturasTareas, selectAsignaturasTema, selectAsignaturas, selectAsignaturasPDF, selectFuncionalidad, selectAsignaturasWord;
    
    @FXML
    Label txtResultadoEstructura;
    
    @FXML
    ListView listaTemas;
    
    @FXML
    TextField txtNumUnidad, txtRuta, txtContador, txtEnlaceWord;
    
    @FXML
    AnchorPane PanelTareas;
    
    funciones f = new funciones();
    
    File PSP = new File("\\\\192.168.1.220\\nukistorage\\Estudios\\DAM\\2_DAM\\Tareas\\PSP");
    File SGE = new File("\\\\192.168.1.220\\nukistorage\\Estudios\\DAM\\2_DAM\\Tareas\\SGE");
    File DI = new File("\\\\192.168.1.220\\nukistorage\\Estudios\\DAM\\2_DAM\\Tareas\\DI");
    File PMDM = new File("\\\\192.168.1.220\\nukistorage\\Estudios\\DAM\\2_DAM\\Tareas\\PMDM");
    File AD = new File("\\\\192.168.1.220\\nukistorage\\Estudios\\DAM\\2_DAM\\Tareas\\AD");
    
    File rutaBase = new File("\\\\192.168.1.220\\nukistorage\\Estudios\\DAM\\2_DAM\\Temarios");
    
    File rutaPDFOrigen = null; 
    File seleccionTemario = null;
    
    
    
    //Opciones Combobox Asignaturas
    ObservableList<String> asignaturasCB =  FXCollections.observableArrayList("PSP", "DI", "SGE", "PMDM", "AD");
    
    ObservableList<String> funcionalidad =  FXCollections.observableArrayList("Abrir Clase Online", "Abrir Asignatura", "Abrir Tarea");
        
    
    
    @FXML
    private void btnCrearEstructura(ActionEvent event) {
        txtResultadoEstructura.setText(f.crearEstructuraTarea((String) selectAsignaturasTareas.getValue(), txtEnlaceWord.getText()));
        actualizaUnidadActual();
        
        txtEnlaceWord.clear();
        
    }
    
    @FXML
    private void accionSelectAsignatura(ActionEvent event){
        
        seleccionTemario = new File(rutaBase + File.separator + (String) selectAsignaturasTema.getValue());
                
        String [] archivosTemarios = f.recuperarListadoArchivos(seleccionTemario);
        
        ObservableList<String> listaArchivos = FXCollections.observableArrayList (archivosTemarios);
        
        listaTemas.setItems(listaArchivos);
    }
    
    @FXML
    private void btnAbrirTemario(ActionEvent event){
        
        File rutaTemario = new File(seleccionTemario + File.separator + (listaTemas.getSelectionModel().getSelectedItem()).toString());
        f.abrirArchivo(rutaTemario);
    }
    
    
    
    @FXML
    private void btnPSPFolder(ActionEvent event) {

        //Creo ruta hasta la unidad
        File unidades = new File(PSP + File.separator + "UD" + f.numUnidades()[0]);
        
        //Abro la carpeta de la unidad
        f.abrirCarpeta(unidades);
    }
    @FXML
    private void btnSGEFolder(ActionEvent event) {
               
        //Creo ruta hasta la unidad
        File unidades = new File(SGE + File.separator + "UD" + f.numUnidades()[1]);
        
        //Abro la carpeta de la unidad
        f.abrirCarpeta(unidades);
        
    }
    @FXML
    private void btnDIFolder(ActionEvent event) {
        
        //Creo ruta hasta la unidad
        File unidades = new File(DI + File.separator + "UD" + f.numUnidades()[2]);
        
        //Abro la carpeta de la unidad
        f.abrirCarpeta(unidades);
        
    }
    @FXML
    private void btnPMDMFolder(ActionEvent event) {
        
        //Creo ruta hasta la unidad
        File unidades = new File(PMDM + File.separator + "UD" + f.numUnidades()[3]);
        
        //Abro la carpeta de la unidad
        f.abrirCarpeta(unidades);
    }
    @FXML
    private void btnADFolder(ActionEvent event) {
        
        //Creo ruta hasta la unidad
        File unidades = new File(AD + File.separator + "UD" + f.numUnidades()[4]);
        
        //Abro la carpeta de la unidad
        f.abrirCarpeta(unidades);
    }
    
        
    private void actualizaUnidadActual(){
        //btnAD, btnPMDM, btnSGE, btnDI, btnPSP;
        
        btnPSP.setText("UD"+f.numUnidades()[0]);
        btnSGE.setText("UD"+f.numUnidades()[1]);
        btnDI.setText("UD"+f.numUnidades()[2]);
        btnPMDM.setText("UD"+f.numUnidades()[3]);
        btnAD.setText("UD"+f.numUnidades()[4]);
    }
    
    @FXML
    private void accionSelectFuncionalidad(ActionEvent event) {
        
        if(selectFuncionalidad.getValue().equals("Abrir Tarea")){
            if(Integer.parseInt(txtNumUnidad.getText()) != 1){
                btnMenosEstado.setDisable(false);
                btnMasEstado.setDisable(false);
            }
            else{
                btnMasEstado.setDisable(false);
            }
        }
        else{
            btnMenosEstado.setDisable(true);
            btnMasEstado.setDisable(true);
        }
    } 
    
    
    
    @FXML
    private void btnMenos(ActionEvent event) {
        txtNumUnidad.setText(String.valueOf((Integer.parseInt(txtNumUnidad.getText())-1)));
        if(Integer.parseInt(txtNumUnidad.getText()) == 1){
            btnMenosEstado.setDisable(true);
        }
    }
    
    @FXML
    private void btnMas(ActionEvent event) {
        txtNumUnidad.setText(String.valueOf((Integer.parseInt(txtNumUnidad.getText())+1)));
        if(Integer.parseInt(txtNumUnidad.getText()) != 1){
            btnMenosEstado.setDisable(false);
        }
    }
    
    
    @FXML
    private void btnAbrir(ActionEvent event) {
        String asignatura = (String) selectAsignaturas.getValue();
        String funcionalidad = (String) selectFuncionalidad.getValue();
        int unidad = Integer.parseInt(txtNumUnidad.getText());
               
        String formatoUnidad = null;
        
        if(unidad <= 9){
            formatoUnidad = "0" + unidad; 
        }
        else{
            formatoUnidad = String.valueOf(unidad);
        }
        
        if(funcionalidad.equals("Abrir Clase Online")){
            if(asignatura.equals("PSP")){
                f.abrirWeb("https://inst-foc.adobeconnect.com/r0jbfhcvmsdk/?launcher=false&proto=true");
            }
            if(asignatura.equals("DI")){
                f.abrirWeb("https://inst-foc.adobeconnect.com/rf5mbw49i18f/?launcher=false");
            }
            if(asignatura.equals("SGE")){
                f.abrirWeb("https://inst-foc.adobeconnect.com/rkr1ea7b992q/?launcher=false&proto=true");
            }
            if(asignatura.equals("AD")){
                f.abrirWeb("https://inst-foc.adobeconnect.com/rwuhz9bgiosi/?launcher=false&proto=true");
            }
            if(asignatura.equals("PMDM")){
                f.abrirWeb("https://inst-foc.adobeconnect.com/r10dt0o18fuu/?launcher=false&proto=true");
            }
            
        }
        if(funcionalidad.equals("Abrir Asignatura")){
            if(asignatura.equals("PSP")){
                f.abrirWeb("https://fp.foc.es/course/view.php?id=54");
            }
            if(asignatura.equals("DI")){
                f.abrirWeb("https://fp.foc.es/course/view.php?id=43");
            }
            if(asignatura.equals("SGE")){
                f.abrirWeb("https://fp.foc.es/course/view.php?id=45");
            }
            if(asignatura.equals("AD")){
                f.abrirWeb("https://fp.foc.es/course/view.php?id=42");
            }
            if(asignatura.equals("PMDM")){
                f.abrirWeb("https://fp.foc.es/course/view.php?id=44");
            }
            if(asignatura.equals("MTA")){
                f.abrirWeb("https://fp.foc.es/course/view.php?id=16");
            }
            
        }
        if(funcionalidad.equals("Abrir Tarea")){
            f.abrirWeb("https://fp.foc.es/tareas/2D-"+ asignatura +"/"+ asignatura + "_TI_"+ formatoUnidad +"/descripcin_de_la_tarea.html");
            
        }
        
    }
    
    @FXML
    private void accionSelectAsignaturaPDF(ActionEvent event){
        try{
            String asignatura = (String) selectAsignaturasPDF.getValue();
            if(asignatura.equals("PSP") || asignatura.equals("AD") || asignatura.equals("SGE") || asignatura.equals("PMDM") || asignatura.equals("DI")){
                btnFusionarPDF.setDisable(false);
            }
            
        }
        catch(Exception ex){
            
        }
    }
    
    @FXML
    private void btnBusquedaArchivo(ActionEvent event){
        final DirectoryChooser dc = new DirectoryChooser();
        
        Stage stage = (Stage) PanelTareas.getScene().getWindow();
        
        rutaPDFOrigen = dc.showDialog(stage);
        
        if(rutaPDFOrigen != null){
            txtRuta.setText(rutaPDFOrigen.getAbsolutePath());
        }
        
    }
    
    @FXML
    private void accionFusionarPDF(ActionEvent event){
        f.fusionPDF(rutaPDFOrigen, (String) selectAsignaturasPDF.getValue());
        txtRuta.clear();
    }
    
    @FXML
    private void btnContadorMas(ActionEvent event){
        txtContador.setText(String.valueOf(Integer.parseInt(txtContador.getText())+1));
    }
    
    @FXML
    private void btnContadorMenos(ActionEvent event){
        txtContador.setText(String.valueOf(Integer.parseInt(txtContador.getText())-1));
    }
    
    @FXML
    private void btnReset(ActionEvent event){
        txtContador.setText("1");
    }
    
    @FXML
    private void btnAbrirWord(ActionEvent event){
        
       File rutaWords = new File ("\\\\192.168.1.220\\nukistorage\\Estudios\\DAM\\2_DAM\\Tareas" + File.separator + selectAsignaturasWord.getValue() + 
               File.separator + selectUnidad.getValue()+ File.separator +"enlace_word.txt");
       if(rutaWords.exists()){
       
       String url = f.recuperarMinutado(rutaWords).trim();
  
        System.out.println(url);
        f.abrirWeb(url);
       }
       else{
           File rutaWordsFisicos = new File ("\\\\192.168.1.220\\nukistorage\\Estudios\\DAM\\2_DAM\\Tareas" + File.separator + selectAsignaturasWord.getValue() + 
               File.separator + selectUnidad.getValue());
           
           String [] archivosFisicos = rutaWordsFisicos.list();
           
           for (int i = 0; i < archivosFisicos.length; i++) {
               
               if(archivosFisicos[i].contains(".doc")){
                   File abreWord = new File ("\\\\192.168.1.220\\nukistorage\\Estudios\\DAM\\2_DAM\\Tareas" + File.separator + selectAsignaturasWord.getValue() + 
                    File.separator + selectUnidad.getValue() + File.separator + archivosFisicos[i]);
                   f.abrirArchivo(abreWord);
               }
               
           }
       }
    }
    
     @FXML
    private void accionSelectAsignaturaWord(ActionEvent event) {
        
        if(selectAsignaturasWord.getValue().equals("PSP") || selectAsignaturasWord.getValue().equals("SGE") || selectAsignaturasWord.getValue().equals("AD") || selectAsignaturasWord.getValue().equals("DI") || selectAsignaturasWord.getValue().equals("PMDM")){
            
            selectUnidad.getItems().clear();
            
            selectAsignaturasWord.getValue();
            
            File rutaWords = new File ("\\\\192.168.1.220\\nukistorage\\Estudios\\DAM\\2_DAM\\Tareas" + File.separator + selectAsignaturasWord.getValue());
            
            String [] archivos = f.recuperarListadoArchivos(rutaWords);
            
            ObservableList<String> unidades =  FXCollections.observableArrayList(archivos);
           
            selectUnidad.getItems().addAll(unidades);
            

            
        }
    } 
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtResultadoEstructura.setText("");
        actualizaUnidadActual();
        selectAsignaturasTareas.getItems().addAll(asignaturasCB);
        selectAsignaturasTema.getItems().addAll(asignaturasCB);
        selectAsignaturasPDF.getItems().addAll(asignaturasCB);
        selectAsignaturas.getItems().addAll(asignaturasCB);
        selectAsignaturasWord.getItems().addAll(asignaturasCB);
        selectFuncionalidad.getItems().addAll(funcionalidad);
        
        //Al principio debe estar desactivado
        btnMenosEstado.setDisable(true);
        btnMasEstado.setDisable(true);
        btnFusionarPDF.setDisable(true);

    }    
    
}
