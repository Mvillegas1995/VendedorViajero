
package vendviajero;

import java.util.ArrayList;

public class Ant extends Thread{
    private int[] camino;                               // Guarda camino por dónde pasa.
    private ArrayList<Integer> memoria;        // Recuerda los caminos que faltan por visitar.
    
    public Ant(int tamMemoria){
        camino = new int[tamMemoria];
        memoria = new ArrayList<>();
               
    }
    public void setCamino(int pos, int visitar){
        camino[pos] = visitar;
    }
    public int getCamino(int pos){
        int ciudad = camino[pos];      
        return ciudad;
    }
    public void borrarMemoria(){
        memoria.removeAll(memoria);
    }
    public final void reestablecerMemoria(){
        for (int i = 0; i < camino.length; i++) {
            memoria.add(i);
        }
        memoria.remove(memoria.indexOf(camino[0]));
    }
    public float costoCamino(AntColony aC){
        float costo = aC.costo(camino);
        return costo;
    }
    public int escogerCamino(float[] valorProb, AntColony colonia){
        int pos = 0;
        float Aleatorio = colonia.random.nextFloat();
        float[] ruleta = new float[camino.length];
        float total = 0;
        for (int i = 0; i < camino.length; i++) {
            total += valorProb[i];
        }    
        float [] proporcion = new float [camino.length];                        //Obtengo las proporciones de mi ruleta
        for (int i = 0; i < proporcion.length; i++) {
            proporcion[i] = valorProb[i]/total;
        }
        ruleta[0] = proporcion[0];
        for (int j = 1; j < ruleta.length; j++) {                                    //Ruleta
                    ruleta[j] = ruleta[j-1] + proporcion[j];
        }   
        for (int i = 0; i < ruleta.length; i++) {
            if(i != 0 && Aleatorio > ruleta[i-1] && ruleta[i] > Aleatorio){
                pos = i;
            }
            else if(i==0 && Aleatorio > 0 && Aleatorio < ruleta[i]){
                pos = i;            
            }
        }
        System.out.println(pos);
        return pos;
    }
    public void Run(AntColony colonia, float[][] mDeterministica){
        reestablecerMemoria();
        float valorMayor;
        int indiceMayor = -1;
        float rand;     
        for (int i = 1; i < mDeterministica.length-1; i++) {   
            rand = colonia.random.nextFloat();
            if(rand < colonia.q0){                                                                   //Explotación
                valorMayor = Integer.MIN_VALUE;
                for (int j = 0; j < memoria.size(); j++) {              
                    if(mDeterministica[camino[i-1]][memoria.get(j)] > valorMayor){  //Determina el sgt mejor camino
                        valorMayor = mDeterministica[camino[i-1]][memoria.get(j)];  
                        indiceMayor = memoria.get(j);                                           
                    }
                }
                camino[i] = indiceMayor;                                                    //inserto el camino mayor
                memoria.remove(memoria.indexOf(indiceMayor));                   //Retiro de la memoria el camino que visite
                System.out.println(indiceMayor);
                memoria.forEach((a) -> {           
                    System.out.print(a+" ");
                });
                System.out.println("");
            }
            else{                                                                           //Exploración
                float[ ] valorProb = new float [mDeterministica.length];  //Guardar la probabilidad de ser escogido
                for (int j = 0; j < valorProb.length; j++) {                     //llenar con 0 para los valores q no estén
                    valorProb[j] = 0;
                }
                float sumatoria = 0;                                                 //sumatoria de los caminos que quedan
                for (int j = 0; j < memoria.size(); j++) {
                    sumatoria += mDeterministica[camino[i-1]][memoria.get(j)]; 
                }
                for (int j = 0; j < memoria.size(); j++) {
                    valorProb[memoria.get(j)] =   mDeterministica[camino[i-1]][memoria.get(j)]/sumatoria;       //formula2
                }
                
                int sgtCamino = escogerCamino(valorProb, colonia);
                camino[i] = sgtCamino;                                                  //inserto el camino mayor
                memoria.remove(memoria.indexOf(sgtCamino));                 //Retiro de la memoria el camino que visite
                System.out.println("Exploración");
                memoria.forEach((a) -> {           
                    System.out.print(a+" ");
                });
                System.out.println("");
            }
            //Actualización local
            colonia.feromonas[camino[i-1]][camino[i]] = ((1-colonia.alfa)*colonia.feromonas[camino[i-1]][camino[i]]) +(colonia.alfa*colonia.vif) ; //formula 4 
        }
        camino[mDeterministica.length-1] = memoria.get(0);  //para que el último camino se ponga automáticamente
        memoria.remove(0);
        colonia.feromonas[camino[camino.length-2]][camino[camino.length-1]] = ((1-colonia.alfa)*                      //De la penúltima posición a la última
                colonia.feromonas[camino[camino.length-2]][camino[camino.length-1]]) +(colonia.alfa*colonia.vif) ;
        colonia.feromonas[camino[camino.length-1]][camino[0]] = ((1-colonia.alfa)*                                          //De la última posición hasta la primera
                colonia.feromonas[camino[camino.length-1]][camino[0]]) + (colonia.alfa*colonia.vif);
        for (int i = 0; i < camino.length; i++) {
            System.out.print(camino[i]+" ");
        }

        System.out.println("");
        System.out.println("");
        System.out.println("costo: "+colonia.costo(camino));
    }
}
