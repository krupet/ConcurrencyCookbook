package chapter1.sinc;

public class PrimeGenerator extends Thread {
    @Override
    public void run() {
        long number = 1L;
        while (true) {
            if (isPrimeNumber(number)) {
                System.out.printf("Number %d is prime\n", number);
            }
            if (isInterrupted()) {
                System.out.printf("The Prime generator has been interrupted\n");
                return;
            }
            number++;
        }
    }

    private boolean isPrimeNumber(long number) {
        if (number <= 2) {
            return true;
        }
        for (long i = 2; i< number; i++) {
            if ((number % i) == 0) {
                return false;
            }
        }
        return true;
    }
}
