public class Main {

    static int turnoffs = 5;
    final static int pauseMillis = 2000;
    volatile static boolean switcher = false;

    private static class User extends Thread{
        @Override
        public void run() {
            while (!switcher && turnoffs > 0){
                turnoffs--; switcher = true;
                System.out.println("Поток-пользователь включил тумблер.");
                try {
                    sleep(pauseMillis);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static class Game extends Thread{
        @Override
        public void run() {
            while (true) {
                if (switcher) {
                    System.out.println("Поток-игрушка выключил тумблер.");
                    switcher = false;
                }
                try {
                    sleep(500); //нужно чтобы программа распознала изменение переменной
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        User u = new User();
        Game g  = new Game();
        u.start();
        g.start();
        u.join();
        g.stop();
    }
}
