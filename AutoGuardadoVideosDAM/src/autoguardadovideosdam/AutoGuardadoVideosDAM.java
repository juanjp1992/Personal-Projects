
package autoguardadovideosdam;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Calendar;
import javax.swing.JOptionPane;

/**
 *
 * @author joan-
 */
public class AutoGuardadoVideosDAM{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        String rutaVideos = JOptionPane.showInputDialog(null, "Introduce la ruta de origen.").replaceAll("\\\\", "\\\\\\\\");
        String rutaDestino = "\\\\192.168.1.124\\nukidata\\Estudios\\DAM\\2_DAM\\Clases";
        File path = new File(rutaVideos);
        File pathPSP = new File(rutaDestino + "\\PSP");
        File pathSGE= new File(rutaDestino + "\\SGE");
        File pathDI = new File(rutaDestino + "\\DI");
        File pathAD = new File(rutaDestino + "\\AD");
        File pathPMDM = new File(rutaDestino + "\\PMDM");
        
        String inputAsignatura = "";
        int psp = 1, di = 1, ad = 1, sge = 1, pmdm = 1;
        
        String [] semanaTexto = {"Viernes", "Sábado", "Domingo", "Lunes", "Martes", "Miércoles", "Jueves"};
        String [] mesTexto = {"Ene", "Feb", "Mar", "Abr", "Mayo", "Jun", "Jul", "Ago", "Sept", "Oct", "Nov", "Dic"};
        String [] archivos = {};
        
        String [] archivosPSP ={};
        String [] archivosDI ={};
        String [] archivosAD ={};
        String [] archivosSGE ={};
        String [] archivosPMDM ={};
        
        
        try{
            archivos = path.list();
            archivosPSP = pathPSP.list();
            archivosDI = pathDI.list();
            archivosAD = pathAD.list();
            archivosSGE = pathSGE.list();
            archivosPMDM = pathPMDM.list();
            
            System.out.println("psp: " + archivosPSP.length + 
                                "di: " + archivosDI.length + 
                                "ad: " + archivosAD.length +
                                "sge: " + archivosSGE.length + 
                                "pmdm: " + archivosPMDM.length);
            
            if (archivosPSP.length != 0){
                for (int i = 0; i < archivosPSP.length; i++) {
                    if(Integer.parseInt(archivosPSP[i].substring(archivosPSP[i].lastIndexOf("_")+1, archivosPSP[i].lastIndexOf("."))) > psp){
                        psp = Integer.parseInt(archivosPSP[i].substring(archivosPSP[i].lastIndexOf("_")+1, archivosPSP[i].lastIndexOf(".")));
                    }
                }
            }
            if (archivosDI.length != 0){
                for (int i = 0; i < archivosDI.length; i++) {
                    if(Integer.parseInt(archivosDI[i].substring(archivosDI[i].lastIndexOf("_")+1, archivosDI[i].lastIndexOf("."))) > di){
                        psp = Integer.parseInt(archivosDI[i].substring(archivosDI[i].lastIndexOf("_")+1, archivosDI[i].lastIndexOf(".")));
                    }
                }
            }
            if (archivosAD.length != 0){
                for (int i = 0; i < archivosAD.length; i++) {
                    if(Integer.parseInt(archivosAD[i].substring(archivosAD[i].lastIndexOf("_")+1, archivosAD[i].lastIndexOf("."))) > ad){
                        psp = Integer.parseInt(archivosAD[i].substring(archivosAD[i].lastIndexOf("_")+1, archivosAD[i].lastIndexOf(".")));
                    }
                }
            }
            if (archivosSGE.length != 0){
                for (int i = 0; i < archivosSGE.length; i++) {
                    if(Integer.parseInt(archivosSGE[i].substring(archivosSGE[i].lastIndexOf("_")+1, archivosSGE[i].lastIndexOf("."))) > sge){
                        psp = Integer.parseInt(archivosSGE[i].substring(archivosSGE[i].lastIndexOf("_")+1, archivosSGE[i].lastIndexOf(".")));
                    }
                }
            }
            if (archivosPMDM.length != 0){
                for (int i = 0; i < archivosPMDM.length; i++) {
                    if(Integer.parseInt(archivosPMDM[i].substring(archivosPMDM[i].lastIndexOf("_")+1, archivosPMDM[i].lastIndexOf("."))) > pmdm){
                        psp = Integer.parseInt(archivosPMDM[i].substring(archivos[i].lastIndexOf("_")+1, archivosPMDM[i].lastIndexOf(".")));
                    }
                }
            }
            
        }
        catch(Exception ex){
            System.out.println("No hay archivos");
        }
        
        for (int i = 0; i < archivos.length; i++) {
            
            if(archivos[i].startsWith("PSP")){
                                
                psp = Integer.parseInt(archivos[i].substring(archivos[i].lastIndexOf("_")+1, archivos[i].lastIndexOf(".")))+1;
            }
            else if(archivos[i].startsWith("DI")){
                di = Integer.parseInt(archivos[i].substring(archivos[i].lastIndexOf("_")+1, archivos[i].lastIndexOf(".")))+1;
            }
            else if(archivos[i].startsWith("AD")){
                ad = Integer.parseInt(archivos[i].substring(archivos[i].lastIndexOf("_")+1, archivos[i].lastIndexOf(".")))+1;
            }
            else if(archivos[i].startsWith("SGE")){
                sge = Integer.parseInt(archivos[i].substring(archivos[i].lastIndexOf("_")+1, archivos[i].lastIndexOf(".")))+1;
            }
            else if(archivos[i].startsWith("PMDM")){
                pmdm = Integer.parseInt(archivos[i].substring(archivos[i].lastIndexOf("_")+1, archivos[i].lastIndexOf(".")))+1;
            }
            else{
                
            }
            
        
        }
        
        for (int i = 0; i < archivos.length; i++) {
            System.out.println(archivos[i]);
            
            Path source = Paths.get(rutaVideos+"\\"+archivos[i]);
            //Si el archivo no empieza por ninguno de estos datos, entra
            if(!archivos[i].startsWith("PSP") || !archivos[i].startsWith("DI") || !archivos[i].startsWith("AD") || !archivos[i].startsWith("SGE") || !archivos[i].startsWith("PMDM")){
            //Recojo datos archivo
            String dia, mes, año, horas, minutos, diaTexto, asignaturaIntuicion;
            //Recojo el año
            año = archivos[i].substring(0, archivos[i].indexOf("-"));
            
            //Remplaza el año, y deja el texto sin ello.
            archivos[i] = archivos[i].replaceFirst(año+"-", " ").trim();
            
            
            //Recojo el mes
            mes = archivos[i].substring(0, archivos[i].indexOf("-"));
            
            //Remplaza el mes, y deja el texto sin ello.
            archivos[i] = archivos[i].replaceFirst(mes+"-", " ").trim();
            
            
            //Recojo el dia
            dia = archivos[i].substring(0, 2);
            
            //Remplaza el dia, y deja el texto sin ello.
            archivos[i] = archivos[i].replaceFirst(dia, " ").trim();
            
            
            //Recojo la hora
            horas = archivos[i].substring(0, archivos[i].indexOf("-"));
            
            //Remplaza la hora, y deja el texto sin ello.
            archivos[i] = archivos[i].replaceFirst(horas + "-", " ").trim();
            
            
            //Recojo los minutos
            minutos = archivos[i].substring(0, archivos[i].indexOf("-"));
            
            
              
         
            
            Calendar cal = Calendar.getInstance();
            
            cal.set(Integer.valueOf(año), Integer.parseInt(mes), Integer.parseInt(dia), Integer.parseInt(horas), Integer.parseInt(minutos));
            
            diaTexto = semanaTexto[cal.get(Calendar.DAY_OF_WEEK)-1];
            
            
            
            if("Martes".equals(diaTexto)){
                asignaturaIntuicion = "PMDM";
            }
            else if("Miércoles".equals(diaTexto)){
                if(Integer.parseInt(horas) == 19 || Integer.parseInt(horas) == 20){
                    asignaturaIntuicion = "PSP";
                }
                else{
                    asignaturaIntuicion = "DI";
                }
            }
            else if("Jueves".equals(diaTexto)){
                if(Integer.parseInt(horas) == 19 || Integer.parseInt(horas) == 20){
                    asignaturaIntuicion = "SGE";
                }
                else{
                    asignaturaIntuicion = "AD";
                }
            }
            else{
                asignaturaIntuicion = "-";
            }
            
            inputAsignatura = (String) JOptionPane.showInputDialog(null, "Video Grabado: \n" + diaTexto +" " + dia +  ", " + mesTexto[Integer.parseInt(mes)+1] + " " + año + " " 
                    + horas + ":" + minutos + "\nIntroduce la asignatura", asignaturaIntuicion, JOptionPane.QUESTION_MESSAGE, null,   new Object[] {"-" ,"PSP", "DI", "AD", "PMDM", "SGE" }, asignaturaIntuicion);
        
        
           
           
           if(inputAsignatura != null){
                System.out.println("ENTRO!!");
           
                try{
                    int numClase = 1;

                   if(inputAsignatura.equalsIgnoreCase("-")){
                        inputAsignatura = "X";
                    }

                    if(inputAsignatura.equalsIgnoreCase("PSP")){
                        psp++;
                        numClase = psp;
                        
                    }
                    else if(inputAsignatura.equalsIgnoreCase("DI")){
                        di++;
                        numClase = di;
                        
                    }
                    else if(inputAsignatura.equalsIgnoreCase("AD")){
                        ad++;
                        numClase = ad;
                        
                    }
                    else if(inputAsignatura.equalsIgnoreCase("SGE")){
                        sge++;
                        numClase = sge;
                    }
                    else if(inputAsignatura.equalsIgnoreCase("PMDM")){
                        pmdm++;
                        numClase = pmdm;
                        
                    }
                    else{
                        numClase = 0;
                    }


                  Files.move(source, source.resolveSibling(rutaDestino + "\\" + inputAsignatura + "\\" + inputAsignatura + "_CLASE_" + numClase +".mp4"));

                } 
                catch (IOException e) {
                    System.out.println("Error renombrando el archivo.");
                }
                
            
                }
            }
        
        }
    
    }
    
}
