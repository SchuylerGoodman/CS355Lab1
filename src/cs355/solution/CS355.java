package cs355.solution;

import cs355.GUIFunctions;
import cs355.controller.CS355Controller;
import cs355.controller.CS355ControllerImpl;
import cs355.model.image.*;
import cs355.model.image.Image;
import cs355.model.scene.IScene;
import cs355.model.scene.Scene;
import cs355.model.view.IViewModel;
import cs355.model.view.ViewModel;
import cs355.model.drawing.CS355Drawing;
import cs355.model.drawing.CS355DrawingImpl;
import cs355.view.ViewRefresher;
import cs355.view.ViewRefresherImpl;

import java.awt.*;

/**
 * This is the main class. The program starts here.
 * Make you add code below to initialize your model,
 * view, and controller and give them to the app.
 */
public class CS355 {

	/**
	 * This is where it starts.
	 * @param args = the command line arguments
	 */
	public static void main(String[] args) {

		// Initialize base color
		Color initialColor = Color.WHITE;

		// Construct model, view, and controller
		CS355Drawing drawing = new CS355DrawingImpl();
		IScene scene = new Scene();
		CS355Image image = new Image();

        IViewModel viewModel = new ViewModel(scene);
		ViewRefresher view = new ViewRefresherImpl(viewModel, drawing, scene, image);
		CS355Controller controller = new CS355ControllerImpl(drawing, viewModel, scene, image, initialColor);

		// Register view with model as observer
        viewModel.addObserver(view);
		drawing.addObserver(view);
		scene.addObserver(view);
		image.addObserver(view);

		// Initialize frame
		GUIFunctions.createCS355Frame(controller, view);

		// Set initial color in frame
		GUIFunctions.changeSelectedColor(initialColor);

		// Start first refresh
		viewModel.updateFrame();
		//GUIFunctions.setHScrollBarMax(2047);
		//GUIFunctions.setHScrollBarKnob(2047);
		GUIFunctions.refresh();
	}
}
