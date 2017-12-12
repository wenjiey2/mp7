package Infection;

public class Cure implements Runnable{
    
    // Instance Variables
	private int percent;
	String lock = "lockcure";
	private OnCureProcess onCureProcess;
	private boolean suspend = false;
	private int cureSleep;

	// Constructor
	public Cure() {
		percent = 0;
		cureSleep = 2600;
	}

	interface OnCureProcess {
		void cureProcess(int percent);
	}

	public void run() {
		while (true) {
			synchronized (lock) {
				try {
					Thread.sleep(cureSleep);
					if (suspend) {
						lock.wait();
					}
					percent += 1;
					if (onCureProcess != null) {
						onCureProcess.cureProcess(percent);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void setOnCureProcess(OnCureProcess o) {
		onCureProcess = o;
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

	public boolean suspended() {
		return suspend;
	}

	public int getPercent() {
		return percent;
	}

	public void addCureSleep() {
		cureSleep += 300;
	}
}
