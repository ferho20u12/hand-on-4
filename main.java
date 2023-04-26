import java.util.ArrayList; 
import java.util.Random;

class Cromosoma{    
    ArrayList<Integer> cromosoma = new ArrayList<Integer>();

    public Cromosoma()
    {
        Random random = new Random();
        for(int i = 0; i<20;i++)
        {
            this.cromosoma.add(random.nextInt(2));
        }
    }
    public int fitness()
    {
        return Math.toIntExact(cromosoma.stream().filter(i -> i == 1).count());
    }

}

class GeneticAlgorithms {
    static ArrayList<Cromosoma> cromosomas = new ArrayList<Cromosoma>();
    static final int POBLACION_SIZE = 100; 
    static final double CROSSOVER_RATE = 0.7; 
    static final double MUTATION_RATE = 0.5;

    public static void main(String[] args) {
        CrearPoblacion();
        while(true){
            Cromosoma mejorCromosoma = SeleccionarMejorCromosoma();
            if(mejorCromosoma.fitness() == 20){
                System.out.println("Encontrado: " + mejorCromosoma.cromosoma);
                break;
            }
            ArrayList<Cromosoma> nuevaGeneracion = new ArrayList<Cromosoma>();
            for(int i = 0; i < POBLACION_SIZE; i++){
                Cromosoma padre1 = SeleccionarPadrePorRuleta();
                Cromosoma padre2 = SeleccionarPadrePorRuleta();
                Cromosoma hijo = Crossover(padre1, padre2);
                Mutar(hijo);
                nuevaGeneracion.add(hijo);
            }
            cromosomas = nuevaGeneracion;
        }
    }
    public static Cromosoma SeleccionarPadrePorRuleta() {

        int sumaFitness = cromosomas.stream().mapToInt(Cromosoma::fitness).sum();

        Random random = new Random();
        int valorRuleta = random.nextInt(sumaFitness);

        int probabilidadAcumulada = 0;
        for (Cromosoma cromosoma : cromosomas) {
            probabilidadAcumulada += cromosoma.fitness();
            if (probabilidadAcumulada > valorRuleta) {
                return cromosoma;
            }
        }

        return cromosomas.get(random.nextInt(cromosomas.size()));
    }
    
    public static void CrearPoblacion() {
        for (int i = 0; i < POBLACION_SIZE; i++) {
            cromosomas.add(new Cromosoma());
        }
    }
    public static Cromosoma SeleccionarMejorCromosoma(){
        Cromosoma mejorCromosoma = cromosomas.get(0);
        for(Cromosoma cromosoma : cromosomas){
            if(cromosoma.fitness() > mejorCromosoma.fitness()){
                mejorCromosoma = cromosoma;
            }
        }
        return mejorCromosoma;
    }
    public static Cromosoma Crossover(Cromosoma padre1, Cromosoma padre2) {
        Cromosoma hijo = new Cromosoma();
        Random random = new Random();
        for(int i = 0; i < 20; i++) {
            double rand = random.nextDouble();
            if(rand <= CROSSOVER_RATE) {
                hijo.cromosoma.set(i, padre1.cromosoma.get(i));
            } else {
                hijo.cromosoma.set(i, padre2.cromosoma.get(i));
            }
        }
        return hijo;
    }
    public static void Mutar(Cromosoma cromosoma) {
        Random random = new Random();
        for(int i = 0; i < 20; i++) {
            double rand = random.nextDouble();
            if(rand <= MUTATION_RATE) {
                int valor = cromosoma.cromosoma.get(i);
                cromosoma.cromosoma.set(i, valor == 1 ? 0 : 1);
            }
        }
    }
}