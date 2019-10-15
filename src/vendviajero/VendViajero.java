package vendviajero;

import java.io.FileNotFoundException;

public class VendViajero {

    public static void main(String[] args) throws FileNotFoundException {
        //agregar en forma de args
        //formula 1 matrizFeromona * matrizN^beta
        int semilla = 1;
        int tamColonia = 52;
        float alfa = (float)0.1;
        float q0 = (float)0.9;
        float beta = (float)2.5;
        int iteracion = 100000;
        int cantIteracion = 0;
        float optimo = (float)7544.3659;
        String dataSet = "berlin52.txt";
        leerarchivo data = new leerarchivo();
        AntColony colonia = new AntColony(semilla,alfa,q0,beta,data.leerTxt(dataSet),tamColonia); 
        while(cantIteracion != iteracion && colonia.fitness() < 7545 && colonia.fitness()>7544){
            int[] inicio = new int[tamColonia];
            inicio = colonia.desordenarVector(inicio);
            colonia.setInicio(inicio);
            colonia.antStart(colonia);
            cantIteracion++;
        }
    }
    
}
