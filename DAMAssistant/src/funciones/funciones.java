/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package funciones;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipOutputStream;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javax.swing.JOptionPane;
import org.apache.pdfbox.multipdf.PDFMergerUtility;

/**
 *
 * @author juanj
 */
public class funciones {
    
    //RUTAS
    File pathTareas = new File("\\\\192.168.1.220\\nukistorage\\Estudios\\DAM\\2_DAM\\Tareas");
    File pathClases = new File("\\\\192.168.1.220\\nukistorage\\Estudios\\DAM\\2_DAM\\Clases");
    File pathTemarios = new File("\\\\192.168.1.220\\nukistorage\\Estudios\\DAM\\2_DAM\\Temarios");
    File pathADClases = new File(pathTareas + "\\" + "AD");
    File pathSGEClases = new File(pathTareas + "\\" + "SGE");
    File pathPMDMClases = new File(pathTareas + "\\" + "PMDM");
    File pathPSPClases = new File(pathTareas + "\\" + "PSP");
    File pathDIClases = new File(pathTareas + "\\" + "DI");
    File pathPSPTareas = new File(pathClases + File.separator + "PSP");
    File pathSGETareas= new File(pathClases + File.separator + "SGE");
    File pathDITareas = new File(pathClases + File.separator + "DI");
    File pathADTareas = new File(pathClases + File.separator + "AD");
    File pathPMDMTareas = new File(pathClases + File.separator + "PMDM");
    
    //Atributos para almacenar archivos
    String[] fAD = null;
    String[] fSGE = null;
    String[] fPMDM = null;
    String[] fPSP = null;
    String[] fDI = null;
    
    
    //Clase que se encarga de revisar el número de grabaciones que existen por organizar
    public int numClasesParaOrganizar(){
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
    
    public String [] unidadActualAsignatura(){
        // Listo la cantidad de archivos que hay para saber la unidad.
        fPSP = pathPSPClases.list();
        fDI = pathDIClases.list();
        fAD = pathADClases.list();
        fSGE = pathSGEClases.list();
        fPMDM = pathPMDMClases.list();
        
        //Formateo el texto dentro de un array de String para mostrarlo
        String [] unidades = {"UD"+fPSP.length, 
                              "UD"+fDI.length,
                              "UD"+fAD.length,
                              "UD"+fSGE.length,
                              "UD"+fPMDM.length};
        
        return unidades;
    }

    public String [] grabacionesAsignaturas(String asignatura){
        //Recojo la ruta seleccionada
        File rutaAsignatura = new File("\\\\192.168.1.220\\nukistorage\\Estudios\\DAM\\2_DAM\\Clases\\"+ asignatura);
        
        //recogo todos los archivos
        String [] archivos = rutaAsignatura.list();
        
        //creo un segundo array recogiendo solo grabaciones, por lo que le resto uno.
        String [] grabaciones = new String[(archivos.length)-1];
        int numGrabaciones = 0;
        //Relleno ese nuevo array.
        for (int i = 0; i < archivos.length; i++) {
            if(!archivos[i].equals("minutado")){
               grabaciones[numGrabaciones] = archivos[i]; 
               numGrabaciones++;
            }
        }
        //Devuelvo todos los archivos.
        return grabaciones;
    }
    
    public void abrirArchivo(File path){
        
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().open(path);
            } catch (IOException e) {
                System.out.println("ERROR: No se puede abrir el archivo elegido.");
            }
        }
    }
    
    public void crearArchivo(File path){
        if(!path.exists()){
            try {
                path.createNewFile();
                
               Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Creación de Archivos");
                alert.setHeaderText("Se ha creado correctamente");
                alert.showAndWait();
               
              
                
                
                
            } catch (IOException ex) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error de Creación");
                alert.setHeaderText("Ha Ocurrido un Error");
                                
                alert.showAndWait();
            }
        
        }
    }

    public String recuperarMinutado(File path){  
        String textoRecuperado = ""; 
        
        if(path.exists()){
            FileReader fr = null;
            BufferedReader br = null;
            try {
                fr = new FileReader(path);
                br = new BufferedReader(fr);
            
               
                String linea = br.readLine();

                while(linea!=null){
                   textoRecuperado = textoRecuperado + linea+"\n";
                   linea = br.readLine();
                }

                fr.close();
                br.close();

            } catch (Exception ex) {
                System.out.println("ERROR: Fallo en la lectura del archivo de minutado." + ex);

            }

            return textoRecuperado;
        }
        else{
            return "No hay archivo de minutado.";
        }
        
    }
    
    public String guardarMinutado(File path, String texto){  
        
        String estado = null;
        if(path.exists()){
            FileWriter fw = null;
            try {
                fw = new FileWriter(path);
                
                fw.write(texto);
                
                fw.close();
                
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
               
                estado = "Cambios guardados correctamente. " + dtf.format(LocalDateTime.now());
                
                
            } catch (IOException ex) {
                Logger.getLogger(funciones.class.getName()).log(Level.SEVERE, null, ex);
                
              estado = "Error al guardar los cambios";
            }
 
        }
       return estado;
    }
    
    public void abrirCarpeta(File path){
        try {
            Process builder = Runtime.getRuntime().exec("cmd /c start " + path.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public int [] numUnidades(){
        //Recopilo el número de la unidad.
        int [] numUnidades = {pathPSPClases.list().length, pathSGEClases.list().length, pathDIClases.list().length, pathPMDMClases.list().length, pathADClases.list().length};
        
        //Lo devuelvo en un array de enteros
        return numUnidades;
    } 

    public String crearEstructuraTarea(String asignatura){
        //Guardo array de string para comparar con la unidad
        String [] asignaturas = {"PSP", "SGE", "DI", "PMDM", "AD"};
        int numProxUnidad = -1;
        //Recorro las asignaturas para ver que numero sería.
        for (int i = 0; i < asignaturas.length; i++) {
            if(asignaturas[i].equals(asignatura)){
                numProxUnidad = this.numUnidades()[i]+1;
            }
        }
        //Consigo la ruta donde estaría la carpeta
        File pathNuevaUnidad = new File(pathTareas + File.separator + asignatura + File.separator + "UD" + numProxUnidad);
        
        
        try{
            if(!pathNuevaUnidad.exists()){
                //Si no existe, lo creo.
                pathNuevaUnidad.mkdir();
            }
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null, "No se pudo crear la nueva unidad", "Error de Creación", JOptionPane.WARNING_MESSAGE);
        }
        //ANTES COGIA EL WORD Y LO LLEVABA
        /*Creo la ruta del archivo base y la de destino
            File archivoBase = new File(path + File.separator + "base.docx");
            File destinoArchivoBase = new File(nuevaUnidad+ File.separator + asignatura + "_Jimenez_Perez_Juan_47619383E_T" + numProxUnidad + ".docx");
        try {
            
            //Realizo una copia
            Files.copy(archivoBase.toPath(), destinoArchivoBase.toPath());
            
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "No se pudo realizar la copia del archivo base.docx", "Error Realizando la Copia", JOptionPane.WARNING_MESSAGE);
        }
        
        //Creo el archivo .zip para luego almacenar más facilmente.
        
        */
        
        //Creación del ZIP, creo la ruta donde irá el ZIP.
        File creacionZIP = new File(pathNuevaUnidad+ File.separator + asignatura + "_Jimenez_Perez_Juan_47619383E_T" + numProxUnidad + ".zip");
        
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
        
        if(creacionZIP.exists()){
            //JOptionPane.showMessageDialog(null, "Se ha creado la Tarea UD" + numProxUnidad + " de " + asignatura + " correctamente." , "Unidad Creada", JOptionPane.INFORMATION_MESSAGE);
            //Abro la carpeta
            abrirCarpeta(pathNuevaUnidad);
            return "Creado UD" + numProxUnidad + " de " + asignatura + " correctamente";
        }
        else{
            //JOptionPane.showMessageDialog(null, "No se pudo crear la nueva Unidad.", "Error en la Creación", JOptionPane.WARNING_MESSAGE);
            return "No se pudo crear la estructura";
        }
    
        
    }
    
    public void guardarGrabaciones(){
        
        //Meses y Días de la Semana
        String [] semanaTexto = {"Domingo", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado"};
        String [] mesTexto = {"Ene", "Feb", "Mar", "Abr", "Mayo", "Jun", "Jul", "Ago", "Sept", "Oct", "Nov", "Dic"};

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
                archivos = pathClases.list();

                // Recojo todos los archivos de cada asignatura y los añado al array
                archivosPSP = pathPSPTareas.list();
                archivosDI = pathDITareas.list();
                archivosAD = pathADTareas.list();
                archivosSGE = pathSGETareas.list();
                archivosPMDM = pathPMDMTareas.list();


                if (archivosPSP.length != 0){
                    psp = archivosPSP.length;
                }

                if (archivosDI.length != 0){
                    di = archivosDI.length;
                }

                if (archivosAD.length != 0){
                    ad = archivosAD.length;
                }

                if (archivosSGE.length != 0){
                    sge = archivosSGE.length;
                }

                if (archivosPMDM.length != 0){
                    pmdm = archivosPMDM.length;
                }
            }
            catch(Exception ex){
                System.out.println("ERROR: Error de lectura de número de archivos dentro de cada carpeta.");
            }
            
        
            for (int i = 0; i < archivos.length; i++) {
            try{
            if(!archivos[i].equals("PSP") && !archivos[i].equals("DI") && !archivos[i].equals("AD") && !archivos[i].equals("PMDM") && !archivos[i].equals("SGE")){
                

                Path source = Paths.get(pathClases+"\\"+archivos[i]);

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
               

            } 
            catch (ParseException ex) {
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


                   Files.move(source, source.resolveSibling(pathClases + "\\" + inputAsignatura + "\\" + inputAsignatura + "_CLASE_" + numClase +".mp4"));

                } 
                catch (IOException e) {
                    System.out.println("ERROR: Error renombrando el archivo.");
                }
            
            }
            
        }
    }catch(Exception ex){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText("Auto Organización Finalizada");
        alert.setTitle("Trabajo Finalizado");
        alert.setContentText("Ya no quedan más grabaciones.");
        alert.showAndWait();
    }
    }
    
    }
    
    public String [] proximaClase(){
       
       File path = new File("C:\\Users\\juanj\\Documents\\Personal-Projects\\DAMAssistant\\archivos\\clases_online.txt");
       
       String [] division = recuperarMinutado(path).split(",");
       
       DateTimeFormatter diaActual = DateTimeFormatter.ofPattern("dd/MM/yyyy");
       
       Calendar c = Calendar.getInstance();
       SimpleDateFormat diaAct = new SimpleDateFormat("dd/MM/yyyy");
       String dia = diaActual.format(LocalDateTime.now());
        try {
            c.setTime(diaAct.parse(dia));
        } catch (ParseException ex) {
            Logger.getLogger(funciones.class.getName()).log(Level.SEVERE, null, ex);
        }
       String [] semana = new String[15];
       
       semana[0] = dia;
       
        //Va añadiendo un dia más al día actual y guarda la fecha para usarla posteriormente.
        for (int i = 1; i < semana.length; i++) {
            c.add(Calendar.DATE, 1);  
            semana[i] = diaAct.format(c.getTime());
            
        }

       ArrayList<Integer> seleccion = new ArrayList<Integer>();
       
       int cuenta = 0;
        for (int i = 0; i < division.length; i++) {
            for (int j = 0; j < semana.length; j++) {
                if(division[i].equals(semana[j])){
                    seleccion.add(i);
                }
            }
        }
        
        Iterator it = seleccion.iterator();
        
        int numArray = seleccion.size()/2;
        
        String [] proxClases = new String [numArray*4];
        
        int contador = 0;
        
        while(it.hasNext()){
            int num = Integer.parseInt( it.next().toString());
            //Asignatura
            proxClases[contador] = division[num-1].substring(division[num-1].indexOf("[")+1, division[num-1].indexOf("]"));
            contador++;
            //Clase
            proxClases[contador] = division[num-1].substring(division[num-1].indexOf("]")+1).trim();
            contador++;
            //Fecha
            proxClases[contador] = division[num];
            contador++;
            //Hora
            proxClases[contador] = division[num+2];
            contador++;
            
            
            //Salto el siguiente;
            it.next();
        }
        
        for (int i = 0; i < proxClases.length; i++) {
            System.out.println(proxClases[i]);
        }
        
               
       // System.out.println(dtf.format(LocalDateTime.of(2021, 10, 29, 12, 00)));
       
        return proxClases;
    }
    
    public void abrirWeb(String web){
        try{
            Desktop.getDesktop().browse(new URI(web));

        } 
        catch (URISyntaxException ex) {
            System.out.println(ex);

        }
        catch(IOException e){
            System.out.println(e);
        }
    }
    
    public String [] recuperarListadoArchivos(File path){
        String [] listadoArchivos = path.list();
       
        return listadoArchivos;
    }
    
    public void fusionPDF(File origen, String asignatura){
        
        File pathPDFfinal = null;
        File destinoArchivoBase = null;
        PDFMergerUtility ut = new PDFMergerUtility();
        
        //Recopilo todos los archivos pdf de la carpeta origen
        String [] pdfs = origen.list();
        
        
        //Inserto todos a PDFMergerUtility
        for (int i = 0; i < pdfs.length; i++) {
            try {
                
                ut.addSource(origen + File.separator + (i+1) + ".pdf");
            } 
            catch (FileNotFoundException ex) {
                System.out.println("ERROR: Error almacenando PDF en PDFMergerUtility.");
            }
        }
        
        //Le digo el nombre del archivo final de pdf.
        ut.setDestinationFileName(origen + File.separator + "archivo_final.pdf");
        
        //Los junto.
        try {
            ut.mergeDocuments();
        } 
        catch (IOException ex) {
            
        }
        
        //Muestro el archivo
        pathPDFfinal = new File(origen + File.separator + "archivo_final.pdf");
        
        //Intento abrirlo
        this.abrirArchivo(pathPDFfinal);
        
        int resp=JOptionPane.showConfirmDialog(null,"¿El PDF es correcto?");
        
        if (JOptionPane.OK_OPTION == resp){
            File pathAsig = new File(pathTemarios + File.separator + asignatura);
            String [] numArch = pathAsig.list();
            
            destinoArchivoBase = new File(pathAsig + File.separator + asignatura + " - UD" + (numArch.length + 1) + ".pdf" );
            
            try {
                //Realizo una copia del pdf final (origen) al nuevo destino.
                Files.copy(pathPDFfinal.toPath(), destinoArchivoBase.toPath());

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "ERROR: No se pudo realizar la copia del archivo", "Error Realizando la Copia", JOptionPane.WARNING_MESSAGE);
            }
            
            //Si el archivo existe, borro todos los archivos.
            if(destinoArchivoBase.exists()){
                pdfs = origen.list();
                File borrado = null;
                for (int i = 0; i < pdfs.length; i++) {
                    borrado = new File(origen + File.separator + pdfs[i]);
                    
                    borrado.delete();
                }
                origen.delete();
                
                JOptionPane.showMessageDialog(null, "El PDF está terminado", "PDF Creator", JOptionPane.WARNING_MESSAGE);
            }
         }
        //Si no es correcto el pdf, elimina el archivo final de la carpeta de origen.
        else{
            pathPDFfinal.delete();
        }
    }
}
