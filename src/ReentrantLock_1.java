import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
* JAVA MULTITHREAD - Video 15 - ReentrantLock
* Pare de usar SYNCHRONIZED nas suas THREADS. Use CLASSES de LOCKS
* essas classes ajudam-nos a travar recursos, para que eles nao sejam utilizados
* por várias Threads ao mesmo tempo.
 */
public class ReentrantLock_1 {
    /* O lock serve para situacoes onde temos um recurso, pode ser uma variável, ou um acesso
    * ao banco de dados, um acesso a um arquivo, nao importa exatamente qual o recurso.
    * O recurso vai ser sempre aquilo que esta sendo utilizado, que esta sendo acessado ou
    * modificado por mais de uma Thread ao mesmo tempo.
    * E quando voce quer evitar que varias Threads consigam alterar, modificar, ler or acessar
    * esse mesmo recurso ao mesmo tempo, voce vai usar esses Locks
     */

    private static int i = -1; //o recurso compartilhado

    /* Lock e a interface, e a implementacao e o ReentrantLock
    * Como funciona? Nos primeiro criamos um novo objeto de Lock
    * e quando chamamos o metodo .lock(), nos travamos o acesso a partir desse ponto
    * A primeira Thread que chegar ali vai obter o lock, e as proximas nao vao conseguir obter,
    * As proximas vao ficar paradas ali esperando,
    * Somente depois que a primeira Thread liberar chamando o .unlock(),
    * que a proxima Thread que estiver na fila conseguira entao, obter o Lock
     */
    private static Lock lock = new ReentrantLock();

    public static void main(String[] args) {

        // Executor vai usar a quantidade de Threads que ele precisar
        ExecutorService executor = Executors.newCachedThreadPool();

        // Agora criamos uma tarefa que vai incrementar uma variável
        // que vai ser o nosso recurso compartilhado, e imprimir ela
        Runnable r1 = () -> {
            // chamamos .lock() pra fazer a trava, como se fosse a entrada do bloco synchronized
            lock.lock();

            String name = Thread.currentThread().getName();
            i++;
            System.out.println(name + " lendo e incrementando " + i);

            // e no final chamamos o unlock(). Como se estivessemos saindo do bloco synchronized
            lock.unlock();
        };

        //coloca a tarefa para ser executada varias vezes
        for(int i=0; i<6; i++) {
            executor.execute(r1);
        }

        executor.shutdown();
    }
}
