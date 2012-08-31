import java.awt.*;
import java.applet.AudioClip;
import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;


/**
 *  Represents your game, where your characters are created, animated, and rules applied.
 */
public class Game
{
    // make it easy to change features of the bouncers
    private static final int NUMBER_TO_CREATE = 5;
    private static final int MIN_SIZE = 16;
    private static final int MAX_SIZE = 48;
    private static final int MAX_VELOCITY = 10;

    // game state
    private Applet myContainer;
    private List<Bouncer> myBouncers;
    private List<Image> myImages;
    private List<AudioClip> mySounds;
    // random numbers
    private Random myGenerator = new Random();

 
    /**
     * Create a game of the given size with the given display for its shapes.
     */
    public Game (Applet container)
    {
    	myContainer = container;
        myBouncers = new ArrayList<Bouncer>();
        myGenerator = new Random();
        myImages = loadImages("/images/");
        mySounds = loadSounds("/sounds/");
        createBouncers(NUMBER_TO_CREATE, container.getSize());
    }


    /**
     * Update all characters in the game.
     */
    public void update ()
    {
        for (Bouncer b : myBouncers)
        {
            b.update(this);
        }
    }


    /**
     * Render all characters in the game.
     */
    public void paint (Graphics2D pen)
    {
        for (Bouncer b : myBouncers)
        {
            b.paint(pen);
        }
    }


    /** 
     * Returns size (in pixels) of the game area.
     */
	public Dimension getSize ()
	{
		return myContainer.getSize();
	}

	
    /**
     * Returns a random value between min and max, inclusive
     *
     * @param min minimum possible value
     * @param max maximum possible value
     */
    public int nextIntInRange (int min, int max)
    {
        return myGenerator.nextInt(max - min + 1) + min;
    }
    
    /**
     * Returns a random value between min and max, inclusive
     * that is guaranteed not to be zero
     *
     * @param min minimum possible value
     * @param max maximum possible value
     */
    public int nextNonZeroIntInRange (int min, int max)
    {
        int result = 0;
        while (result == 0)
        {
            result = nextIntInRange(min, max);
        }
        return result;
    }


    /**
     * Returns a random image from those in the images directory.
     */
    public Image getRandomImage ()
    {
        return myImages.get(myGenerator.nextInt(myImages.size()));
    }


    /**
     * Returns a random sound from those in the sounds directory.
     */
    public AudioClip getRandomSound ()
    {
        return mySounds.get(myGenerator.nextInt(mySounds.size()));
    }

    
    /**
     * Creates given number of bouncers within the given bounds.
     *
     * @param number of BouncingBalls to create
     * @param bounds within which BouncingBalls should appear
     */
    private void createBouncers(int number, Dimension bounds)
    {
        for (int k = 0; k < number; k++)
        {
            int size = nextIntInRange(MIN_SIZE, MAX_SIZE);
            myBouncers.add(
                new Bouncer(
                    new Point(nextIntInRange(size / 2, bounds.width - size / 2),
                              nextIntInRange(size / 2, bounds.height - size / 2)),
                    new Dimension(size, size),
                    new Point(nextNonZeroIntInRange(-MAX_VELOCITY, MAX_VELOCITY),
                              nextNonZeroIntInRange(-MAX_VELOCITY, MAX_VELOCITY)),
                    getRandomImage()));
        }
    }

    
	/**
     * Returns all images in the given directory
     * 
     * The given directory should be given relative to the source code,
     * this function will find its absolute location.
     */
    private List<AudioClip> loadSounds (String directory) 
    {
        try
        {
            URL path = getClass().getResource(directory);
            List<AudioClip> results = new ArrayList<AudioClip>();
            for (String file : new File(path.toURI()).list())
            {
                results.add(myContainer.getAudioClip(path, file));
            }
            return results;
        }
        catch (Exception e)
        {
            // should not happen
            return new ArrayList<AudioClip>(); 
        }
    }


    /**
     * Returns all images in the given directory
     * 
     * The given directory should be given relative to the source code,
     * this function will find its absolute location.
     */
	private List<Image> loadImages (String directory)
    {
        try
        {
            URL path = getClass().getResource(directory);
            List<Image> results = new ArrayList<Image>();
            for (String file : new File(path.toURI()).list())
            {
                results.add(myContainer.getImage(path, file));
            }
            return results;
        }
        catch (Exception e)
        {
            // should not happen
            return new ArrayList<Image>(); 
        }
    }
}
