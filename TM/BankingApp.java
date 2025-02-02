import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class BankAccount {
    private double balance;
    private final Lock lock = new ReentrantLock();

    public BankAccount(double balance) {
        this.balance = balance;
    }

    public void deposit(double amount) {
        lock.lock();
        try {
            balance += amount;
            System.out.println("Deposited " + amount + ", New Balance: " + balance);
        } finally {
            lock.unlock();
        }
    }

    public void withdraw(double amount) {
        lock.lock();
        try {
            if (balance >= amount) {
                balance -= amount;
                System.out.println("Withdrew " + amount + ", New Balance: " + balance);
            } else {
                System.out.println("Insufficient funds");
            }
        } finally {
            lock.unlock();
        }
    }
}

class BankTransaction implements Runnable {
    private BankAccount account;
    private boolean deposit;
    private double amount;
    private int times;

    public BankTransaction(BankAccount account, boolean deposit, double amount, int times) {
        this.account = account;
        this.deposit = deposit;
        this.amount = amount;
        this.times = times;
    }

    @Override
    public void run() {
        for (int i = 0; i < times; i++) {
            if (deposit) {
                account.deposit(amount);
            } else {
                account.withdraw(amount);
            }
        }
    }
}

public class BankingApp {
    public static void main(String[] args) {
        BankAccount account = new BankAccount(100);
        
        Thread t1 = new Thread(new BankTransaction(account, true, 50, 5));
        Thread t2 = new Thread(new BankTransaction(account, false, 30, 5));
        Thread t3 = new Thread(new BankTransaction(account, false, 70, 5));

        t1.start();
        t2.start();
        t3.start();

        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Final Balance: " + account.balance);
    }
}
