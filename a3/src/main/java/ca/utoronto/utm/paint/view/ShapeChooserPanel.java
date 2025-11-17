package ca.utoronto.utm.paint.view;

import ca.utoronto.utm.paint.controller.ShapeChooserPanelController;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class ShapeChooserPanel extends GridPane {
	public ShapeChooserPanel(View view) {
        ShapeChooserPanelController controller = new ShapeChooserPanelController(view);

		String[] buttonLabels = { "Circle", "Rectangle", "Squiggle" };

		int row = 0;
		for (String label : buttonLabels) {
			Button button = new Button(label);
			button.setMinWidth(100);
			this.add(button, 0, row);
			row++;
			button.setOnAction(controller);
		}
	}
}
