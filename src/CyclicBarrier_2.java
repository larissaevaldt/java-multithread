import java.util.concurrent.*;

public class CyclicBarrier_2 {

    private static BlockingQueue<Double> resultados = new LinkedBlockingQueue<>();

    // 432*3 + 3^14 + 45*127/12 = ?
    public static void main(String[] args) {
        //cria uma tarefa com uma expressao lambda, declarando qual e o comportamento do metodo run() desse Runnable
        Runnable finalizacao = () -> {
            System.out.println("Somando tudo.");
            double resultadoFinal = 0;
            resultadoFinal += resultados.poll();
            resultadoFinal += resultados.poll();
            resultadoFinal += resultados.poll();
            System.out.println("Processamento finalizado. Resultado final: " + resultadoFinal);
        };
        //esse 3 e a qtd de participantes que estao participando dessa "barreira"
        // Quantas Threads tem que avisar a nossa barreira pra que elas possam continuar
        // finalizacao.run() vai ser chamado pelo proprio CyclicBarrier depois que 3 Threads chamarem o await()
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3, finalizacao);

        // Criando um Executor com 3 Threads
        ExecutorService executor = Executors.newFixedThreadPool(3);

        // Criando as tarefas, cada um vai fazer um pedaco da equacao
        // Percebam que eu nao criei uma classe, como nos outros exemplos
        // Aqui estamos usando funcoes lambda para ocupar menos espaco de codigo
        Runnable r1 = () -> {
            System.out.println(Thread.currentThread().getName());
            resultados.add(432d*3d);
            await(cyclicBarrier);
            System.out.println(Thread.currentThread().getName());
        };
        Runnable r2 = () -> {
            System.out.println(Thread.currentThread().getName());
            resultados.add(Math.pow(3, 14));
            await(cyclicBarrier);
            System.out.println(Thread.currentThread().getName());
        };
        Runnable r3 = () -> {
            System.out.println(Thread.currentThread().getName());
            resultados.add(45d*127d/12d);
            await(cyclicBarrier);
            System.out.println(Thread.currentThread().getName());
        };

        //executando as tarefas
        executor.submit(r1);
        executor.submit(r2);
        executor.submit(r3);

        // para o nosso programa nao ficar executando pra sempre, precisamos chamar o shutdown do executor
        executor.shutdown();

    }
    //await faz a Thread ficar esperando
    private static void await(CyclicBarrier cyclicBarrier) {
        try {
            cyclicBarrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }
}
