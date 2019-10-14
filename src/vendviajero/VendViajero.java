package vendviajero;

import java.io.FileNotFoundException;

public class VendViajero {

    public static void main(String[] args) throws FileNotFoundException {
        //agregar en forma de args
        int semilla = 3;
        int tamColonia = 51;
        float alfa = (float)0.1;
        float q0 = (float)0.5;
        float beta = (float)2.5;
        int iteracion = 100000;
        int cantIteracion = 0;
        float optimo = (float)7544.3659;
        String dataSet = "berlin52.txt";
        leerarchivo data = new leerarchivo();
        AntColony colonia = new AntColony(semilla,alfa,q0,beta,data.leerTxt(dataSet),tamColonia);
        int c = colonia.antVector.length; 
        int[] inicio = new int[c];
        inicio = colonia.desordenarVector(inicio);
        for (int i = 0; i < c; i++) {
            System.out.print(inicio[i]+" ");
        }
       /* while(cantIteracion != iteracion && colonia.fitness() < 7545 && colonia.fitness()>7544){
            
            cantIteracion++;
        }*/
    }
    
}
