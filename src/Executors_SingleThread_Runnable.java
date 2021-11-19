import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Executors_SingleThread_Runnable {

    public static void main(String[] args) {
        ExecutorService executor = null;

        try {
            executor = Executors.newSingleThreadExecutor();
            executor.execute(new Tarefa());
            executor.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // garante que mesmo que ocorra um erro, o shutdown ainda assim vai ser executado
            if (executor != null) {
                executor.shutdown();
            }
        }
    }

    public static class Tarefa implements Runnable {

        @Override
        public void run() {
            String name = Thread.currentThread().getName();
            System.out.println(name + ": Increva-se no canal!");
        }
    }
}
