/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package damtools;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipOutputStream;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javax.swing.JOptionPane;
import org.apache.pdfbox.multipdf.PDFMergerUtility;

/**
 *
 * @author juanj
 */
public class InterfazController implements Initializable {
    
    File pathTareas = new File("\\\\192.168.1.220\\nukistorage\\Estudios\\DAM\\2_DAM\\Tareas");
    File pathClases = new File("\\\\192.168.1.220\\nukistorage\\Estudios\\DAM\\2_DAM\\Clases");
    File pathAD = new File(pathTareas + "\\" + "AD");
    File pathSGE = new File(pathTareas + "\\" + "SGE");
    File pathPMDM = new File(pathTareas + "\\" + "PMDM");
    File pathPSP = new File(pathTareas + "\\" + "PSP");
    File pathDI = new File(pathTareas + "\\" + "DI");

    String[] fAD = pathAD.list();
    String[] fSGE = pathSGE.list();
    String[] fPMDM = pathPMDM.list();
    String[] fPSP = pathPSP.list();
    String[] fDI = pathDI.list();
    
    @FXML
    private Button psp = new Button();
    
    @FXML
    private Button ad = new Button();
        
    @FXML
    private Button di = new Button();
    @FXML
    private Button pmdm = new Button();
    @FXML
    private Button sge = new Button();
    
    @FXML
    private Label txtNumGrabaciones = new Label();
    
    
    @FXML
    private void btnAutoGuardadoDAM(ActionEvent event) {
        try {
            Process p2 = new ProcessBuilder("java", "-jar", "AutoGuardadoVideosDAM.jar").inheritIO().start();
        } catch (IOException ex) {
            System.out.println("ERROR: Imposible crear nuevo proceso de AutoGuardado de Videos --> " + ex);
        }
        //Volvemos a contar las grabaciones.
        txtNumGrabaciones.setText(String.valueOf(recuentoGrabaciones()));
        
    }
    @FXML
    private void btnCreacionTareas(ActionEvent event) {
        //Recogo ruta de destino
        String ruta = "\\\\192.168.1.220\\nukistorage\\Estudios\\DAM\\2_DAM\\Tareas";
        File path = new File(ruta);
              
        //Abrimos un JOptionPane para recopilar el dato de la asignatura de manera manual.
     
        String asignatura = (String) JOptionPane.showInputDialog(null, "Introduce la Asignatura", "Creación de Tareas", JOptionPane.QUESTION_MESSAGE, null,   new Object[] {"-" ,"PSP", "DI", "AD", "PMDM", "SGE" }, "-");
         
        File pathAsignatura = new File(ruta + File.separator + asignatura);
        
        //Recupero unidades actuales.
        String [] unidades = pathAsignatura.list();
        //Numero de la próxima unidad
        int numProxUnidad = unidades.length + 1;
        
        //Creo ruta de la que sería la nueva unidad
        File nuevaUnidad = new File(pathAsignatura + File.separator + "UD" + String.valueOf(numProxUnidad));
        try{
            if(!nuevaUnidad.exists()){
                //Si no existe, lo creo.
                nuevaUnidad.mkdir();
                
            }
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null, "No se pudo crear la nueva unidad", "Error de Creación", JOptionPane.WARNING_MESSAGE);
        }
        //Creo la ruta del archivo base y la de destino
            File archivoBase = new File(path + File.separator + "base.docx");
            File destinoArchivoBase = new File(nuevaUnidad+ File.separator + asignatura + "_Jimenez_Perez_Juan_47619383E_T" + numProxUnidad + ".docx");
        try {
            
            //Realizo una copia
            Files.copy(archivoBase.toPath(), destinoArchivoBase.toPath());
            
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "No se pudo realizar la copia del archivo base.docx", "Error Realizando la Copia", JOptionPane.WARNING_MESSAGE);
        }
        
        //Creo el archivo .zip para luego almacenar más facilmente.
        File creacionZIP = new File(nuevaUnidad+ File.separator + asignatura + "_Jimenez_Perez_Juan_47619383E_T" + numProxUnidad + ".zip");
        
        
        //CREAR ZIP
        ZipOutputStream os = null;
        try {   
            os = new ZipOutputStream(new FileOutputStream(creacionZIP.getAbsolutePath()));
            
        } catch (FileNotFoundException ex) {
            System.out.println("Archivo No Encontrado");
        }
        finally{
            try {
                os.close();
            } catch (IOException ex) {
                System.out.println("ZipOutputStream no cerrado");
            }
        }
        // FIN CREAR ZIP
     
        if(destinoArchivoBase.exists() && creacionZIP.exists()){
            JOptionPane.showMessageDialog(null, "Se ha creado la Tarea UD" + numProxUnidad + " de " + asignatura + " correctamente." , "Unidad Creada", JOptionPane.INFORMATION_MESSAGE);
            
            try {
                Process p = new ProcessBuilder("Explorer.exe", "/select,"+destinoArchivoBase.getAbsolutePath()).start();
            } catch (IOException ex) {
                System.out.println("ERROR: ¡Entrada y salida no cerrada!");
            }
            
        }
        else{
            JOptionPane.showMessageDialog(null, "No se pudo crear la nueva Unidad.", "Error en la Creación", JOptionPane.WARNING_MESSAGE);
        }
       recuentoTareaActual(); 
        
    }
    @FXML
    private void btnVerGrabaciones(ActionEvent event) {
        String asignatura = (String) JOptionPane.showInputDialog(null, "¿Que grabaciones quieres ver?", "Selecciona Asignatura", JOptionPane.QUESTION_MESSAGE, null,   new Object[] {"-" ,"PSP", "DI", "AD", "PMDM", "SGE" }, "-");
        
        File clases = new File("\\\\192.168.1.220\\nukistorage\\Estudios\\DAM\\2_DAM\\Clases\\"+ asignatura);
        
        String [] videos = clases.list();
        
        String eleccionClase = (String) JOptionPane.showInputDialog(null, "Elige una clase que ver:", "Selecciona Grabación", JOptionPane.QUESTION_MESSAGE, null,   videos, videos[0]);
        
        File eleccionVideo = null;
        if (Desktop.isDesktopSupported()) {
            try {
                eleccionVideo = new File (clases + File.separator + eleccionClase);
                Desktop.getDesktop().open(eleccionVideo);
            } catch (IOException e) {
                // System probably doesn't have a default PDF program
            }
        }

    }
    
    @FXML
    private void btnAbrirTrabajo(ActionEvent event) {
        String asignatura = (String) JOptionPane.showInputDialog(null, "¿Que Trabajo Quieres Abrir?", "Selecciona Asignatura", JOptionPane.QUESTION_MESSAGE, null,   new Object[] {"-" ,"PSP", "DI", "AD", "PMDM", "SGE" }, "-");
        
        File clases = new File("\\\\192.168.1.220\\nukistorage\\Estudios\\DAM\\2_DAM\\Tareas\\"+ asignatura);
        
        String [] tarea = clases.list();
        
        String unidad = (String) JOptionPane.showInputDialog(null, "¿Que Unidad Quieres Abrir?", "Selecciona Unidad:", JOptionPane.QUESTION_MESSAGE, null,   tarea, tarea[tarea.length-1]);
        
        File unidadPath = new File(clases + File.separator + unidad);
        
        String [] word = unidadPath.list();
        
        String nombreArchivo = null;
        //busco word
        for (int i = 0; i < word.length; i++) {
            if(word[i].contains(".docx") || word[i].contains(".odt") || word[i].contains(".doc") || word[i].contains(".docm")){
                nombreArchivo = word[i];
            }
        }
        
        File archivoWord = null;
        if (Desktop.isDesktopSupported()) {
            try {
                archivoWord = new File (unidadPath + File.separator + nombreArchivo);
                Desktop.getDesktop().open(archivoWord);
            } catch (IOException e) {
                // System probably doesn't have a default PDF program
            }
        }

    }
    
    @FXML
    private void btnJuntarPDF(ActionEvent event) {
        String rutaDinamica = JOptionPane.showInputDialog(null, "Introduce la ruta", "PDF Creator", JOptionPane.INFORMATION_MESSAGE).replace("\\\\", "\\\\\\\\");

        String asignatura = (String) JOptionPane.showInputDialog(null, "¿Este PDF de que asignatura es?", "Selecciona Asignatura", JOptionPane.QUESTION_MESSAGE, null,   new Object[] {"-" ,"PSP", "DI", "AD", "PMDM", "SGE" }, "-");
       
        File rutaPDF = new File(rutaDinamica);
        File pathPDFfinal = null;
        PDFMergerUtility ut = new PDFMergerUtility();
        
        
        String [] pdfs = rutaPDF.list();
        
        
        
        for (int i = 0; i < pdfs.length; i++) {
            try {
                
                ut.addSource(rutaPDF + File.separator + (i+1) + ".pdf");
            } catch (FileNotFoundException ex) {
                Logger.getLogger(InterfazController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        ut.setDestinationFileName(rutaPDF + File.separator + "archivo_final.pdf");
        try {
            ut.mergeDocuments();
        } catch (IOException ex) {
            Logger.getLogger(InterfazController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (Desktop.isDesktopSupported()) {
            try {
                pathPDFfinal = new File(rutaPDF + File.separator + "archivo_final.pdf");
                Desktop.getDesktop().open(pathPDFfinal);
            } catch (IOException e) {
                // System probably doesn't have a default PDF program
            }
        }
        
       
        
        int resp=JOptionPane.showConfirmDialog(null,"¿El PDF es correcto?");
        if (JOptionPane.OK_OPTION == resp){
            File pathAsig = new File("\\\\192.168.1.220\\nukistorage\\Estudios\\DAM\\2_DAM\\Temarios\\" + asignatura);
            String [] numArch = pathAsig.list();
            
            File destinoArchivoBase = new File(pathAsig + File.separator + asignatura + " - UD" + (numArch.length + 1) + ".pdf" );
            try {

                //Realizo una copia
                Files.copy(pathPDFfinal.toPath(), destinoArchivoBase.toPath());

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "No se pudo realizar la copia del archivo", "Error Realizando la Copia", JOptionPane.WARNING_MESSAGE);
            }
            
            if(destinoArchivoBase.exists()){
                pdfs = rutaPDF.list();
                File borrado = null;
                for (int i = 0; i < pdfs.length; i++) {
                    borrado = new File(rutaPDF + File.separator + pdfs[i]);
                    
                    borrado.delete();
                }
                rutaPDF.delete();
                 
            }
            File comprobacion = new File (rutaPDF + File.separator + pdfs[0]);
            if(!comprobacion.exists()){
                
                JOptionPane.showMessageDialog(null, "El PDF está terminado", "PDF Creator", JOptionPane.WARNING_MESSAGE);
                
                
            }
         }
        else{
            pathPDFfinal.delete();
        }
              

        
        
    }
    
    @FXML
    private void btnAbrirAsignatura(ActionEvent event) {
        
        String asignatura = (String) JOptionPane.showInputDialog(null, "¿Que asignatura quieres abrir?", "Selecciona Asignatura", JOptionPane.QUESTION_MESSAGE, null,   new Object[] {"-" ,"PSP", "DI", "AD", "PMDM", "SGE", "MTA" }, "-");

        String web = "";
        
        if(asignatura.equals("PSP")){
            web = "https://fp.foc.es/course/view.php?id=54";
        }
        if(asignatura.equals("AD")){
           web = "https://fp.foc.es/course/view.php?id=42"; 
        }
        if(asignatura.equals("DI")){
            web = "https://fp.foc.es/course/view.php?id=43";
        }
        if(asignatura.equals("PMDM")){
            web = "https://fp.foc.es/course/view.php?id=44";
        }
        if(asignatura.equals("SGE")){
            web = "https://fp.foc.es/course/view.php?id=45";
        }
        if(asignatura.equals("MTA")){
            web = "https://fp.foc.es/course/view.php?id=16";
        }
        
        
        if(!asignatura.equals("-")){
            try {

                Desktop.getDesktop().browse(new URI(web));

            } catch (URISyntaxException ex) {

                System.out.println(ex);

            }catch(IOException e){

                System.out.println(e);

            }
        }
        
    }
    
    @FXML
    private void btnAbrirClaseOnline(ActionEvent event) {
        String asignatura = (String) JOptionPane.showInputDialog(null, "¿Que asignatura quieres abrir?", "Selecciona Asignatura", JOptionPane.QUESTION_MESSAGE, null,   new Object[] {"-" ,"PSP", "DI", "AD", "PMDM", "SGE", "MTA" }, "-");

        String web = "";
        
        if(asignatura.equals("PSP")){
            web = "https://inst-foc.adobeconnect.com/r0jbfhcvmsdk/?launcher=false&proto=true";
        }
        if(asignatura.equals("AD")){
           web = "https://inst-foc.adobeconnect.com/rwuhz9bgiosi/?launcher=false&proto=true"; 
        }
        if(asignatura.equals("DI")){
            web = "https://inst-foc.adobeconnect.com/rf5mbw49i18f/?launcher=false";
        }
        if(asignatura.equals("PMDM")){
            web = "https://inst-foc.adobeconnect.com/r10dt0o18fuu/?launcher=false&proto=true";
        }
        if(asignatura.equals("SGE")){
            web = "https://inst-foc.adobeconnect.com/rkr1ea7b992q/?launcher=false&proto=true";
        }
        
        if(!asignatura.equals("-")){
            try {

                Desktop.getDesktop().browse(new URI(web));

            } catch (URISyntaxException ex) {

                System.out.println(ex);

            }catch(IOException e){

                System.out.println(e);

            }
        }
    }
   
    @FXML
    private void btnContadorPaginas(ActionEvent event) {
        try {
            Process p2 = new ProcessBuilder("java", "-jar", "contadorPaginas_Portatil.jar").inheritIO().start();
        } catch (IOException ex) {
            System.out.println("ERROR: Imposible crear nuevo proceso de AutoGuardado de Videos --> " + ex);
        }
        
    }
    
    @FXML
    private void btnPSP(ActionEvent event) {
        try {
            Process builder = Runtime.getRuntime().exec("cmd /c start " + pathPSP.getAbsolutePath() + File.separator + "UD"+(fPSP.length));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    @FXML
    private void btnSGE(ActionEvent event) {
        try {
            Process builder = Runtime.getRuntime().exec("cmd /c start " + pathSGE.getAbsolutePath() + File.separator + "UD"+(fSGE.length));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    @FXML
    private void btnDI(ActionEvent event) {
        try {
            Process builder = Runtime.getRuntime().exec("cmd /c start " + pathDI.getAbsolutePath() + File.separator + "UD"+(fDI.length));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    @FXML
    private void btnAD(ActionEvent event) {
        try {
            Process builder = Runtime.getRuntime().exec("cmd /c start " + pathAD.getAbsolutePath() + File.separator + "UD"+(fAD.length));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    @FXML
    private void btnPMDM(ActionEvent event) {
        try {
            Process builder = Runtime.getRuntime().exec("cmd /c start " + pathPMDM.getAbsolutePath() + File.separator + "UD"+(fPMDM.length));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
    private int recuentoGrabaciones(){
        
        String [] archivos = pathClases.list();
        int contadorGrabaciones = 0;
        for (int i = 0; i < archivos.length; i++) {
            System.out.println(archivos[i]);
       
            //Si no es ninguno de estos, entonces añade un nuevo archivo
            if(!archivos[i].contains("DI") && !archivos[i].contains("AD") && !archivos[i].contains("PMDM") && !archivos[i].contains("PSP") && !archivos[i].contains("SGE") && !archivos[i].contains("AutoGuardadoVideosDAM.jar")){
               
               contadorGrabaciones++;
            }
            
        }   
        
        return contadorGrabaciones;
            
        }
    
    private void cuentaTareas(){
        fAD = pathAD.list();
        fSGE = pathSGE.list();
        fPMDM = pathPMDM.list();
        fPSP = pathPSP.list();
        fDI = pathDI.list();
    }
    
    private void recuentoTareaActual(){
        cuentaTareas();
        
        psp.setText("PSP - UD" + (fPSP.length));
        ad.setText("AD - UD" + (fAD.length));
        sge.setText("SGE - UD" + (fSGE.length));
        pmdm.setText("PMDM - UD" + (fPMDM.length));
        di.setText("DI - UD" + (fDI.length));
    }
        
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtNumGrabaciones.setText(String.valueOf(recuentoGrabaciones()));
        recuentoTareaActual();
    }    
  }  

