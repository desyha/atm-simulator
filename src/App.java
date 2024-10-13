import java.util.Scanner;
import java.text.NumberFormat;

public class App {

    // Menu paling utama
    public static void menuMain(){
        System.out.println("\nATM KelFour");
        System.out.println("1. Informasi tabungan");
        System.out.println("2. Penarikan tunai ");
        System.out.println("3. Transfer kekayaan");
        System.out.println("4. Ganti PIN");
        System.out.println("5. Exit");
    }

    // Menu pilihan uang
    public static void menuUang(){
        System.out.println("PILIH JUMLAH");
        System.out.println("1. Rp. 100.000,00");
        System.out.println("2. Rp. 200.000,00");
        System.out.println("3. Rp. 500.000,00");
        System.out.println("4. Rp. 1.000.000,00");
        System.out.println("5. Cancel");
    }

    // Tempat validasi input dari menu pilihan uang
    public static double inputUang(Scanner scan, User user){
        double input = 0;
        do{
            menuUang();
            input = scan.nextInt();
            scan.nextLine();
            if(input>5 || input<1){
                System.out.println("Tolong input pilihan menu yang valid");
            }else if(input<5){
                checkPIN(scan, user);
                return (100000 + (Math.pow(input-1, 2)*100000));
            }
        }while(input!=5);
        return 0;
    }

    // Tempat memasukan input no rekening transfer
    // Validasi: No rek harus 8 digit
    public static String inputTransfer(Scanner scan){
        String transferID;
        do{
             System.out.println("Masukan nomor rekening tujuan");
             System.out.println("(8-digit number)");
             transferID = scan.nextLine();
         }while(transferID.length()!=8);   
         return transferID;     
    }

    // Tempat memasukan PIN
    // Datatype PIN berupa string agar awalnya dapat ada 0 pada awal
    // Validasi: 4 digit angka, numeric semua
    public static String inputPIN(Scanner scan){
        String pin;
        do{
            System.out.println("MASUKAN PIN [4-digit angka]");
            pin = scan.nextLine();
        }while(pin.length()!=4 || pin.matches("[0-9]+") == false);
        return pin;
    }

    // Tempat mengecek PIN sesuai atau tdk dgn yg ada di User
    public static void checkPIN(Scanner scan, User user){
        boolean check;
        String pin;
        do{
            pin = inputPIN(scan);
            check = pin.matches(user.getPin());
            if(check == false){
                System.out.println("Tolong input PIN yang benar");
            }
        }while(check == false);  
    }

    // Threading penarikan uang
    // Tempat mengeluarkan uang dan update balance uang user
    // Validasi: jika tabungan user lebih kecil dari pilihan uang, maka tidak berlanjut
    public static void penarikan(int uangJmlh, User user, NumberFormat formatter){
        if(checkBalance(uangJmlh, user)==true){
            new TransactionATM(uangJmlh, user);
            new MoneyOutput(uangJmlh);
        }  
    }

    // Threading transfer uang
    public static void pengiriman(int uangJmlh, User user, String transferID, NumberFormat formatter){
        if(checkBalance(uangJmlh, user)==true){
            new TransactionATM(uangJmlh, user);
            System.out.println("Sukses transfer "+ formatter.format(uangJmlh) +" ke nomor rekening " + transferID);
        }  
    }

    public static Boolean checkBalance(int uangJmlh, User user){
        if(user.getBalance() < uangJmlh){
            System.out.println("Balance to low");
            return false;
        }else{
            return true;
        }
    }

    public static void Main(){
        Scanner scan = new Scanner(System.in);
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        System.out.println("(hint: terserah berapa aja dan tolong diingat)");
        String tempPin = inputPIN(scan);
        User user = new User(tempPin);

        int inputMain = 0;
        int uangJmlh = 0;

        do{
            menuMain();
            inputMain = scan.nextInt();
            scan.nextLine();
            
            switch (inputMain) {
                case 1:
                    System.out.println("\nINFORMASI TABUNGAN");
                    System.out.println("Balance akun");
                    System.out.println(formatter.format(user.getBalance()));
                    break;
                case 2:
                    System.out.println("\nPENARIKAN TUNAI");
                    uangJmlh = (int)inputUang(scan, user);
                    penarikan(uangJmlh, user, formatter);
                    break;
                case 3:
                    System.out.println("\nTRANSFER KEKAYAAN");
                    String transferID = inputTransfer(scan);
                    uangJmlh = (int)inputUang(scan, user);
                    pengiriman(uangJmlh, user, transferID, formatter);
                    break;
                case 4:
                    System.out.println("\nGANTI PIN");
                    tempPin = inputPIN(scan);
                    user.setPin(tempPin);
                    System.out.println("Konfirmasi PIN baru");
                    checkPIN(scan, user);
                    System.out.println("PIN sukses diganti");
                    break;
                case 5:
                    System.out.println("\nExiting...");
                    break;
                default:
                    System.out.println("\nTolong input pilihan menu yang valid");
                    break;
            }
        }while(inputMain!=5);
        scan.close();
    }


    public static void main(String[] args) throws Exception {
        Main();
    }
}
