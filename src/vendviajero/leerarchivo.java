
package vendviajero;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class leerarchivo {
    
    @SuppressWarnings("empty-statement")
    public int[][] leerTxt(String direccion) throws FileNotFoundException{
        
        int[][] coordenadas = null;
        int tamaño = 0;
        File f = new File(direccion);
        String linea = "";
        
        try(Scanner entrada = new Scanner(f)){    //Guardo el archivo en la variable Scanner
            //PARTE 1 Encontrar la cantidad de ciudades a visitar
            while (!linea.contains("DIMENSION:")) { //mientras no encuentre la dimension 
                linea = entrada.nextLine();  //se lee una línea
                if (linea.contains("DIMENSION:")) { //si la linea contiene la dimension
                    String[] parte = linea.split(" "); // separo el string con el delimitador de espacio 
                    tamaño = Integer.parseInt(parte[1]);  // guardo la parte de la cantidad de ciudades                   
                }
            }
            coordenadas = new int [tamaño][2]; //inicializo las coordenadas
            //PARTE 2 Guardar las coordenadas de las ciudades
            do{
                linea = entrada.nextLine(); 
            }while(!linea.contains("NODE_COORD_SECTION")); //paro antes de iniciar las coordenadas
            
            linea = entrada.nextLine(); //avanzo al primer par de coordenadas
            do{   
                String[] parte = linea.split(" ");  //separo las coordenadas
                int fila = Integer.parseInt(parte[0])-1;   //guardo el número de la ciudad que está en la primera columna
                coordenadas[fila][0] = (int)Float.parseFloat(parte[1]); //transformo y guardo la coordenada X
                coordenadas[fila][1] = (int)Float.parseFloat(parte[2]); //transformo y guardo la coordenada Y
                linea = entrada.nextLine();  //avanzo a la siguiente coordenada
            }while(!linea.contains("EOF"));  //hasta llegar al fin del archivo
        } 
        return coordenadas;
    }
}