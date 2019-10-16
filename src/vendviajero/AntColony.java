
package vendviajero;

import java.util.ArrayList;
import java.util.Random;

public class AntColony {
    public final Random random;    
    public final float alfa; //disolución feromona
    public final float q0;  //probabilidad de explotación o exploración
    public final float beta; //potenciación de matriz N
    public final float[][] distancia;
    public final float[][] N; //matriz heurística 1/distancia
    public Ant[] antVector;
    public int[] mejorSolucion;
    public float fitnessMejorSolucion;
    public float[][] feromonas;
    public boolean libre;
    
    public AntColony(int semilla, float alfa, float q0, float beta,int[][] coordenadas, int tamColonia){
        random = new Random(semilla);
        this.alfa = alfa;
        this.q0 = q0;
        this.beta = beta;
        distancia = distancia(coordenadas);
        N = new float[distancia.length][distancia.length];
        for (int i = 0; i < N.length; i++) {
            for (int j = 0; j < N.length; j++) {
                if(i == j)
                    N[i][j] = 0;
                else{
                    N[i][j] = 1/distancia[i][j];
                }
            }
        }
        antVector = new Ant[tamColonia];
        for (int i = 0; i < tamColonia; i++) {
            antVector[i] = new Ant(N.length);
        }
        mejorSolucion = new int[N.length];
        mejorSolucion = desordenarVector(mejorSolucion); //desordeno mejor solucion
        fitnessMejorSolucion = costo(mejorSolucion);    //obtengo el fitness de esa solucion
        feromonas = new float[N.length][N.length];
        for (int i = 0; i < N.length; i++) {            //inicializo matriz de feromonas
            for (int j = 0; j < N.length; j++) {
                if(i == j){
                    feromonas[i][j] = 0;
                }else{
                feromonas[i][j] = 1/fitnessMejorSolucion;
                }
            }
        }
        libre = true;
    }
    public final int[] desordenarVector(int[] desordenar){
        int rand,temp;
        if(desordenar.length < N.length){
            ArrayList<Integer> memTemp = new ArrayList<>();
            for (int i = 0; i < N.length; i++) {
                memTemp.add(i);
            }
            for (int i = 0; i < desordenar.length; i++) {
                rand = random.nextInt(memTemp.size());
                desordenar[i] = memTemp.get(rand);
                memTemp.remove(rand);
            }
        }
        else if(desordenar.length == N.length){
            for (int i = 0; i < desordenar.length; i++) {
                desordenar[i] = i;
            }
            for (int i = 0; i < desordenar.length; i++) {
            temp = desordenar[i];
            rand = random.nextInt(desordenar.length);
            desordenar[i] = desordenar[rand];
            desordenar[rand] = temp;
            }
        }
        return desordenar;
    }
    private float[][] distancia(int[][] coordenadas){
        int cantPuntos = coordenadas.length;
        //MATRIZ DE COSTOS 
        float[][]distanciaZ = new float[cantPuntos][cantPuntos];
        int diferenciaX, diferenciaY;
        for (int i = 0; i < cantPuntos; i++) {
            for (int j = 0; j < cantPuntos; j++) {
                if (i==j) {
                    distanciaZ[i][j] = 0;
                }else{
                    diferenciaX = coordenadas[i][0] - coordenadas[j][0];
                    diferenciaY = coordenadas[i][1] - coordenadas[j][1];
                    distanciaZ[i][j] =  (float) Math.sqrt(Math.pow(diferenciaX, 2) + Math.pow(diferenciaY, 2));
                }
            }
        }
        return distanciaZ;
    }
    public final float costo(int[] solucion){
        float costo = 0;
        for (int i = 0; i < (distancia.length-1); i++) {
            costo += distancia[solucion[i]][solucion[i+1]];
        }    
        costo += distancia[distancia.length-1][0];
        return costo;
    }
    public float fitness(){
        return fitnessMejorSolucion;
    }
    public void setInicio(int[] inicio){
        for (int i = 0; i < antVector.length; i++) {
            antVector[i].setCamino(0, inicio[i]);
        }
    }
    public void antStart(AntColony colonia){
        //formula 1 matrizFeromona * matrizN^beta
        float[][] mDeterministica = new float[N.length][N.length];
        for (int i = 0; i < mDeterministica.length; i++) {
            for (int j = 0; j < mDeterministica.length; j++) {
                if(i == j){
                    mDeterministica[i][j] = 0;
                }else{
                    mDeterministica[i][j] = feromonas[i][j]*(float)Math.pow(N[i][j],beta);
                }                
            }
        }
        for (Ant antVector1 : antVector) {
            antVector1.Run(colonia, mDeterministica);
        }
        for (Ant antVector1 : antVector) {
            try {
                antVector1.join();
            }catch(InterruptedException e){
            }
        }
    }
}
