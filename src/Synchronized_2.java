// 1. descobrir qual o recurso q esta sendo disputado por mais de uma Thread ao mesmo tempo
// 2. bloqueia o recurso concorrido em um trecho de codigo sincronizado
public class Synchronized_2 {

    private static int i = 0;

    public static void main(String[] args) {
        MyRunnable myRunnable = new MyRunnable();

        Thread t0 = new Thread(myRunnable);
        Thread t1 = new Thread(myRunnable);
        Thread t2 = new Thread(myRunnable);
        Thread t3 = new Thread(myRunnable);
        Thread t4 = new Thread(myRunnable);

        t0.start();
        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }

    public static class MyRunnable implements Runnable {

        @Override
        public void run() {
            int j;
            //isola o uso do bloco synchronized apenas para quando existe a concorrencia,
            //nesse caso para acessar a variavel i, uma variavel statica, global, que esta sendo
            //acessada, a mesma variavel, por todas essas Threads
            synchronized (this) {
                i++;
                j = i *2;
            }
            //a partir do momento q o q eu vou fazer nao tem mais ligacao nenhuma com o recurso q esta sendo
            //concorrido por varias Threads, nao precisa mais de synchronized
            //depois a cada Thread ja calculou o seu j, nao importa o q as outras Threads tao fazendo
            double jElevadoA10 = Math.pow(j, 100);
            double sqrt = Math.sqrt(jElevadoA10);
            System.out.println(sqrt);
        }
    }

}
