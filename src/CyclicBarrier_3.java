import java.util.concurrent.*;

/*
* demonstrando o por que que e uma barreira "ciclica":
* que podemeos ter uma tarefa que nao e so de finalizacao, ela pode ser uma tarefa
* meio que de sumarizacao
* Nos criamos e inicializamos a barreira uma vez so e reutilizamos ela.
* 3 Threads atingiram aquela barreira? Executa a finalizacao e podemos re executar
* a contagem da barreira q chegou no 3 volta pra 0
 */
public class CyclicBarrier_3 {
    private static BlockingQueue<Double> resultados = new LinkedBlockingQueue<>();
    private static ExecutorService executor;
    private static Runnable r1;
    private static Runnable r2;
    private static Runnable r3;
    private static double resultadoFinal = 0;

    // 432*3 + 3^14 + 45*127/12 = ?
    public static void main(String[] args) {
        //cria uma tarefa com uma expressao lambda, declarando qual e o comportamento do metodo run() desse Runnable
        Runnable sumarizacao = () -> {
            System.out.println("Somando tudo.");
            resultadoFinal += resultados.poll();
            resultadoFinal += resultados.poll();
            resultadoFinal += resultados.poll();
            System.out.println("Processamento finalizado. Resultado final: " + resultadoFinal);
            System.out.println("-------------------------------------");
            // O restart() nao precisa estar aqui. A propria tarefa pode fazer o seu proprio while(true) loop
 //           restart();
        };
        //esse 3 e a qtd de participantes que estao participando dessa "barreira"
        // Quantas Threads tem que avisar a nossa barreira pra que elas possam continuar
        // finalizacao.run() vai ser chamado pelo proprio CyclicBarrier depois que 3 Threads chamarem o await()
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3, sumarizacao);

        // Criando um Executor com 3 Threads
        executor = Executors.newFixedThreadPool(3);

        // Criando as tarefas, cada um vai fazer um pedaco da equacao
        // Percebam que eu nao criei uma classe, como nos outros exemplos
        // Aqui estamos usando funcoes lambda para ocupar menos espaco de codigo
        r1 = () -> {
            while(true) {
                resultados.add(432d*3d);
                await(cyclicBarrier);
                sleep();
            }
        };
        r2 = () -> {
            while(true) {
                resultados.add(Math.pow(3, 14));
                await(cyclicBarrier);
                sleep();
            }
        };
        r3 = () -> {
            while(true) {
                resultados.add(45d*127d/12d);
                await(cyclicBarrier);
                sleep();
            }
        };

        //quando chegar aqui, ele vai executar o submit, que a gente ja tava fazendo antes
        restart();

        // nao vou chamar o shutdown, ou seja, nosso programa vai ficar rodando pra sempre
       // executor.shutdown();

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

    private static void restart() {
        sleep();
        //executando as tarefas
        executor.submit(r1);
        executor.submit(r2);
        executor.submit(r3);
    }

    private static void sleep() {
        // sleep so para que nao fique muito rapido, ja que esse codigo vai executar varias vezes
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
