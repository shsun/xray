package com.aqs.pool;

import java.util.Date;

/**
 * Created by shsun on 1/14/18.
 */
public class XUTask implements Runnable {

    public final Date startDate;
    public final Date endDate;
    //
    public volatile int displayedTimes = 0;
    public final int FC = 3;

    public XUTask(Date pStartDate, Date pEndDate) {
        this.startDate = pStartDate;
        this.endDate = pEndDate;
    }

    @Override
    public void run() {
        displayedTimes += 1;
    }
}
