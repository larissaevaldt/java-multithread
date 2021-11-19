/* JAVA MULTITHREAD - Volatile (uma palavra reservada) e Yeld (um metodo) (Video 8)
 *
 */
public class Volatile {

    private static int numero = 0;
    private static boolean preparado = false;

    private static class MeuRunnable implements Runnable {

        @Override
        public void run() {
            while(!preparado) {
                /* yeld() e uma forma Thread avisar para o processador que ela nao tem trabalho para ser feito
                 * naquele momento. O processador entao pode pausar a execucao dessa Thread, e executar outra Thread
                 * Ou seja, yield fornece o tempo de processador para uma outra Thread, abrindo mao da sua execucao.
                 * Mas ela nao para, ela so da uma pausa, o processador prioriza alguma outra Thread enquanto isso
                 */
                Thread.yield();
            }

            System.out.println(numero);
        }
    }

    public static void main(String[] args) {
        Thread t0 = new Thread(new MeuRunnable());
        t0.start();
        numero = 42;
        preparado = true;
    }
}
