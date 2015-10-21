package cs355.solution;

import cs355.GUIFunctions;
import cs355.controller.CS355Controller;
import cs355.controller.CS355ControllerImpl;
import cs355.model.view.AbstractViewModel;
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
		CS355Drawing model = new CS355DrawingImpl();

        AbstractViewModel viewModel = new ViewModel();
		ViewRefresher view = new ViewRefresherImpl(viewModel, model);
		CS355Controller controller = new CS355ControllerImpl(model, viewModel, initialColor);

		// Register view with model as observer
        viewModel.addObserver(view);
		model.addObserver(view);

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
