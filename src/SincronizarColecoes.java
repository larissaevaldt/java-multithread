import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
* JAVA MULTITHREAD - Sincronizar Colecoes
* Classes que sao bem uteis quando temos que lidar com varias Threads ao mesmo tempo
* basicamente alternativas pra nao precisar usar a palavra synchronized, e ainda assim resolver problemas parecidos
 */
public class SincronizarColecoes {

    private static List<String> lista = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        // sobreescreve a lista com uma versao sincronizada dessa lista
        // o Java pega a lista que a gente ja tinha e colocou uma protecao em volta dela, pra ela
        // so conseguir ser acessada, em algumas operacoes, por uma unica Thread
        lista = Collections.synchronizedList(lista);
        // Utilize a versao do synchronizedXXX de acordo com o tipo de colecao! Ex:
        //lista = Collections.synchronizedCollection(lista);
        //lista = Collections.synchronizedMap(lista);
        //lista = Collections.synchronizedSet(lista);

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
