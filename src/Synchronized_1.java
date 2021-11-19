/*
* JAVA MULTITHREAD - Synchronized
* Entendendo como funciona a concorrencia entre as threads
* Desvantagem: Acaba com o paralelismo
* Quando criamos varias Threads, queremos que o codigo execute em paralelo
 */
public class Synchronized_1 {

    static int i = -1;

    public static void main(String[] args) {
        MyRunnable myRunnable = new MyRunnable();

        // Dentro da Thread main, criamos 5 outras threads
        // cada uma delas vai executar o metodo run()
        // e elas vao fazer isso em paralelo
        Thread t0 = new Thread(myRunnable);
        Thread t1 = new Thread(myRunnable);
        Thread t2 = new Thread(myRunnable);
        Thread t3 = new Thread(myRunnable);
        Thread t4 = new Thread(myRunnable);

        // There is no guarantee of who is going to execute first
        t0.start();
        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }

    public static class MyRunnable implements Runnable {
        static Object lock1 = new Object();
        static Object lock2 = new Object();
        // o uso de synchronized e como se fosse uma barreira
        // a partir de agora so uma Thread pode executar esse metodo por vez
        // dentro dessa instancia, estamos usando a mesma instancia pra todas as Threads
        // a Thread que entrar no method run() precisa executar tudo e sair, e so depois outra podera entrar
        @Override
        //public synchronized void run() {
        public void run() {
            System.out.println("ab");

            //em um bloco sincronizado precisamos dizer em qual objecto sera feita a sincronia
            //podemos ter varios blocos sincronizados
            synchronized (lock1) {
                i++;
                String name = Thread.currentThread().getName();
                System.out.println(name + ": " + i);
            }
            synchronized (lock2) {
                i++;
                String name = Thread.currentThread().getName();
                System.out.println(name + ": " + i);
            }
        }
    }
}
