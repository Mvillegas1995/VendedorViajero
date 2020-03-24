
package vendviajero;

import java.util.ArrayList;
import java.util.Random;

public class AntColony {
    public static Random random;    
    public static float alfa; //disolución feromona
    public static float q0;  //probabilidad de explotación o exploración
    public static float beta; //potenciación de matriz N
    public static float gama;//refuerzo positivo de feromona
    public static float vif; //valor inicial de feromona 
    public static float[][] distancia; //matriz de distancia
    public static float[][] N; //matriz heurística 1/distancia
    public Ant[] antVector;
    public static int[] mejorSolucion;
    public static float fitnessMejorSolucion;
    public static float[][] feromonas;
    public static boolean ocupado;
    public static float[][] mDeterministica;
    
    public AntColony(int semilla, float alfa, float q0, float beta,int[][] coordenadas, int tamColonia){
        random = new Random(semilla);
        AntColony.alfa = alfa;
        AntColony.q0 = q0;
        AntColony.beta = beta;
        ocupado = false;
        distancia = distancia(coordenadas);
        N = new float[distancia.length][distancia.length];
        mDeterministica = new float[N.length][N.length];
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
        fitnessMejorSolucion = costo(mejorSolucion);         //obtengo el fitness de esa solucion
        System.out.print("El camino inicial es: ");
        for (int i = 0; i < mejorSolucion.length; i++) {
            System.out.print(mejorSolucion[i]+" ");
        }
        System.out.println("");
        System.out.println("El costo del camino inicial es: "+fitnessMejorSolucion);
        gama = 1/fitnessMejorSolucion;
        feromonas = new float[N.length][N.length];
        vif = 1/fitnessMejorSolucion;
        for ( int i = 0; i < N.length; i++ ) {                    //inicializo matriz de feromonas
            for ( int j = 0; j < N.length; j++ ) {
                if ( i == j ){
                    feromonas[i][j] = 0;
                }else{
                feromonas[i][j] = 1/fitnessMejorSolucion;
                }
            }
        }
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
        float diferenciaX, diferenciaY;
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
    public static float costo(int[] solucion){
        float costo = 0;
        for (int i = 0; i < (distancia.length-1); i++) {
            costo += distancia[solucion[i]][solucion[i+1]];
        }    
        costo += distancia[solucion[solucion.length-1]][solucion[0]];
        return costo;
    }
    public void setInicio(int[] inicio){
        for (int i = 0; i < antVector.length; i++) {
            antVector[i].setCamino(0, inicio[i]);
        }
    }
    public void actualizacionGlobalFeromona(){
        for (int i = 0; i < feromonas.length; i++) {
            for (int j = 0; j < feromonas.length; j++) {
                feromonas[i][j] = 1/((1-alfa)*feromonas[i][j]);
            }
        }
        for (int i = 1; i < feromonas.length; i++) {
            feromonas[mejorSolucion[i-1]][mejorSolucion[i]] += alfa*gama;
        }
        feromonas[mejorSolucion[mejorSolucion.length-1]][mejorSolucion[0]] +=alfa*gama;
    }
    public void antStart(){      
        for (Ant antVector1 : antVector) {
            antVector1.Run(mDeterministica);
        }
        for (Ant antVector1 : antVector) {
            try {
                antVector1.join();
            }catch(InterruptedException e){
            }
        }
        gama = 1/fitnessMejorSolucion;
        actualizacionGlobalFeromona();
    }
}
