package ca.utoronto.utm.paint.persistence;

import ca.utoronto.utm.paint.model.*;

public class SaveVisitor implements PaintCommandVisitor {

    private String saveString = "";

    @Override
    public void visit(CircleCommand circle) {
        String s = "Circle\n";
        s += sharedProperties(circle);
        s += "\tcenter:(" + circle.getCentre().x + "," + circle.getCentre().y + ")\n";
        s += "\tradius:" + circle.getRadius() + "\n";
        s += "End Circle \n";
        saveString += s;
    }

    @Override
    public void visit(RectangleCommand rectangle) {
        String s = "Rectangle\n";
        s += sharedProperties(rectangle);
        s += "\tp1:(" + rectangle.getP1().x + "," + rectangle.getP1().y + ")\n";
        s += "\tp2:(" + rectangle.getP2().x + "," + rectangle.getP2().y + ")\n";
        s += "End Rectangle \n";
        saveString += s;
    }

    @Override
    public void visit(SquiggleCommand squiggle) {
        String s = "Squiggle\n";
        s += sharedProperties(squiggle);
        s += "\tpoints\n";

        for (Point p: squiggle.getPoints()) {
            s += "\t\tpoint:(" + p.x + "," + p.y + ")\n";
        }
        s += "\tend points\n";
        s += "End Squiggle\n";
        saveString += s;
    }

    @Override
    public void visit(PolylineCommand polyline) {
        String s = "Polyline\n";
        s += sharedProperties(polyline);
        s += "\tpoints\n";

        for (Point p: polyline.getPoints()) {
            s += "\t\tpoint:(" + p.x + "," + p.y + ")\n";
        }
        s += "\tend points\n";
        s += "End Polyline\n";
        saveString += s;
    }

    private String sharedProperties(PaintCommand cmd) {
        int r = (int) (cmd.getColor().getRed() * 255);
        int g = (int) (cmd.getColor().getGreen() * 255);
        int b = (int) (cmd.getColor().getBlue() * 255);
        String s = "\tcolor:" + r + "," + g + "," + b + "\n";
        s += "\tfilled:" + cmd.isFill() + "\n";
        return s;
    }

    @Override
    public String getSaveString() {
        return this.saveString;
    }
}
