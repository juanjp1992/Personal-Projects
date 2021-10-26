/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package damtools;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

/**
 *
 * @author juanj
 */
public class autoGuardadoVideos {

    
    public void guardaVideos(){
        
    //Meses y Días de la Semana
    String [] semanaTexto = {"Domingo", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado"};
    String [] mesTexto = {"Ene", "Feb", "Mar", "Abr", "Mayo", "Jun", "Jul", "Ago", "Sept", "Oct", "Nov", "Dic"};
    
    //Recogo ruta de destino
    String ruta = "\\\\192.168.1.220\\nukistorage\\Estudios\\DAM\\2_DAM\\Clases";
    File path = new File(ruta);
    
    //Recogo ruta de todas las carpetas de las asignaturas
    File pathPSP = new File(ruta + "\\PSP");
    File pathSGE= new File(ruta + "\\SGE");
    File pathDI = new File(ruta + "\\DI");
    File pathAD = new File(ruta + "\\AD");
    File pathPMDM = new File(ruta + "\\PMDM");
    
    //Creo String para guardar la asignatura
    String inputAsignatura = "";
    
    //Array de archivos de origen
    String [] archivos ={};
    
    //Creo array de archivos internos de cada asignatura
    String [] archivosPSP ={};
    String [] archivosDI ={};
    String [] archivosAD ={};
    String [] archivosSGE ={};
    String [] archivosPMDM ={};
    
    //Número de clase por la que va
    int psp = 1, di = 1, ad = 1, sge = 1, pmdm = 1;
    
    try{
            archivos = path.list();
            
            // Recojo todos los archivos de cada asignatura y los añado al array
            archivosPSP = pathPSP.list();
            archivosDI = pathDI.list();
            archivosAD = pathAD.list();
            archivosSGE = pathSGE.list();
            archivosPMDM = pathPMDM.list();
           
            
            if (archivosPSP.length != 0){
                psp = archivosPSP.length + 1;
            }
            
            if (archivosDI.length != 0){
                di = archivosDI.length + 1;
            }
            
            if (archivosAD.length != 0){
                ad = archivosAD.length + 1;
            }
           
            if (archivosSGE.length != 0){
                sge = archivosSGE.length + 1;
            }
            
            if (archivosPMDM.length != 0){
                pmdm = archivosPMDM.length + 1;
            }
             
            
            
        System.out.println("PSP: " + psp +
                "\nAD: " + ad +
                "\nPMDM : " + pmdm +
                "\nSGE: " + sge +
                 "\nDI: " + di);
            
        }
        catch(Exception ex){
            System.out.println("ERROR: Error de lectura de número de archivos dentro de cada carpeta.");
        }
    
         for (int i = 0; i < archivos.length; i++) {
            System.out.println(archivos[i]);
            
            Path source = Paths.get(ruta+"\\"+archivos[i]);
            
            //Creo variables para guardar los diferentes datos
            String dia, mes, año, horas, minutos, diaTexto = "", asignaturaIntuicion;
            
            //### Recopilación de datos ###
            
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
            
            //### Fin de Recopilación de datos ###
              
         
            //Guardo los datos en un tipo Calendario para tenerlo en formato fecha
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", java.util.Locale.ENGLISH);
            Date myDate;
        try {
            myDate = sdf.parse(dia+"/"+mes+"/"+año);
            sdf.applyPattern("EEE");
            diaTexto = sdf.format(myDate);
            System.out.println(diaTexto);
            
        } catch (ParseException ex) {
            System.out.println("Error");
        }
            
            
            
            //Si cae en martes, por intuición sabemos que es PMDM
            if("Tue".equals(diaTexto)){
                asignaturaIntuicion = "PMDM";
                diaTexto = "Martes";
            }
            // Si cae en miércoles por intuición es PSP o DI
            else if("Wed".equals(diaTexto)){
                //Ajusto para saber si es uno o otro, por la hora.
                if(Integer.parseInt(horas) == 19 || Integer.parseInt(horas) == 20){
                    asignaturaIntuicion = "PSP";
                }
                else{
                    asignaturaIntuicion = "DI";
                }
                diaTexto = "Miércoles";
            }
            // Si cae en jueves por intuición es SGE o AD
            else if("Thu".equals(diaTexto)){
                //Ajusto para saber si es uno o otro, por la hora.
                if(Integer.parseInt(horas) == 19 || Integer.parseInt(horas) == 20){
                    asignaturaIntuicion = "SGE";
                }
                else{
                    asignaturaIntuicion = "AD";
                }
                diaTexto = "Jueves";
            }
            else{
                //Si no es capaz de adivinarlo, pondrá el guión.
                asignaturaIntuicion = "-";
                
                if(diaTexto.equalsIgnoreCase("Mon")){
                    diaTexto = "Lunes";
                }
                else if(diaTexto.equalsIgnoreCase("Fri")){
                   diaTexto = "Viernes"; 
                }
                else if(diaTexto.equalsIgnoreCase("Sat")){
                   diaTexto = "Sábado"; 
                }
                else if(diaTexto.equalsIgnoreCase("Sun")){
                   diaTexto = "Domingo"; 
                }
                else{
                    diaTexto = "DiaSemanaDesconocido";
                }
            }
            //Abrimos un JOptionPane para recopilar el dato de la asignatura de manera manual.
            inputAsignatura = (String) JOptionPane.showInputDialog(null, "Video Grabado: \n" + diaTexto +" " + dia +  ", " + mesTexto[Integer.parseInt(mes)-1] + " " + año + " " 
                    + horas + ":" + minutos + "\nIntroduce la asignatura", asignaturaIntuicion, JOptionPane.QUESTION_MESSAGE, null,   new Object[] {"-" ,"PSP", "DI", "AD", "PMDM", "SGE" }, asignaturaIntuicion);
        
        
           
           //Si la asignatura elegida no es null entra.
           if(inputAsignatura != null){
                System.out.println("ENTRO!!");
           
                try{
                    //Creo variable para asignar el número de clase.
                    int numClase = 1;
                   //Si la clase es "-" le añadiremos una x para definirlo como desconocido 
                    if(inputAsignatura.equalsIgnoreCase("-")){
                        inputAsignatura = "X";
                    }
                    // Elegimos la asignatura y guardaremos el número de clase que toca.
                    if(inputAsignatura.equalsIgnoreCase("PSP")){
                        numClase = psp;
                        psp++;
                        
                    }
                    else if(inputAsignatura.equalsIgnoreCase("DI")){
                        numClase = di;
                        di++;
                    }
                    else if(inputAsignatura.equalsIgnoreCase("AD")){
                        numClase = ad;
                        ad++;
                    }
                    else if(inputAsignatura.equalsIgnoreCase("SGE")){
                        numClase = sge;
                        sge++;
                    }
                    else if(inputAsignatura.equalsIgnoreCase("PMDM")){
                        numClase = pmdm;
                        pmdm++;
                    }
                    //Si es desconocida, le pondremos 0
                    else{
                        numClase = 0;
                    }


                  Files.move(source, source.resolveSibling(ruta + "\\" + inputAsignatura + "\\" + inputAsignatura + "_CLASE_" + numClase +".mp4"));

                } 
                catch (IOException e) {
                    System.out.println("ERROR: Error renombrando el archivo.");
                }
                
            
                }
            
        
        }
    }
    
}
