package ca.utoronto.utm.paint.view;

import ca.utoronto.utm.paint.model.*;
import javafx.scene.canvas.GraphicsContext;
import java.util.ArrayList;

public class DrawVisitor implements PaintCommandVisitor {
    private GraphicsContext g;

    public DrawVisitor(GraphicsContext g) {
        this.g = g;
    }

    @Override
    public void visit(CircleCommand circle) {
        int x = circle.getCentre().x;
        int y = circle.getCentre().y;
        int radius = circle.getRadius();
        if(circle.isFill()){
            g.setFill(circle.getColor());
            g.fillOval(x-radius, y-radius, 2*radius, 2*radius);
        } else {
            g.setStroke(circle.getColor());
            g.strokeOval(x-radius, y-radius, 2*radius, 2*radius);
        }
    }

    @Override
    public void visit(RectangleCommand rectangle) {
        Point topLeft = rectangle.getTopLeft();
        Point dimensions = rectangle.getDimensions();
        if(rectangle.isFill()){
            g.setFill(rectangle.getColor());
            g.fillRect(topLeft.x, topLeft.y, dimensions.x, dimensions.y);
        } else {
            g.setStroke(rectangle.getColor());
            g.strokeRect(topLeft.x, topLeft.y, dimensions.x, dimensions.y);
        }
    }

    @Override
    public void visit(SquiggleCommand squiggle) {
        ArrayList<Point> points = squiggle.getPoints();
        g.setStroke(squiggle.getColor());
        for(int i=0;i<points.size()-1;i++){
            Point p1 = points.get(i);
            Point p2 = points.get(i+1);
            g.strokeLine(p1.x, p1.y, p2.x, p2.y);
        }
    }

    @Override
    public void visit(PolylineCommand polyline) {
        ArrayList<Point> point = polyline.getPoints();
        g.setStroke(polyline.getColor());

        for (int i = 0; i < point.size() - 1; i++) {
            Point p1 = point.get(i);
            Point p2 = point.get(i + 1);
            g.strokeLine(p1.x, p1.y, p2.x, p2.y);
        }

        ArrayList<Point> points = polyline.getPoints();
        Point previewPoint = polyline.getPreviewPoint();

        if (previewPoint != null && !points.isEmpty()) {
            Point lastPoint = points.get(points.size() - 1);
            g.strokeLine(lastPoint.x, lastPoint.y, previewPoint.x, previewPoint.y);
        }
    }

    public String getSaveString() {return "";}
}
