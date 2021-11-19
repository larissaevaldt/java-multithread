/*
* JAVA MULTITHREAD - Executors - SingleThread
* Uma alternativa melhor ao uso da classe Thread
* 1. Single Thread - Um executor com uma unica Thread
* 2. Executor com varias Threads
* 3. Executor que agenda tarefas
 */

import java.util.Random;
import java.util.concurrent.*;
// Existe uma classe chamada Executors, e essa classe contem varios metodos pra criar diferentes tipo de Executores
public class Executors_SingleThread_Callable {

    public static void main(String[] args) {
        // Executors single Thread
        // nao quer dizer que a gente nao ta usando multi thread no nosso programa
        // lembre: so de estar criando esse executor, a gente ja tem 2 threads: a principal (thread main) mais a outra que o Executor vai executar as tarefas
        ExecutorService executor = null;
        try {
            executor = Executors.newSingleThreadExecutor();
            /**
             * com o Executor nos podemos fazer varias execucoes, ex. podemos pegar 1 executor e mandar executar
             * varias coisas, porem quando usamos Thread, nao podemos executar a mesma Thread mais que uma vez
             */
            // executor.execute(new Tarefa());
            // executor.execute(new Tarefa());
            // executor.execute(new Tarefa());

            /* execute() e o metodo mais simples, mais direto.
             * uma alternativa mais interessante que temos do que o metodo execute, e o metodo submit()
             * quando chamamos o submit, nos conseguimos ter um retorno, que e o Future!
             */
            Future<String> future = executor.submit(new Tarefa());

            //o Future permite ver algumas coisas, como por exemplo o isDone(), que e pra saber se a sua tarefa ja foi executada
            System.out.println(future.isDone()); //vendo se a tarefa ja foi finalizada

            try {
                //System.out.println(future.get());

                //uma outra alternativa e o get() passando um time out. Ex: Espera no max. 1 segundo p essa tarefa terminar
                System.out.println(future.get(1, TimeUnit.SECONDS));
            } catch (ExecutionException | TimeoutException e) {
                e.printStackTrace();
            }

            // quando eu crio um executor eu tenho que depois chamar o metodo shutdown()
            //mas o que pode acontecer aqui e ele nao terminar de executar a tarefa que a gente passou pra ele
            // entao uma boa coisa a fazer antes de chamar o shutdown e esse metodo aqui, awaitTermination()
            executor.awaitTermination(10, TimeUnit.SECONDS); //awaitTermination espera o shutdown()
            executor.shutdown(); //nao aceita nenhuma tarefa nova mais, porem espera todas as tarefas que ja estavam na fila finalizarem a execucao

            System.out.println(future.isDone());

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (executor != null) {

                // Uma alternativa, um pouco mais agressiva que o shutdown(), e o shutdownNow()
                // nao vai tentar fazer com que as tarefas que estavam em execucao no seu Executor, sejam paradas calmamente
                // shutdownNow faz uma parada mais abrupta
                executor.shutdownNow();
            }
        }
    }

    // Implementing Callable instead of Runnable
    // Callable has the method call() instead of run()
    // and instead of just executing something, it has to return something as well
    public static class Tarefa implements Callable<String> {

        @Override
        public String call() throws Exception {
            String name = Thread.currentThread().getName();
            int nextInt = new Random().nextInt(1000);
            return name + ": Inscreva-se no canal! " + nextInt;
        }
    }
}
