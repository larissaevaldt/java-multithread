//demonstrando que pode acontecer de o numero nao ser 42, se nao usarmos o volatile
public class Volatile2 {
    /* Para entender pra que serve a palavra reservada "volatile", nos precisamos entender um pouco
     * como que funciona a leitura dessas variaveis dentro do processador. Em geral, a gente imagina que as variaveis
     * estao na memoria RAM, e quando passamos na linha 43, o Java foi la e escreveu 42 onde esta a variavel numero na memoria RAM.
     * e o nosso if statement na linha 27, quando foi ler, ele foi la na memoria RAM e viu se ta 42 ou nao esta 42
     * mas na verdade, o que o processador faz quando vamos ler ou escrever o valor da variavel numero
     * o que voce faz na verdade e escrever em um cache local, que esta dentro do seu processador e voce pode,
     * tanto estar lendo de la, quando escrevendo pra la. E em algum momento existe uma sincronia, em algum momento
     * o valor que esta no cache local ele e sincronizado com a RAM
     * Volatile diz para o seu programa nao confiar no que esta no cache local do processador, escreva e leia esse dado
     * sempre direto da memoria RAM
     * Na pratica voce vai garantir que todas as Threads que estao lendo o valor daquela variavel, vao sempre ler o
     * valor mais recente, mais atualizado possivel daquela variavel
     */
    private static volatile int numero = 0;
    private static volatile boolean preparado = false;

    private static class MeuRunnable implements Runnable {

        @Override
        public void run() {
            while(!preparado) {
                Thread.yield();
            }

            if (numero != 42) {
                System.out.println(numero);
                throw new IllegalStateException("Inscreva-se no canal!");
            }
        }
    }

    public static void main(String[] args) {
        while(true) {
            Thread t0 = new Thread(new MeuRunnable());
            t0.start();
            Thread t1 = new Thread(new MeuRunnable());
            t1.start();
            Thread t2 = new Thread(new MeuRunnable());
            t2.start();

            numero = 42;
            preparado = true;

            while (
                    t0.getState() != Thread.State.TERMINATED
                    || t1.getState() != Thread.State.TERMINATED
                    || t2.getState() != Thread.State.TERMINATED
            ) {
                // WAIT
            }

            numero = 0;
            preparado = false;
        }
    }
}
