
package Threads;

import DAO.CouponsDAO;
import DBDAO.CouponsDBDAO;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class CouponExpirationDailyJob implements Runnable {
    private CouponsDAO couponsDAO = new CouponsDBDAO();
    private boolean quit;

    /**
     * A default constructor for the creation of a "dailyJob" object at the
     * "Test" class.
     */
    public CouponExpirationDailyJob() {
    }

    /**
     * Runs a thread that deletes expired coupons once a day. As long a boolean
     * "quit" == false, the thread is active.
     */
    public void run() {
        while (!quit) {
            try {
                couponsDAO.deleteExpiredCoupons();
                TimeUnit.MINUTES.sleep(1);
            } catch (InterruptedException | SQLException err) {
                err.getCause();
            }
        }
    }

    /**
     * A method that stops the thread by making the boolean "quit" == true.
     * one second sleep time was added to give the daily job thread time
     * to do its job before we shut it down (see in Test class).
     *
     * @throws InterruptedException - The TimeUnit sleep thread was interrupted.
     */
    public void stop() throws InterruptedException {
        TimeUnit.SECONDS.sleep(1);
        quit = true;
    }
}
