package nl.avans.sagrada.model.toolcard;

import java.util.ArrayList;
import javafx.scene.input.MouseEvent;
import nl.avans.sagrada.model.FavorToken;
import nl.avans.sagrada.model.GameDie;
import nl.avans.sagrada.model.PatternCard;

public abstract class ToolCard {
    private int id;
    private int seqnr;
    private String description;
    private String imagePath;
    private String name;
    private ArrayList<FavorToken> favorTokens;
    private boolean hasBeenPaidForBefore;
    private boolean isDone;

    /**
     * Filled constructor.
     *
     * @param id int
     * @param seqnr int
     * @param description String
     */
    public ToolCard(int id, String name, int seqnr, String description) {
        this.id = id;
        this.seqnr = seqnr;
        this.description = description;
        this.name = name;
        favorTokens = new ArrayList<>();
        isDone = false;
    }

    public void useToolCard() {}

    /**
     * Returns the toolcard id.
     *
     * @return a toolcard id (int)
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the toolcard id.
     *
     * @param id int
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the toolcard sequence number.
     *
     * @return a toolcard sequence number (int)
     */
    public int getSeqnr() {
        return seqnr;
    }

    /**
     * Sets the sequence number for a toolcard
     *
     * @param seqnr int
     */
    public void setSeqnr(int seqnr) {
        this.seqnr = seqnr;
    }

    /**
     * Returns the toolcard description.
     *
     * @return a toolcard description (String)
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the toolcard description.
     *
     * @param description String
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the favortokens bound to the toolcard.
     *
     * @return favortokens bound to this toolcard (ArrayList type: FavorToken)
     */
    public ArrayList<FavorToken> getFavorTokens() {
        return favorTokens;
    }

    /**
     * Binds favortokens to the toolcard.
     *
     * @param favorTokens ArrayList type: FavorToken
     */
    public void setFavorTokens(ArrayList<FavorToken> favorTokens) {
        this.favorTokens = favorTokens;
    }

    /**
     * Returns the image path for the toolcard.
     *
     * @return an image path for this toolcard (String)
     */
    public String getImagePath() {
        int[] ids = new int[12];
        for (int index = 0; index < ids.length; index++) {
            if (getId() == (index + 1)) {
                setImagePath("/images/toolcardImages/toolcard" + (index + 1) + ".png");
            }
        }
        return imagePath;
    }

    /**
     * Sets the image path for the toolcard.
     *
     * @param imagePath String
     */
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    /**
     * Returns the name of the toolcard.
     *
     * @return the name for this toolcard
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name for the toolcard.
     *
     * @param name String
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the payment status for the toolcard. If true, this toolcard has already been paid for
     * before.
     * 
     * @return the payment status for the toolcard
     */
    public boolean hasBeenPaidForBefore() {
        return hasBeenPaidForBefore;
    }

    /**
     * Sets the payment status for the toolcard.
     * 
     * @param hasBeenPaidForBefore boolean
     */
    public void setHasBeenPaidForBefore(boolean hasBeenPaidForBefore) {
        this.hasBeenPaidForBefore = hasBeenPaidForBefore;
    }
    
    /**
     * Handels the drag event
     * So we can handle the toolcard ability
     * <p>
     * The event.getSource or event.getTaget contains the field that we are going to drop the die on
     * @param event
     * @param die
     * @return PatternCard
     */
    public abstract PatternCard handleDrag(MouseEvent event, GameDie die);

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }
}
