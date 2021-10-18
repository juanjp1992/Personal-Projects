/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autocreaciontareasdam;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.swing.JOptionPane;
import static javax.xml.catalog.CatalogFeatures.Feature.FILES;

/**
 *
 * @author joan-
 */
public class AutoCreacionTareasDAM {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
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
            Logger.getLogger(AutoCreacionTareasDAM.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            try {
                os.close();
            } catch (IOException ex) {
                Logger.getLogger(AutoCreacionTareasDAM.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        // FIN CREAR ZIP
     
        if(destinoArchivoBase.exists() && creacionZIP.exists()){
            JOptionPane.showMessageDialog(null, "Se ha creado la Tarea UD" + numProxUnidad + " de " + asignatura + " correctamente." , "Unidad Creada", JOptionPane.INFORMATION_MESSAGE);
            
            try {
                Process p = new ProcessBuilder("Explorer.exe", "/select,"+destinoArchivoBase.getAbsolutePath()).start();
            } catch (IOException ex) {
                Logger.getLogger(AutoCreacionTareasDAM.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        else{
            JOptionPane.showMessageDialog(null, "No se pudo crear la nueva Unidad.", "Error en la Creación", JOptionPane.WARNING_MESSAGE);
        }
        
    }
    
}
