package Infection;

import java.util.Calendar;

public class Dates extends Thread {

    // Instance Variable
    Calendar calendar;
    private int day = 0;
    private OnTimeChanged onTimeChanged;

    // Constructor
    public Dates(Calendar c) {
            calendar = c;
    }

    interface OnTimeChanged {
            void timeChanged(Calendar calendar);
    }

    String lock = "lock";
    private boolean suspend = false;

    // Override the run method of Calendar class
    @Override
    public void run() {
        while (true) {
            synchronized (lock) {
                try {
                    Thread.sleep(2000);
                        if(suspend){
                            lock.wait();
                        }
                        calendar.add(Calendar.DAY_OF_MONTH, 1);
                        day += 1;

                        onTimeChanged.timeChanged(calendar);
                } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
            }
        }
    }

    public void setOnTimeChanged(OnTimeChanged onTimeChanged) {
            this.onTimeChanged = onTimeChanged;
    }

    public int getDuration() {
            return day;
    }

    public void suspendDate() {
            suspend = true;
    }

    public void resumeDate() {
            synchronized (lock) {
                    lock.notifyAll();
            }
            suspend = false;
    }

    public boolean suspended(){
        return suspend;
    }
}

