package ca.utoronto.utm.paint.view;

import ca.utoronto.utm.paint.controller.MenuBarController;
import ca.utoronto.utm.paint.model.PaintModel;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class View {

	private final PaintModel paintModel;
	private final PaintPanel paintPanel;
	private final ShapeChooserPanel shapeChooserPanel;
	private final Stage stage;

	public View(PaintModel model, Stage stage) {
		this.stage = stage;
		this.paintModel = model;
        this.paintPanel = new PaintPanel(this.paintModel);
        this.shapeChooserPanel = new ShapeChooserPanel(this);

        BorderPane root = new BorderPane();
        root.setTop(createMenuBar());
        root.setCenter(this.paintPanel);
        root.setLeft(this.shapeChooserPanel);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Paint");
        stage.show();
	}

    public Stage getStage(){ return this.stage; }
	public PaintModel getPaintModel() {
		return this.paintModel;
	}
	public PaintPanel getPaintPanel() {
		return paintPanel;
	}
	public ShapeChooserPanel getShapeChooserPanel() {
		return shapeChooserPanel;
	}

	private MenuBar createMenuBar() {
        MenuBarController menuBarController = new MenuBarController(this);
		MenuBar menuBar = new MenuBar();
		Menu menu;
		MenuItem menuItem;

		// A menu for File

		menu = new Menu("File");

		menuItem = new MenuItem("New");
		menuItem.setOnAction(menuBarController);
		menu.getItems().add(menuItem);

		menuItem = new MenuItem("Open");
		menuItem.setOnAction(menuBarController);
		menu.getItems().add(menuItem);

		menuItem = new MenuItem("Save");
		menuItem.setOnAction(menuBarController);
		menu.getItems().add(menuItem);

		menu.getItems().add(new SeparatorMenuItem());

		menuItem = new MenuItem("Exit");
		menuItem.setOnAction(menuBarController);
		menu.getItems().add(menuItem);

		menuBar.getMenus().add(menu);

		return menuBar;
	}
}
