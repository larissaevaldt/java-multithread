import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

/*
 * JAVA MULTITHREAD - Classes com operacoes atomicas (Video 7)
 * Classes uteis para evitar o uso da palavra synchronized
 */
public class ClassesAtomicas {
    //static int i = -1;
    static AtomicInteger i = new AtomicInteger(-1);
    static AtomicLong l = new AtomicLong(); //mesma coisa que AtomicInteger mas cabe um valor maior
    static AtomicBoolean b = new AtomicBoolean(false);
    static AtomicReference<Object> r = new AtomicReference<>(new Object()); //pode user qualquer object que quiser

    public static void main(String[] args) {
        MeuRunnable myRunnable = new MeuRunnable();

        Thread t0 = new Thread(myRunnable);
        Thread t1 = new Thread(myRunnable);
        Thread t2 = new Thread(myRunnable);

        t0.start();
        t1.start();
        t2.start();

    }

    public static class MeuRunnable implements Runnable {
        /* usando AtomicInteger e incrementAndGet, os numeros nao se repetem mais,
         * eles podem mudar de ordem, pq a ordem de, de fato conseguir ir imprimir no console nao e garantida,
         * uma Thread pode ganhar da outra, digamos assim, nessa corrida,
         * mas nao se repetem, teremos sempre 0, 1 e 2
         * incrementAndGet() executa de uma vez so, nao vai ser quebradas no meio,
         * nao tem como no meio do caminho, outra Thread atrapalhar aquela primeira Thread
         *
         */
        @Override
        public void run() {
            //i++; //nao conseguimos mais usar essas operacoes de ++ com Atomic Integer
            String name = Thread.currentThread().getName();
            System.out.println(name + ": " + i.incrementAndGet());
            System.out.println(name + ": " + b.compareAndSet(false, true)); //se for falso, altera pra true
            System.out.println(name + ": " + r.getAndSet(new Object())); //pega o valor que estava la, e coloca um novo
        }
    }
}
