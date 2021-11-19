import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/*
* JAVA MULTITHREAD - CountDownLatch
* 1) Varios await()
* Posso ter uma tarefa, ou varias tarefas chamando o countdown() e eu posso ter varias Threads chamando o await()
* o CountDownLatch tem essa flexibilidade de eu poder ter varias tarefas que estao decrementando o contador
* e varias outras tarefas que estao esperando o contador chegar em 0 pra fazer alguma coisa, inclusive,
* se for o caso, reiniciar esse contador.
 */
public class CountDownLatch_2 {
    private static volatile int i = 0;
    private static CountDownLatch latch = new CountDownLatch(3);

    public static void main(String[] args) {
        //Executor q consegue agendar tarefas para serem executadas. Com 3 Threads disponiveis para o Executor.
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(4);

        // aqui ao inves de ter so 1 tarefa, eu vou fazer mais 3 tarefas, r1 faz a mesma coisa de antes
        Runnable r1 = () -> {
            int j = new Random().nextInt(1000);
            int x = i*j;
            System.out.println(i + " x " + j + " = " + x);
            //toda vez que r1 executar, vamos chamar o CountDown do latch
            latch.countDown(); // a gente chama o countdown onde queremos decrementar o contador
        };
        // as 3 vao chamar o await()
        // a primeira vai atualizar o valor do i
        Runnable r2 = () -> {
            await();
            i = new Random().nextInt(100);
        };
        //a segunda vai atualizar o nosso latch
        Runnable r3 = () -> {
            await();
            latch = new CountDownLatch(3);
        };
        // a terceira vai so imprimir no console
        Runnable r4 = () -> {
            await();
            System.out.println("Terminou! Vamos começar de novo!");
        };

        // coloca o Executor pra de fato executar o Runnable r1
        // agenda o Runnable para ser executado a cada 1 segundo com um delay de 0 segundos
        // a cada 1 segundo o Runnable sera executado
        executor.scheduleAtFixedRate(r1,0,1, TimeUnit.SECONDS);
        //agendar as nossas outras tarefas mas eu nao quero que ela execute a cada 1 segundo
        //eu quero que assim que ela termine de executar, ela comece a executar de novo
        // entao, eu quero que ela execute com um delay fixo
        executor.scheduleWithFixedDelay(r2,0, 1, TimeUnit.SECONDS);
        executor.scheduleWithFixedDelay(r3,0, 1, TimeUnit.SECONDS);
        executor.scheduleWithFixedDelay(r4,0, 1, TimeUnit.SECONDS);

        // ficar mudando o valor do i
        while(true) {
            //sleep();
            //instead of sleep, vamos fazer latch.await()
            // agora ao inves de ficar parado por um tempo especifico, vai ficar parado ate que:
            await(); //espera ate o CountDownLatch ser decrementado a 0, ate que r1 rode 3 vezes

            // precisamos nos mesmos criar um novo CountDownLatch pra que reinicie, pra q volte a 3
        }
    }

    private static void await() {
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
