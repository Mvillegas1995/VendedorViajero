
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
    public float costoCamino(){
        float costo = AntColony.costo(camino);
        return costo;
    }
    public int escogerCamino(float[] valorProb){
        int pos = -1;
        float Aleatorio = AntColony.random.nextFloat();
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
        for (int j = 1; j < ruleta.length; j++) {    //Ruleta   
            ruleta[j] = ruleta[j-1] + proporcion[j];
        }
        if( Aleatorio == 0){
            int i = 0;
            while(ruleta[i]==0){
                i++;
            }
            pos = i;
        }
        else{
            do{
                for (int i = 0; i < ruleta.length; i++) {
                    if(i != 0 && Aleatorio > ruleta[i-1] && ruleta[i] >= Aleatorio){
                        pos = i;
                    }
                    else {
                        if(i==0 && Aleatorio < ruleta[i]){
                            pos = i;
                        }
                    }
                }
                if(pos == -1){
                    Aleatorio = AntColony.random.nextFloat();                       //Para ahorrarme errores si no encuentra la prob busca otro número aleatorio
                }
            }while(pos == -1);
        }
        return pos;
    }
    public void actualizacionMejorSolucion(){
        while(AntColony.ocupado){
            //   System.out.println("esperando");
        }
        AntColony.ocupado = true;                                                                      //Semáforo en rojo
        float auxCosto = AntColony.costo(camino);
        if(auxCosto<AntColony.fitnessMejorSolucion){                                           //Si el fitness obtenido es mejor que el actual mejor fitness
            System.arraycopy(camino, 0, AntColony.mejorSolucion, 0, camino.length);   //Se pone el camino obtenido como el mejor
            AntColony.fitnessMejorSolucion = auxCosto;                                         //Se actualiza el nuevo fitness
        }
        AntColony.ocupado = false;                                                                    //Semáforo en verde
    }
    public void Run(float[][] mDeterministica){
        reestablecerMemoria();
        float valorMayor;
        int indiceMayor = -10;
        float rand;     
        for (int i = 1; i < mDeterministica.length; i++) {   
            rand = AntColony.random.nextFloat();
            if(rand < AntColony.q0){                                                                   //Explotación
                valorMayor = Integer.MIN_VALUE;
                for (int j = 0; j < memoria.size(); j++) {              
                    if(mDeterministica[camino[i-1]][memoria.get(j)] > valorMayor){  //Determina el sgt mejor camino
                        valorMayor = mDeterministica[camino[i-1]][memoria.get(j)];  
                        indiceMayor = memoria.get(j);                                           
                    }
                }
                camino[i] = indiceMayor;                                                    //inserto el camino mayor
                memoria.remove(memoria.indexOf(indiceMayor));                   //Retiro de la memoria el camino que visite
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
                    valorProb[memoria.get(j)] =   mDeterministica[camino[i-1]][memoria.get(j)]/sumatoria;       //formula 2
                }
                int sgtCamino = escogerCamino(valorProb);
                camino[i] = sgtCamino;                                                  //inserto el camino mayor
                memoria.remove(memoria.indexOf(sgtCamino));                 //Retiro de la memoria el camino que visite
            }
            //Actualización local
            AntColony.feromonas[camino[i-1]][camino[i]] = ((1-AntColony.alfa)*AntColony.feromonas[camino[i-1]][camino[i]]) +(AntColony.alfa*AntColony.vif) ; //formula 4 
        }
        AntColony.feromonas[camino[camino.length-1]][camino[0]] = ((1-AntColony.alfa)*                                          //De la última posición hasta la primera
                AntColony.feromonas[camino[camino.length-1]][camino[0]]) + (AntColony.alfa*AntColony.vif);
        //Actualización de la mejor solucion
        actualizacionMejorSolucion();
    }
}
