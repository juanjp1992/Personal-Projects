/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication19;

import java.io.File;

/**
 *
 * @author juanj
 */
public class JavaApplication19 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        File path = new File ("\\\\192.168.1.220\\nukistorage\\Estudios\\DAM\\2_DAM\\Clases");
        
        String [] prueba = path.list();
        
        for (int i = 0; i < prueba.length; i++) {
            System.out.println(prueba[i]);
        }
        
    }
    
}
