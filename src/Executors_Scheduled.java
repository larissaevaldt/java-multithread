import java.util.Random;
import java.util.concurrent.*;

/**
 * JAVA MULTITHREAD - Executors - Scheduled
 * Tarefas que sao agendadas. Ex: A cada 1 segundo, a cada 1 minuto, ou a cada 30 segundos
 *
 */
public class Executors_Scheduled {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // precisamos especificar a quantidade de Threads que a gente vai deixar ele usar
        // a interface dele diferente mas o funcionamento e parecido
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(3);

        System.out.println(System.currentTimeMillis());
        //agenda a Tarefa para daqui a dois segundos
        // nos podemos pegar o retorno, que e um ScheduledFuture
        ScheduledFuture<String> future = executor.schedule(new Tarefa(), 2, TimeUnit.SECONDS);

        System.out.println(future.get()); //fica esperando a Tarefa finalizar
        System.out.println(System.currentTimeMillis());

        executor.shutdown();
    }

    public static class Tarefa implements Callable<String> {
        @Override
        public String call() throws Exception {
            String name = Thread.currentThread().getName();
            int nextInt = new Random().nextInt(1000);
            return name + ": Inscreva-se no canal! " + nextInt;
        }
    }
}
