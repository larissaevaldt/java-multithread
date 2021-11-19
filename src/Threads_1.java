/*
* JAVA MULTITHREAD - Threads and Runnable
* Criando novas linhas de execucao paralelas dentro de um programa que
* vai executar tudo as mesmo tempo, se tiver processador disponivel
 */

public class Threads_1 {

    public static void main(String[] args) {
        // Understand in which thread you are executing
        Thread t = Thread.currentThread();
        System.out.println(t.getName()); //it will print main, which is the default

        MyRunnable myRunnable = new MyRunnable();

        // Create a new Thread
        Thread t1 = new Thread(myRunnable);
        //t1.run(); //this still runs in the main Thread
        t1.start(); // running in a new Thread

        // Runnable como Lambda
        Thread t2 = new Thread(() -> System.out.println(Thread.currentThread().getName()));
        t2.start();
        //t2.start(); // can't start the same thread twice, throws an exception

        // Multiple Threads
        // You can't start the same thread twice BUT
        // You can create another Thread and use the same Runnable
        Thread t3 = new Thread(myRunnable);
        t3.start();
    }
}
