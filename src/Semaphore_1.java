import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 *  JAVA MULTITHREAD - Semaphore
 *  1) Aquire
 *
 *  Pra que serve a classe de Semaphore:
 *  Se voce viu os videos da CyclicBarrier e CountdownLatch voce ja deve ter percebido que o Java tem algumas ferramentas
 *  algumas classes que servem como se fosse uma ferramenta pra gente lidar com a concorrencia entre multiplas Threads.
 *  Seja para limitar de alguma forma essa concorrencia ou pra fazer algum controle em cima dela, quais sao as Threads que
 *  a gente quer deixar executar e coisas desse sentido.
 *  O Semaphore e mais uma classe que ajuda a gente a fazer esse controle dessa concorrencia e desse paralelismo.
 *
 *  Voce vai usar o Semaphore quando voce sabe a quantidade de Threads que voce quer que possam executar um trecho de codigo ao mesmo tempo
 *  O Semaphore vai servir, fazendo literalmente uma barreira que permite ou nao, que uma Thread continue com o processamento
 *  e ela faz isso baseado na quantidade de Threads que voce permite que prossigam, na quantidade de "vagas" que voce disponibiliza pra esse Semaphore.
 */
public class Semaphore_1 {

    // quando a gente cria um Semaphore a gente precisa dizer quantas Threads podem passar pelo semaforo ao mesmo tempo:
    private static final Semaphore SEMAFORO = new Semaphore(3);

    public static void main(String[] args) {
        // Executor que vai criando Threads conforme o necessario
        ExecutorService executor = Executors.newCachedThreadPool();

        // Tarefa que simila como se fossem usuarios tentando executar alguma acao
        // criamos um numero aleatorio que vai identificar quem e esse usuario
        // e vamos imprimir o nome da Thread, e o id do usuario
        Runnable r1 = () -> {
            String name = Thread.currentThread().getName();
            int usuario = new Random().nextInt(10000);

            // agora antes de imprimir no console, o usuario tera que conseguir passar pelo Semaphore
            // lembrando, nos so temos 3 vagas, entao as 3 primeiras Threads que chegarem aqui vao conseguir passar
            // as proximas irao ficar esperando
            acquire();

            System.out.println("Usuário " + usuario + ", usando a Thread " + name);

            sleep(); //so para que vejamos isso acontecendo aos poucos

            SEMAFORO.release(); // vai falar ok, ja fiz o que eu tinha que fazer, pode dar a minha vaga pra proxima Tarefa
        };

        // agora vamos colocar varias vezes a tarefa para ser executada
        for (int i=0; i<500; i++) {
            executor.execute(r1);
        }

        executor.shutdown();
    }
    private static void acquire() {
        try {
            SEMAFORO.acquire();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }
    private static void sleep() {
        //espera entre 1 a 6 segundos
        try {
            int tempoEspera = new Random().nextInt(6);
            Thread.sleep(1000 * tempoEspera);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }
}
