import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
* JAVA MULTITHREAD - CyclicBarrier (barreira ciclica)
* Apresentando algumas classes que servem como ferramenta para nos ajudar quando temos esse cenario de varias
* Threads funcionando ao mesmo tempo.
* CyclicBarrier - Use quando estiver em uma situacao em que voce tem varias Threads executando em paralelo
* E voce precisa que em algum momento, essas threads vao uma esperar pela outra.
* Ex: 3 Threds executando tarefas diferentes, e voce precisa, em algum momento, forcar essas 3 threads a esperar
* umas pelas outras.
* 1) Numero de participantes
 */
public class CyclicBarrier_1 {

    // 432*3 + 3^14 + 45*127/12 = ?
    public static void main(String[] args) {
        //esse 3 e a qtd de participantes que estao participando dessa "barreira"
        // Quantas Threads tem que avisar a nossa barreira pra que elas possam continuar
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3);

        // Criando um Executor com 3 Threads
        ExecutorService executor = Executors.newFixedThreadPool(3);

        // Criando as tarefas, cada um vai fazer um pedaco da equacao
        // Percebam que eu nao criei uma classe, como nos outros exemplos
        // Aqui estamos usando funcoes lambda para ocupar menos espaco de codigo
        Runnable r1 = () -> {
            System.out.println(432d*3d); //o d transforma os numeros em double
            await(cyclicBarrier);
            System.out.println("Terminei o processamento.");
        };
        Runnable r2 = () -> {
            System.out.println(Math.pow(3, 14));
            await(cyclicBarrier);
            System.out.println("Terminei o processamento.");
        };
        Runnable r3 = () -> {
            System.out.println(45d*127d/12d);
            await(cyclicBarrier);
            System.out.println("Terminei o processamento.");
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
