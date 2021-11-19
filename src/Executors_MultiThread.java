import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/*
* JAVA MULTITHREAD - Executors - MultiThread
* Demonstrando como usar o Executor de uma forma mais util.
* Criando varias Threads e botando ele pra executar uma tarefa pra gente.
 */
public class Executors_MultiThread {

    public static void main(String[] args) {

        ExecutorService executor = null;
        try {
            // cria um Executor que vai utilizar 4 Threads diferentes
            // Esse Executor, quando ele for executar alguma coisa, ele vai ter um conjunto de 4 threads pra escolher
            // qual que ele quer utilizar.
            executor = Executors.newFixedThreadPool(4);

            List<Tarefa> lista = new ArrayList<>();
            for (int i=0; i<10; i++) {
                lista.add(new Tarefa());
            }

            // invokeAll executa todas as tarefas de uma vez so.
            List<Future<String>> list = executor.invokeAll(lista);
            // invokeAny vai executar as tarefas que a gente passar pra ele, mas ele so vai retornar uma
            // provavelmente a primeira, a que finalizar mais rapido
            // o retorno dela ja e uma String, nao Future como no invokeAll
            // Interessante se voce tiver um conjunto de tarefas diferentes a serem executados
            // e voce so precisa do retorno de uma, a que terminar primeiro, voce pega o resultado dela
            String string = executor.invokeAny(lista);

            for (Future<String> future : list) {
                System.out.println(future.get());
            }

            //ainda mais interessante que fixedThreadPool, e o cachedThread pool
            // nao precisa passar uma quantidade fixa de Threads
            // com o cachedThreadPool o Executor cria uma Thread nova sempre que precisar e tambem deleta quando nao precisar mais
            // ou seja, o Executor faz um cache das Threads pra ficar utilizando. E as Threads que nao forem utilizadas
            //por mais de 60 segundos, ele mesmo destroi.
            // O unico cuidado que precisa ter, e que ele nao tem um limite de Threads pra criar.
            executor = Executors.newCachedThreadPool();

            //diferencas de quando temos varias Threads, pra quando estavamos usando o Executor que so tem uma Thread
            Future<String> f1 = executor.submit(new Tarefa());
            Future<String> f2 = executor.submit(new Tarefa());
            Future<String> f3 = executor.submit(new Tarefa());
            System.out.println(f1.get());
            System.out.println(f2.get());
            System.out.println(f3.get());
            executor.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(executor != null) {
                executor.shutdownNow();
            }
        }
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
