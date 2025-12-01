package ca.utoronto.utm.paint.persistence;

import ca.utoronto.utm.paint.model.*;
import javafx.scene.paint.Color;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Parse a file in Version 1.0 PaintSaveFile format. An instance of this class
 * understands the paint save file format, storing information about
 * its effort to parse a file. After a successful parse, an instance
 * will have an ArrayList of PaintCommand suitable for rendering.
 * If there is an error in the parse, the instance stores information
 * about the error. For more on the format of Version 1.0 of the paint 
 * save file format, see the associated documentation.
 * 
 * @author 
 *
 */
public class PaintFileParser {
	private int lineNumber = 0; // the current line being parsed
	private String errorMessage =""; // error encountered during parse
	private PaintModel paintModel;
	private boolean isParsed=false; // whether the parse succeeded
	/**
	 * Below are Patterns used in parsing 
	 */
	private Pattern pFileStart=Pattern.compile("^PaintSaveFileVersion1.0$");
	private Pattern pFileEnd=Pattern.compile("^EndPaintSaveFile$");

	private Pattern pCircleStart=Pattern.compile("^Circle$");
	private Pattern pCircleEnd=Pattern.compile("^EndCircle$");
    private Pattern pCenter = Pattern.compile("^center:\\((-?\\d+),(-?\\d+)\\)$");
    private Pattern pRadius = Pattern.compile("^radius:(\\d+)$");

    // Rectangle Patterns
    private Pattern pRectangleStart = Pattern.compile("^Rectangle$");
    private Pattern pRectangleEnd = Pattern.compile("^EndRectangle$");
    private Pattern pP1 = Pattern.compile("^p1:\\((-?\\d+),(-?\\d+)\\)$");
    private Pattern pP2 = Pattern.compile("^p2:\\((-?\\d+),(-?\\d+)\\)$");

    // Squiggle Patterns
    private Pattern pSquiggleStart = Pattern.compile("^Squiggle$");
    private Pattern pSquiggleEnd = Pattern.compile("^EndSquiggle$");

    // Polyline Patterns
    private Pattern pPolylineStart  = Pattern.compile("^Polyline$");
    private Pattern pPolylineEnd = Pattern.compile("^EndPolyline$");

    // Patterns for all shapes
    private Pattern pColor = Pattern.compile("^color:(\\d+),(\\d+),(\\d+)$");
    private Pattern pFilled = Pattern.compile("^filled:(true|false)$");
    private Pattern pPointStart = Pattern.compile("^points$");
    private Pattern pPointEnd = Pattern.compile("^endpoints$");
    private Pattern pPoint = Pattern.compile("^point:\\((-?\\d+),(-?\\d+)\\)$");

	/**
	 * Store an appropriate error message in this, including 
	 * lineNumber where the error occurred.
	 * @param mesg
	 */
	private void error(String mesg){
		this.errorMessage = "Error in line "+lineNumber+" "+mesg;
	}
	
	/**
	 * 
	 * @return the error message resulting from an unsuccessful parse
	 */
	public String getErrorMessage(){
		return this.errorMessage;
	}

    /**
     * @return the PaintModel associated with parsing the file
     */
    public PaintModel getPaintModel() { return this.paintModel; }

    /**
     * @return whether the file parsed successfully
     */
    public boolean isParsed(){
        return this.isParsed;
    }

    /**
	 * Parse the specified file
	 * @param fileName
	 * @return
	 */
	public boolean parse(String fileName){
		boolean retVal = false;
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(fileName));
			PaintModel pm = new PaintModel();
			retVal = this.parse(br, pm);
		} catch (FileNotFoundException e) {
			error("File Not Found: "+fileName);
		} finally {
			try { br.close(); } catch (Exception e){};
		}
        this.isParsed = retVal;
		return retVal;
	}


	/**
	 * Parse the inputStream as a Paint Save File Format file.
	 * The result of the parse is stored as an ArrayList of Paint command.
	 * If the parse was not successful, this.errorMessage is appropriately
	 * set, with a useful error message.
	 * 
	 * @param inputStream the open file to parse
	 * @param paintModel the paint model to add the commands to
	 * @return whether the complete file was successfully parsed
	 */
	public boolean parse(BufferedReader inputStream, PaintModel paintModel) {
		this.paintModel = paintModel;
		this.errorMessage="";
		
		// During the parse, we will be building one of the 
		// following commands. As we parse the file, we modify 
		// the appropriate command.
		
		CircleCommand circleCommand = null;
		RectangleCommand rectangleCommand = null;
		SquiggleCommand squiggleCommand = null;
        PolylineCommand polylineCommand = null;
	
		try {	
			int state=0; Matcher m; String l;
			
			this.lineNumber=0;
			while ((l = inputStream.readLine()) != null) {
				this.lineNumber++;
                // remove whitespace
                l = l.replaceAll("\\s+", "");

                if (!l.isEmpty()) {


                    System.out.println(lineNumber + " " + l + " " + state);
                    switch (state) {
                        case 0:
                            m = pFileStart.matcher(l);
                            if (m.matches()) {
                                state = 1;
                                break;
                            }
                            error("Expected Start of Paint Save File");
                            return false;

                        case 1: // Looking for the start of a new object or end of the save file
                            m = pCircleStart.matcher(l);
                            if (m.matches()) {
                                circleCommand = new CircleCommand(new Point(0, 0), 0);
                                state = 2; // move to expecting color
                                break;
                            }
                            m = pRectangleStart.matcher(l);
                            if (m.matches()) {
                                // temporary points
                                rectangleCommand = new RectangleCommand(new Point(0, 0), new Point(0, 0));
                                state = 10;
                                break;
                            }
                            m = pSquiggleStart.matcher(l);
                            if (m.matches()) {
                                squiggleCommand = new SquiggleCommand();
                                state = 20;
                                break;
                            }
                            m = pPolylineStart.matcher(l);
                            if (m.matches()) {
                                polylineCommand = new PolylineCommand();
                                state = 30;
                                break;
                            }
                            m = pFileEnd.matcher(l);
                            if (m.matches()) {
                                state = 100;
                                break;
                            }
                            error("Expected Start of Shape or End of Paint Save File");
                            return false;

                        case 2:
                            // Expect circle color
                            m = pColor.matcher(l);
                            if (m.matches()) {
                                int r = Integer.parseInt(m.group(1));
                                int g = Integer.parseInt(m.group(2));
                                int b = Integer.parseInt(m.group(3));
                                circleCommand.setColor(Color.rgb(r, g, b));
                                state = 3;
                                break;
                            }
                            error("Expected Circle color");
                            return false;
                        case 3:
                            // Expect Circle filled
                            m = pFilled.matcher(l);
                            if (m.matches()) {
                                boolean filled = Boolean.parseBoolean(m.group(1));
                                circleCommand.setFill(filled);
                                state = 4;
                                break;
                            }
                            error("Expected Circle filled");
                            return false;

                        case 4:
                            // Expect circle center
                            m = pCenter.matcher(l);
                            if (m.matches()) {
                                int x = Integer.parseInt(m.group(1));
                                int y = Integer.parseInt(m.group(2));
                                circleCommand.setCentre(new Point(x, y));
                                state = 5;
                                break;
                            }
                            error("Expected Circle centre");
                            return false;

                        case 5:
                            // Expect circle radius
                            m = pRadius.matcher(l);
                            if (m.matches()) {
                                int radius = Integer.parseInt(m.group(1));
                                circleCommand.setRadius(radius);
                                state = 6;
                                break;
                            }
                            error("Expected Circle radius");
                            return false;

                        case 6:
                            // Expect End Circle
                            m = pCircleEnd.matcher(l);
                            if (m.matches()) {
                                paintModel.addCommand(circleCommand);
                                circleCommand = null;
                                state = 1;
                                break;
                            }
                            error("Expected End Circle");
                            return false;

                        case 10:
                            // Expect rectangle color
                            m = pColor.matcher(l);
                            if (m.matches()) {
                                int r = Integer.parseInt(m.group(1));
                                int g = Integer.parseInt(m.group(2));
                                int b = Integer.parseInt(m.group(3));
                                rectangleCommand.setColor(Color.rgb(r, g, b));
                                state = 11;
                                break;
                            }
                            error("Expected Rectangle color");
                            return false;

                        case 11:
                            // Expect Rectangle filled
                            m = pFilled.matcher(l);
                            if (m.matches()) {
                                boolean filled = Boolean.parseBoolean(m.group(1));
                                rectangleCommand.setFill(filled);
                                state = 12;
                                break;
                            }
                            error("Expected Rectangle filled");
                            return false;

                        case 12:
                            // Expect Rectangle p1
                            m = pP1.matcher(l);
                            if (m.matches()) {
                                int x = Integer.parseInt(m.group(1));
                                int y = Integer.parseInt(m.group(2));
                                rectangleCommand.setP1(new Point(x, y));
                                state = 13;
                                break;
                            }
                            error("Expected Rectangle p1");
                            return false;

                        case 13:
                            m = pP2.matcher(l);
                            if (m.matches()) {
                                int x = Integer.parseInt(m.group(1));
                                int y = Integer.parseInt(m.group(2));
                                rectangleCommand.setP2(new Point(x, y));
                                state = 14;
                                break;
                            }
                            error("Expected Rectangle p2");
                            return false;

                        case 14:
                            // Expect End Rectangle
                            m = pRectangleEnd.matcher(l);
                            if (m.matches()) {
                                paintModel.addCommand(rectangleCommand);
                                rectangleCommand = null;
                                state = 1;
                                break;
                            }
                            error("Expected End Rectangle");
                            return false;

                        case 20:
                            // Expect squiggle color
                            m = pColor.matcher(l);
                            if (m.matches()) {
                                int r = Integer.parseInt(m.group(1));
                                int g = Integer.parseInt(m.group(2));
                                int b = Integer.parseInt(m.group(3));
                                squiggleCommand.setColor(Color.rgb(r, g, b));
                                state = 21;
                                break;
                            }
                            error("Expected Squiggle color");
                            return false;

                        case 21:
                            // Expect Squiggle filled
                            m = pFilled.matcher(l);
                            if (m.matches()) {
                                boolean filled = Boolean.parseBoolean(m.group(1));
                                squiggleCommand.setFill(filled);
                                state = 22;
                                break;
                            }
                            error("Expected Squiggle filled");
                            return false;

                        case 22:
                            // Expect squiggle points start
                            m = pPointStart.matcher(l);
                            if (m.matches()) {
                                state = 23;
                                break;
                            }
                            error("Expected Squiggle points");
                            return false;

                        case 23:
                            // Expect Squiggle points or end points
                            m = pPoint.matcher(l);
                            if (m.matches()) {
                                int x = Integer.parseInt(m.group(1));
                                int y = Integer.parseInt(m.group(2));
                                squiggleCommand.add(new Point(x, y));
                                state = 23; // can have multiple points
                                break;
                            }
                            m = pPointEnd.matcher(l);
                            if (m.matches()) {
                                state = 24;
                                break;
                            }
                            error("Expected Squiggle point or end points");
                            return false;

                        case 24:
                            m = pSquiggleEnd.matcher(l);
                            if (m.matches()) {
                                paintModel.addCommand(squiggleCommand);
                                squiggleCommand = null;
                                state = 1;
                                break;
                            }
                            error("Expected End Squiggle");
                            return false;

                        case 30:
                            // Expect polyline color
                            m = pColor.matcher(l);
                            if (m.matches()) {
                                int r = Integer.parseInt(m.group(1));
                                int g = Integer.parseInt(m.group(2));
                                int b = Integer.parseInt(m.group(3));
                                polylineCommand.setColor(Color.rgb(r, g, b));
                                state = 31;
                                break;
                            }
                            error("Expected Polyline color");
                            return false;

                        case 31:
                            // Expect polyline filled
                            m = pFilled.matcher(l);
                            if (m.matches()) {
                                boolean filled = Boolean.parseBoolean(m.group(1));
                                polylineCommand.setFill(filled);
                                state = 32;
                                break;
                            }
                            error("Expected Polyline filled");
                            return false;

                        case 32:
                            // Expect polyline points start
                            m = pPointStart.matcher(l);
                            if (m.matches()) {
                                state = 33;
                                break;
                            }
                            error("Expected Polyline points");
                            return false;

                        case 33:
                            // Expect polyline points or end points
                            m = pPoint.matcher(l);
                            if (m.matches()) {
                                int x = Integer.parseInt(m.group(1));
                                int y = Integer.parseInt(m.group(2));
                                polylineCommand.add(new Point(x, y));
                                state = 33; // can have multiple points here
                                break;
                            }
                            m = pPointEnd.matcher(l);
                            if (m.matches()) {
                                state = 34; // move to end polyline
                                break;
                            }
                            error("Expected Polyline point or end points");
                            return false;

                        case 34:
                            // Expect end polyline
                            m = pPolylineEnd.matcher(l);
                            if (m.matches()) {
                                paintModel.addCommand(polylineCommand);
                                polylineCommand = null;
                                state = 1;
                                break;
                            }
                            error("Expected End Polyline");
                            return false;

                        case 100:
                            // no more lines
                            error("Extra content after End of File");
                            return false;
                    }
                }
			}

            // check if we ended in correct state
            if (state != 100) {
                error("Unexpected end of file");
                return false;
            }

		}  catch (Exception e){
			error("Error reading file: " + e.getMessage());
            return false;
		}
		return true;
	}

    /**
     * Parse the specified RGB integer to JavaFX color.
     *
     * @param r
     * @param g
     * @param b
     * @return the associated javaFX color
     */
    private Color parseColor(int r, int g, int b) {
        return Color.rgb(r, g, b);
    }
}
