package Infection;

import objectdraw.VisibleImage;

public class Twinkle extends Thread {
	private VisibleImage image;

	public Twinkle(VisibleImage image) {
		super();
		this.image = image;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		try {
			image.show();
			Thread.sleep(1000);
			image.hide();
			Thread.sleep(1000);
			image.show();
			Thread.sleep(1000);
			image.hide();
			Thread.sleep(1000);
			image.show();
			Thread.sleep(1000);
			image.hide();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
