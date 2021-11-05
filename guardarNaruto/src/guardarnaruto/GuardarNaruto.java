/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guardarnaruto;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author juanj
 */
public class GuardarNaruto {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        File rutaBase = new File("C:\\Users\\juanj\\Downloads\\extracted");
    
        String [] archivosRutaBase = rutaBase.list();
        
        
    
        
        for (int i = 0; i < archivosRutaBase.length; i++) {
            
            File rutaCarpeta = new File(rutaBase + File.separator + archivosRutaBase[i]);
            
            String [] interiorCarpeta = rutaCarpeta.list();
            
            File rutaVideo = null;
            
            String eleccion = null;
            
            for (int j = 0; j < interiorCarpeta.length; j++) {
                
                if(interiorCarpeta[j].contains(".mp4")){
                    rutaVideo = new File(rutaCarpeta + File.separator + interiorCarpeta[j]);
                    
                    eleccion = interiorCarpeta[j];
                }
                
            }
            
            File rutaDestino = new File("\\\\192.168.1.220\\share\\nukiflix\\series\\Naruto" + File.separator + eleccion);
            
            try {
                
                //Realizo una copia al destino
                if(!rutaDestino.exists()){
                    System.out.println("# Iniciando copia de " + eleccion);
                    Files.copy(rutaVideo.toPath(), rutaDestino.toPath());
                    
                    if(rutaDestino.exists()){
                        System.out.println("> "+eleccion + " se ha copiado correctamente.\n");
                    }
                    else{
                        System.out.println("> "+eleccion + " no se ha podido copiar.\n");
                    }
                }
                else{
                    System.out.println("> "+eleccion + " ya existe, paso al siguiente archivo.\n");
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(GuardarNaruto.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "ERROR: No se pudo realizar la copia del archivo", "Error Realizando la Copia", JOptionPane.WARNING_MESSAGE);
            }

        }
    }
    
}
