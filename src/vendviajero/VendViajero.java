package vendviajero;

import java.io.FileNotFoundException;

public class VendViajero {

    public static void main(String[] args) throws FileNotFoundException {
        //agregar en forma de args
        
        int semilla = 1;
        int tamColonia = 15;
        float alfa = (float)0.1;  //Factor de evaporación de feromona
        float q0 = (float)0.8;    //Probabilidad de avanzar por medio de explotación o exploración
        float beta = (float)2.5; //Peso de valor de heurística
        float gama = 3;         //Refuerzo positivo de feromona
        int iteracion = 1;
        int cantIteracion = 0;
        //float optimo = (float)7544.3659;
        String dataSet = "berlin52.txt";
        leerarchivo data = new leerarchivo();
        AntColony colonia = new AntColony(semilla, alfa, q0, beta, data.leerTxt(dataSet), tamColonia, gama); 
        while(cantIteracion != iteracion){
            if(colonia.fitness() < 7545 && colonia.fitness()>7542){
                break;
            }
            int[] inicio = new int[tamColonia];
            inicio = colonia.desordenarVector(inicio);
            colonia.setInicio(inicio);
            colonia.antStart(colonia);
            cantIteracion++;
        }
    }
    
}
