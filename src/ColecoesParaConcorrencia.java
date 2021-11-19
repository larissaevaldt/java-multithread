import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/*
 * JAVA MULTITHREAD - Colecoes utilizadas quando ha Concorrencia - Thread safe Collections
 * Continua o assunto de tentar evitar o uso da palavra synchronized em alguns contextos onde temos
 * opcoes melhores.
 */
public class ColecoesParaConcorrencia {
    //CopyOnWriteArrayList e uma classe que e Thread-safe.
    //Thread-safe sao classes que sao seguras de serem utilizadas quando voce tem esse scenario de Multi Thread
    //quando se tem varias Threads acessando a colecao. Significa que essas classes podem ser acessadas
    //por varias Threads sem causar problemas
    private static List<String> lista = new CopyOnWriteArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        // Em alguns momentos vamos precisar usar colecoes, listas, mapas, etc.
        // que vao estar sendo acessadas por varias Threads. No ultimo video falamos sobre como sincronizar o acesso a elas
        // Mas, tem uma alternativa ainda melhor, dependendo do caso:
        // Se voce tiver um caso que possa usar uma dessas classes, e melhor do que usar a palavra reservada synchronized
        // 1. CopyOnWriteArrayList
        // 2. ConcurrentHashMap
        // 3. LinkedBlockingQueue
        // 4. LinkedBlockingDeque

       MyRunnable myRunnable = new MyRunnable();
        Thread t0 = new Thread(myRunnable);
        Thread t1 = new Thread(myRunnable);
        Thread t2 = new Thread(myRunnable);
        t0.start();
        t1.start();
        t2.start();
        Thread.sleep(500);
        System.out.println(lista);
    }

    public static class MyRunnable implements Runnable {
        @Override
        public void run() {
            lista.add("Inscreva-se no canal!");
            String name = Thread.currentThread().getName();
            System.out.println(name + " inseriu na lista!");
        }
    }
}
