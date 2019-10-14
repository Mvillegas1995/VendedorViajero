package vendviajero;

import java.io.FileNotFoundException;

public class VendViajero {

    public static void main(String[] args) throws FileNotFoundException {
        //agregar en forma de args
        int semilla = 1;
        int tamColonia = 52;
        float alfa = (float)0.1;
        float q0 = (float)0.5;
        float beta = (float)2.5;       
        String dataSet = "berlin52.txt";
        leerarchivo data = new leerarchivo();
        AntColony colonia = new AntColony(semilla,alfa,q0,beta,data.leerTxt(dataSet),tamColonia);
    }
    
}
