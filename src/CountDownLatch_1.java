import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/*
* JAVA MULTITHREAD - CountDownLatch
* Classe parecida com a CyclicBarrier, as duas sao usadas em situacoes parecidas, mas
* a gente consegue ter um pouco mais de flexibilidade em alguns pontos e menos flexibilidade em outros
* Em que situacao voce vai pensar em usar CountDownLatch?
* Quando voce tiver multiplas Threads executando/fazendo alguma coisa (pode ser a mesma tarefa ou tarefas diferentes)
* mas que depois de uma certa quantidade de vezes que essas Threads executarem, voce quer executar uma outra coisa
* ou varias outras coisas.
* Entao, voce tem alguma tarefa, e voce quer que ela execute, por ex., 10 vezes e a cada 10 vezes que ela executar
* voce vai executar uma outra tarefa.
* Coisas nesse sentido, sempre que voce tiver uma quantidade fixa de vezes que voce quer executar alguma coisa
* pra ai voce poder executar outra. CountdownLatch vai fazer isso de contar quantas vezes uma coisa esta sendo feita
* pra voce entao liberar uma outra
* 1) Um await
* quero que a cada 3 execucoes de r1, o valor de i mude
* Desvantagem: Nao e reutilizável
* Parecido com o CyclicBarrier mas vc tem um pouco mais de flexibilidade pq o CyclicBarrir so tem o metodo await()
* tudo e uma operacao so, eu chamo o await(), e a mesma Thread que vai ficar esperando e que vai executar
* o CountDownLatch nao, ele separa quem ta esperando de quem esta contando
* Eu consigo ter tarefas que estao decrementando meu contador, enquanto tenho uma outra tarefa que esta esperando
* esse contador chegar em 0 pra fazer alguma coisa. Ele separa essas 2 responsabilidades: contar e esperar pra fazer algo.
* Mas temos menos flexibilidade no sentido que ele nao e reaproveitavel, nao e ciclico, nao reinicia pra gente a contagem
* inicial, depois que chegou em 0, eu tenho que atribuir um novo CountDownLatch se eu quiser ficar fazendo essa execucao circular
 */
public class CountDownLatch_1 {

    //volatile garante que esse valor aqui ta sempre o mais atualizado possivel
    //sempre buscando o valor da RAM e nao do cache local
    private static volatile int i = 0;
    private static CountDownLatch latch = new CountDownLatch(3);

    public static void main(String[] args) {
        //Executor q consegue agendar tarefas para serem executadas. Com 3 Threads disponiveis para o Executor.
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(3);

        // cria a tarefa que esse Executor vai ficar fazendo
        Runnable r1 = () -> {
            int j = new Random().nextInt(1000);
            int x = i*j;
            System.out.println(i + " x " + j + " = " + x);
            //toda vez que r1 executar, vamos chamar o CountDown do latch
            latch.countDown(); // a gente chama o countdown onde queremos decrementar o contador
        };

        // coloca o Executor pra de fato executar o Runnable r1
        // agenda o Runnable para ser executado a cada 1 segundo com um delay de 0 segundos
        // a cada 1 segundo o Runnable sera executado
        executor.scheduleAtFixedRate(r1,0,1, TimeUnit.SECONDS);

        // ficar mudando o valor do i
        while(true) {
            //sleep();
            //instead of sleep, vamos fazer latch.await()
            // agora ao inves de ficar parado por um tempo especifico, vai ficar parado ate que:
            await(); //espera ate o CountDownLatch ser decrementado a 0, ate que r1 rode 3 vezes
            i = new Random().nextInt(100);
            // precisamos nos mesmos criar um novo CountDownLatch pra que reinicie, pra q volte a 3
            latch = new CountDownLatch(3);
        }
    }

    private static void await() {
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

//    private static void sleep() {
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
}
