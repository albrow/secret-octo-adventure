import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Image;


/**
 * Represents an image that bounces around within the canvas.
 * 
 * Only the following formats are supported: png, jpg, gif.
 *
 * @author Robert C. Duvall
 */
public class Bouncer
{
    // state
    private Point myCenter;
    private Point myVelocity;
    private Dimension mySize;
    private Image myImage;


    /**
     * Construct a shape at the given position, with the given velocity, 
     * size, and color.
     */
    public Bouncer (Point center,
    		        Dimension size,
    		        Point velocity,
                    Image image)
    {
        setCenter(center.x, center.y);
        setSize(size.width, size.height);
        setVelocity(velocity.x, velocity.y);
        setImage(image);
    }


    /**
     * Describes how to "animate" the shape by changing its state.
     *
     * Currently, moves by the current velocity.
     */
    public void update (Game game)
    {
    	getCenter().translate(getVelocity().x, getVelocity().y);
        // bounce off the walls
        Dimension bounds = game.getSize();
        int radius = mySize.width / 2;
        if (getCenter().x < radius)
        {
            getCenter().x = bounds.width - radius - 1;
            // uncomment to experience chaos :)
            //game.getRandomSound().play();
        }
        else if (getCenter().x > bounds.width - radius)
        {
            getCenter().x = 0 + radius + 1;
        }
        radius = mySize.height / 2;
        if (getCenter().y < radius)
        {
            getCenter().y = bounds.height + radius + 1;
        }
        else if (getCenter().y > bounds.height - radius)
        {
            getCenter().y = 0 + radius + 1;
        }
    }


    /**
     * Describes how to draw the shape on the screen.
     */
    public void paint (Graphics pen)
    {
        pen.drawImage(myImage,
                      getLeft(), getTop(), 
                      getSize().width, getSize().height,
                      null);
    }


    /**
     * Returns shape's center.
     */
    public Point getCenter ()
    {
        return myCenter;
    }


    /**
     * Resets shape's center.
     */
    public void setCenter (int x, int y)
    {
        myCenter = new Point(x, y);
    }

    
    /**
     * Returns shape's left-most coordinate.
     */
    public int getLeft ()
    {
        return getCenter().x - getSize().width / 2;
    }

    
    /**
     * Returns shape's top-most coordinate.
     */
    public int getTop ()
    {
        return getCenter().y - getSize().height / 2;
    }


    /**
     * Returns shape's right-most coordinate.
     */
    public int getRight ()
    {
        return getCenter().x + getSize().width / 2;
    }


    /**
     * Reports shape's bottom-most coordinate.
     *
     * @return bottom-most coordinate
     */
    public int getBottom ()
    {
        return getCenter().y + getSize().height / 2;
    }
    

    /**
     * Returns shape's size.
     */
    public Dimension getSize ()
    {
        return mySize;
    }


    /**
     * Resets shape's size.
     */
    public void setSize (int width, int height)
    {
        mySize = new Dimension(width, height);
    }


    /**
     * Returns shape's velocity.
     */
    public Point getVelocity ()
    {
        return myVelocity;
    }


    /**
     * Resets shape's velocity.
     */
    public void setVelocity (int dx, int dy)
    {
        myVelocity = new Point(dx, dy);
    }

    
    /**
     * Get this shape's current image.
     */
    public Image getImage ()
    {
        return myImage;
    }


    /**
     * Set this shape's image.
     */
    public void setImage (Image image)
    {
        myImage = image;
    }


    /**
     * Returns true if the given point is within a rectangle representing this shape.
     */
    public boolean intersects (Point pt)
    {
        return (getLeft() <= pt.x && pt.x <= getRight() &&
                getTop() <= pt.y && pt.y <= getBottom());
    }
}
