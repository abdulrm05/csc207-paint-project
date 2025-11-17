package ca.utoronto.utm.paint;

import ca.utoronto.utm.paint.model.PaintModel;
import ca.utoronto.utm.paint.view.View;
import javafx.application.Application;
import javafx.stage.Stage;

public class Paint extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		new View(new PaintModel(), stage);
	}
}
