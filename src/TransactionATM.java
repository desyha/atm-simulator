
public class TransactionATM implements Runnable{
    private int uangNew;
    private Thread update;
    private User updateUser;

    public TransactionATM(int uangNew, User user){
        this.uangNew = uangNew;
        updateUser = user;  
        update = new Thread(this);
        update.start();  
    }

    @Override
    public void run() {
        // Thread
        // Mengurangi jumlah uang dari tabungan user
        updateUser.setBalance(updateUser.getBalance() - uangNew);
    }
    
}
