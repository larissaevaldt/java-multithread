import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *  JAVA MULTITHREAD - Semaphore
 *  1) TryAquire
 *
 */
public class Semaphore_2 {
    // quando criamos um Semaphore precisamos dizer quantas Threads podem passar pelo semaforo ao mesmo tempo:
    private static final Semaphore SEMAFORO = new Semaphore(3);

    private static final AtomicInteger QTD = new AtomicInteger(0); //comeca com 0 pq inicialmente nao vai ter ninguem esperando

    public static void main(String[] args) {
        // Executor que vai criando Threads conforme o necessario
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(501);

        // Tarefa que simila como se fossem usuarios tentando executar alguma acao
        // criamos um numero aleatorio que vai identificar quem e esse usuario
        // e vamos imprimir o nome da Thread, e o id do usuario
        Runnable r1 = () -> {
            String name = Thread.currentThread().getName();
            int usuario = new Random().nextInt(10000);

            // fica tentando pra sempre ate que consiga uma vaga no semaforo (de 3 vagas ao total)
            boolean conseguiu = false;
            //antes da r1 tentar pegar essa vaga ela vai incrementar aquela variavel
            QTD.incrementAndGet();
            while(!conseguiu) {
                conseguiu = tryAcquire();
            }
            // caso consiga, ela vai decrementar a variavel
            QTD.decrementAndGet();
            System.out.println("Usuário " + usuario + ", usando a Thread " + name);

            sleep(); //so para que vejamos isso acontecendo aos poucos

            SEMAFORO.release(); // vai falar ok, ja fiz o que eu tinha que fazer, pode dar a minha vaga pra proxima Tarefa
        };
        //so para ver as coisas acontecendo melhor, criamos uma segunda tarefa que
        // vai exibir quantas tarefas ainda estao esperando para serem executadas
        Runnable r2 = () -> {
            System.out.println(QTD.get());
        };

        // agora vamos colocar varias vezes a tarefa para ser executada
        for (int i=0; i<500; i++) {
            executor.execute(r1);
        }
        executor.scheduleWithFixedDelay(r2,0,100,TimeUnit.MILLISECONDS);

    }
    //tenta adquirir uma vaga por no maximo 1 segundo
    //se conseguir retorna true, se nao retorna false.
    private static boolean tryAcquire() {
        try {
            //o try aquire, precisa passar pra ele quanto tempo queremos que ele fique esperando.
            return SEMAFORO.tryAcquire(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
            return false;
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
