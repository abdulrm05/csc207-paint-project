package ca.utoronto.utm.paint.model;

public interface PaintCommandVisitor {
    void visit(CircleCommand circle);
    void visit(RectangleCommand rectangle);
    void visit(SquiggleCommand squiggle);
    void visit(PolylineCommand polyline);
}
