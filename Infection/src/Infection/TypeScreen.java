package Infection;

import objectdraw.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.util.ArrayList;

public class TypeScreen extends FilledRect {

	// instance variables
	private static final int WIDTH = 1280;
	private static final int HEIGHT = 800;
	private VisibleImage start2, infectious, lethal, resistant;
	private FramedRect enter, infoTitle, info;
	private Text prompt, enterTxt, title, info1, info2, spreadInfo, lethalInfo, resistInfo, resistInfo2;
	private ArrayList<DrawableInterface> window;

	public TypeScreen(Infection infection, DrawingCanvas canvas) {
		super(0, 0, 0, 0, canvas);

		window = new ArrayList<>();
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		MediaTracker tracker = new MediaTracker(infection);

		// load the image into the tracker
		Image image1 = toolkit.getImage("start2.jpeg");

		tracker.addImage(image1, 0);

		start2 = new VisibleImage(image1, 0, 0, canvas);

		start2.setSize(WIDTH, 664);

		infoTitle = new FramedRect(820, 100, 400, 100, canvas);
		infoTitle.setColor(Color.YELLOW);
		info = new FramedRect(820, 200, 400, 350, canvas);
		info.setColor(Color.YELLOW);

		prompt = new Text("Choose the type of your virus", 180, 130, canvas);
		prompt.setFontSize(40);
		prompt.setColor(Color.RED);

		title = new Text("Details about the feature", 850, 130, canvas);
		title.setFont(new Font("", Font.BOLD, 22));
		title.setColor(Color.RED);

		// add all the objects to the array list
		window.add(start2);

		window.add(infoTitle);
		window.add(info);
		window.add(prompt);
		window.add(title);

	}

	// show and hide methods
	public void hide() {
		for (int i = 0; i < window.size(); i++) {

			window.get(i).hide();

		}
	}

	public void show() {
		for (int i = 0; i < window.size(); i++) {

			window.get(i).show();

		}
	}

}
