public class MoneyOutput implements Runnable{
    private int uangNew;
    private Thread t;

    public MoneyOutput(int uang){
        uangNew = uang/100000;
        t = new Thread(this);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            System.out.println("Somethings wrong");
        }
    }

    @Override
    public void run() {
        // Thread
        // Mengeluarkan uang sesuai permintaan user
        System.out.println("Silakan ambil tunai pada kolom bawah");
        try{
            for(int i=0; i<uangNew*2; i++){
                System.out.println("Rp. 50.000,00");
                Thread.sleep(500);
            }
        }catch(InterruptedException e){
            System.out.println("Somethings wrong");
        }
    }
    
}
