package nl.avans.sagrada.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PublicObjectiveCard {
    private int id;
    private String name;
    private String description;
    private int points;
    private String imagePath;

    public PublicObjectiveCard() {
    }

    /**
     * Full constructor
     *
     * @param id int
     * @param name String
     * @param description String
     * @param points int
     */
    public PublicObjectiveCard(int id, String name, String description, int points) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.points = points;
    }

    /**
     * Returns public objective card id
     *
     * @return id (int)
     */
    public int getId() {
        return id;
    }

    /**
     * Sets public objective card id
     *
     * @param id int
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return The name of this public objective card.
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name of this public objective card.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the public objective card description
     *
     * @return description (String)
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the public objective card description
     *
     * @param description String
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns public objective card points
     *
     * @return points (int)
     */
    public int getPoints() {
        return points;
    }

    /**
     * Sets the public objective card points
     *
     * @param points int
     */
    public void setPoints(int points) {
        this.points = points;
    }

    /**
     * Returns the public objective card image path
     *
     * @return image path (String)
     */
    public String getImagePath() {
        int[] ids = new int[10];
        for (int index = 0; index < ids.length; index++) {
            if (getId() == (index + 1)) {
                setImagePath("/images/publicObjectiveCardImages/publicObjectiveCard" + (index + 1)
                        + ".png");
            }
        }
        return imagePath;
    }

    /**
     * Sets the public objective card image path
     *
     * @param imagePath String
     */
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    /**
     * Calculate score for a pattern card using this public objective card.
     *
     * @param patternCard The patterncard.
     * @return The score.
     */
    public int calculateScore(PatternCard patternCard) {
        switch (id) {
            case 2:
                return calculatePairShades(patternCard, 3, 4, points);
            case 5:
                return calculatePairShades(patternCard, 5, 6, points);
            case 9:
                return calculatePairShades(patternCard, 1, 2, points);
            case 1:
                return calculateShadeVariety(patternCard, points);
            case 10:
                return calculateRowShadeVariety(patternCard, points);
            case 7:
                return calculateRowColorVariety(patternCard, points);
            case 6:
                return calculateColorVariety(patternCard, points);
            case 3:
                return calculateColumnShadeVariety(patternCard, points);
            case 4:
                return calculateColumnColorVariety(patternCard, points);
            case 8:
                return calculateColorDiagonals(patternCard, points);
            default:
                return 0;
        }
    }

    /**
     * Calculate score for a patterncard by val1 and val2.
     *
     * @param patternCard The patterncard.
     * @param val1 Set of values.
     * @param val2 Set of values.
     * @param rewardScore The score for each set.
     * @return The score.
     */
    private int calculatePairShades(PatternCard patternCard, int val1, int val2, int rewardScore) {
        int score = 0;

        // Add the values of val1 and val2 in patterncard to valueList
        ArrayList<Integer> valueList = new ArrayList<>();
        for (int x = 1; x <= PatternCard.CARD_SQUARES_WIDTH; x++) { // Loop through x-pos
            for (int y = 1; y <= PatternCard.CARD_SQUARES_HEIGHT; y++) { // Loop through y-pos
                if (patternCard.getPatternCardField(x, y)
                        .hasDie()) { // Check if there is a placed die
                    int value = patternCard.getPatternCardField(x, y).getDie()
                            .getEyes(); // Get the value from die
                    if (value == val1 || value == val2) { // Check if the value matches val1 or val2
                        valueList.add(value); // Add the value to valueList
                    }
                }
            }
        }

        // Add rewardScore for each pair of values
        outerloop:
        for (int x = 0; x < valueList.size(); ) {
            int value = valueList.get(x); // The first value in valueList
            for (int i = 1; i < valueList.size(); i++) {
                int value1 = valueList.get(i); // The second or higher value in valueList
                if (value == value1) { // If there is a pair of values
                    score += rewardScore; // Increase score
                    valueList.remove(Integer.valueOf(value)); // Remove value from valueList
                    valueList.remove(Integer.valueOf(value1)); // Remove value from valueList
                    continue outerloop; // Continue in outerloop using label (next first value)
                }
            }
            valueList.remove(Integer
                    .valueOf(value)); // If there is no pair for a value, remove from valueList
        }

        return score;
    }

    /**
     * Calculate the score for a patterncard for each set of 6 values.
     *
     * @param patternCard The patterncard.
     * @param rewardScore The score for each set.
     * @return The score.
     */
    private int calculateShadeVariety(PatternCard patternCard, int rewardScore) {
        int score = 0;

        // Add all values in patterncard to valueList
        ArrayList<Integer> valueList = new ArrayList<>();
        for (int x = 1; x <= PatternCard.CARD_SQUARES_WIDTH; x++) { // Loop through x-pos
            for (int y = 1; y <= PatternCard.CARD_SQUARES_HEIGHT; y++) { // Loop through y-pos
                if (patternCard.getPatternCardField(x, y)
                        .hasDie()) { // Check if there is a placed die
                    valueList.add(patternCard.getPatternCardField(x, y).getDie()
                            .getEyes()); // Add the value to valueList
                }
            }
        }

        // Add rewardScore for each set of 6 different values
        ArrayList<Integer> varietyList = new ArrayList<>();
        for (int x = 0; x < valueList.size(); ) {
            int firstValue = valueList.get(x); // The first value in valueList
            varietyList.add(firstValue); // Add the first value to varietyList

            for (int i = 1; i < valueList.size(); i++) {
                int nextValue = valueList.get(i); // The second or higher value in valueList
                if (varietyList.contains(nextValue)) {
                    continue; // Continue with next value in valueList when it already exists in varietyList
                }
                varietyList.add(nextValue); // Add the next value to varietyList
            }

            if (varietyList.contains(1) && varietyList.contains(2) && varietyList.contains(3)
                    && varietyList.contains(4) && varietyList.contains(5)
                    && varietyList.contains(6)) { // Check if there is a set of values
                score += rewardScore; // Increase score
                for (int removeValue : varietyList) { // Remove values from valueList
                    valueList.remove(Integer.valueOf(removeValue));
                }
                varietyList.clear(); // Clear varietyList for next iteration
            } else {
                break;
            }
        }

        return score;
    }

    /**
     * Calculate the score for a patterncard for each unique set of values in a row.
     *
     * @param patternCard The patterncard.
     * @param rewardScore The score for each set.
     * @return The score.
     */
    private int calculateRowShadeVariety(PatternCard patternCard, int rewardScore) {
        int score = 0;

        outerloop:
        for (int y = 1; y <= PatternCard.CARD_SQUARES_HEIGHT; y++) { // Loop through y-pos
            ArrayList<Integer> valueList = new ArrayList<>();
            for (int x = 1; x <= PatternCard.CARD_SQUARES_WIDTH; x++) { // Loop though x-pos
                if (patternCard.getPatternCardField(x, y)
                        .hasDie()) { // Check if there is a places die
                    valueList.add(patternCard.getPatternCardField(x, y).getDie()
                            .getEyes()); // Add the value to valueList
                }
            }

            ArrayList<Integer> varietyList = new ArrayList<>();
            for (int i = 0; i < valueList.size(); i++) { // Loop through all x-pos values
                int value = valueList.get(i); // Value for current x-pos
                if (varietyList.contains(value)) { // Check if varietyList already contains value
                    continue outerloop; // Continue in outerloop, next y-pos
                }
                varietyList.add(value); // Add value to varietyList
            }

            if (varietyList.size() == 5) { // Check if there are 5 unique values in varietyList
                score += rewardScore; // Increase score
            }
        }

        return score;
    }

    /**
     * Calculate the score for a patterncard for each unique set of colors in a row.
     *
     * @param patternCard The patterncard.
     * @param rewardScore The score for each set.
     * @return The score.
     */
    private int calculateRowColorVariety(PatternCard patternCard, int rewardScore) {
        int score = 0;

        outerloop:
        for (int y = 1; y <= PatternCard.CARD_SQUARES_HEIGHT; y++) { // Loop though y-pos
            ArrayList<String> colorList = new ArrayList<>();
            for (int x = 1; x <= PatternCard.CARD_SQUARES_WIDTH; x++) { // Loop though x-pos
                if (patternCard.getPatternCardField(x, y)
                        .hasDie()) { // Check if there is a placed die
                    colorList.add(patternCard.getPatternCardField(x, y).getDie()
                            .getColor()); // Add the color to colorList
                }
            }

            ArrayList<String> varietyList = new ArrayList<>();
            for (int i = 0; i < colorList.size(); i++) { // Loop though all x-pos colors
                String color = colorList.get(i); // Color for current x-pos
                if (varietyList.contains(color)) { // Check if varietyList already contains color
                    continue outerloop; // Continue in outerloop, next y-pos
                }
                varietyList.add(color); // Add color to varietyList
            }

            if (varietyList.size() == 5) { // Check if there are 5 unique colors in varietyList
                score += rewardScore; // Increase score
            }
        }

        return score;
    }

    /**
     * Calculate the score for a patterncard for each set of 5 colors.
     *
     * @param patternCard The patterncard.
     * @param rewardScore The score for each set.
     * @return The score.
     */
    private int calculateColorVariety(PatternCard patternCard, int rewardScore) {
        int score = 0;

        // Add all colors in patterncard to valueList
        ArrayList<String> colorList = new ArrayList<>();
        for (int x = 1; x <= PatternCard.CARD_SQUARES_WIDTH; x++) { // Loop through x-pos
            for (int y = 1; y <= PatternCard.CARD_SQUARES_HEIGHT; y++) { // Loop through y-pos
                if (patternCard.getPatternCardField(x, y)
                        .hasDie()) { // Check if there is a placed die
                    colorList.add(patternCard.getPatternCardField(x, y).getDie()
                            .getColor()); // Add the color to colorList
                }
            }
        }

        // Add rewardScore for each set of 5 different colors
        ArrayList<String> varietyList = new ArrayList<>();
        for (int x = 0; x < colorList.size(); ) {
            String firstColor = colorList.get(x); // The first color in valueList
            varietyList.add(firstColor); // Add the first color to varietyList

            for (int i = 1; i < colorList.size(); i++) {
                String nextColor = colorList.get(i); // The second or higher color in valueList
                if (varietyList.contains(nextColor)) {
                    continue; // Continue with next color in colorList when it already exists in varietyList
                }
                varietyList.add(nextColor); // Add the next color to varietyList
            }

            if (varietyList.contains("rood") && varietyList.contains("blauw") && varietyList
                    .contains("groen") && varietyList.contains("paars") && varietyList
                    .contains("geel")) { // Check if there is a set of colors
                score += rewardScore; // Increase score
                for (String removeColor : varietyList) { // Remove colors from valueList
                    colorList.remove(removeColor);
                }
                varietyList.clear(); // Clear varietyList for next iteration
            } else {
                break;
            }
        }

        return score;
    }

    /**
     * Calculate the score for a patterncard for each unique set of values in a column.
     *
     * @param patternCard The patterncard.
     * @param rewardScore The score for each set.
     * @return The score.
     */
    private int calculateColumnShadeVariety(PatternCard patternCard, int rewardScore) {
        int score = 0;

        outerloop:
        for (int x = 1; x <= PatternCard.CARD_SQUARES_WIDTH; x++) { // Loop though x-pos
            ArrayList<Integer> valueList = new ArrayList<>();
            for (int y = 1; y <= PatternCard.CARD_SQUARES_HEIGHT; y++) { // Loop though y-pos
                if (patternCard.getPatternCardField(x, y)
                        .hasDie()) { // Check if there is a placed die
                    valueList.add(patternCard.getPatternCardField(x, y).getDie()
                            .getEyes()); // Add the value to valueList
                }
            }

            ArrayList<Integer> varietyList = new ArrayList<>();
            for (int i = 0; i < valueList.size(); i++) { // Loop though all y-pos values
                int value = valueList.get(i); // Value for current y-pos
                if (varietyList.contains(value)) { // Check if varietyList already contains value
                    continue outerloop; // Continue in outerloop, next x-pos
                }
                varietyList.add(value); // Add value to varietyList
            }

            if (varietyList.size() == 4) { // Check if there are 4 unique values in varietyList
                score += rewardScore; // Increase score
            }
        }

        return score;
    }

    /**
     * Calculate the score for a patterncard for each unique set of colors in a column.
     *
     * @param patternCard The patterncard.
     * @param rewardScore The score for each set.
     * @return The score.
     */
    private int calculateColumnColorVariety(PatternCard patternCard, int rewardScore) {
        int score = 0;

        outerloop:
        for (int x = 1; x <= PatternCard.CARD_SQUARES_WIDTH; x++) { // Loop though x-pos
            ArrayList<String> colorList = new ArrayList<>();
            for (int y = 1; y <= PatternCard.CARD_SQUARES_HEIGHT; y++) { // Loop though y-pos
                if (patternCard.getPatternCardField(x, y)
                        .hasDie()) { // Check if there is a placed die
                    colorList.add(patternCard.getPatternCardField(x, y).getDie()
                            .getColor()); // Add the color to valueList
                }
            }

            ArrayList<String> varietyList = new ArrayList<>();
            for (int i = 0; i < colorList.size(); i++) { // Loop though all y-pos values
                String color = colorList.get(i); // Value for current y-pos
                if (varietyList.contains(color)) { // Check if varietyList already contains color
                    continue outerloop; // Continue in outerloop, next x-pos
                }
                varietyList.add(color); // Add color to varietyList
            }

            if (varietyList.size() == 4) { // Check if there are 4 unique colors in varietyList
                score += rewardScore; // Increase score
            }
        }

        return score;
    }

    /**
     * Calculate the score for each diagonally placed die with the same color.
     *
     * @param patternCard The patterncard.
     * @param rewardScore The score for each color.
     * @return The score.
     */
    private int calculateColorDiagonals(PatternCard patternCard, int rewardScore) {
        int score = 0;

        ArrayList<PatternCardField> blockedFields = new ArrayList<>(); // Fields to not check for anymore
        for (int y = 1; y <= PatternCard.CARD_SQUARES_HEIGHT; y++) { // Loop though x-pos
            for (int x = 1; x <= PatternCard.CARD_SQUARES_WIDTH; x++) { // Loop though y-pos
                if (blockedFields.contains(patternCard
                        .getPatternCardField(x, y))) { // Check if pattern card field is blocked
                    continue; // Continue to next position
                }
                if (patternCard.getPatternCardField(x, y)
                        .hasDie()) { // Check if the pattern card field has a die
                    PatternCardField firstPatternCardField = patternCard
                            .getPatternCardField(x, y); // Get the pattern card field
                    blockedFields
                            .add(firstPatternCardField); // Add the pattern card field to the blocked fields

                    ArrayList<PatternCardField> nextIteration = new ArrayList<>();
                    HashMap<PatternCardField, int[]> list = checkColorDiagonals(
                            firstPatternCardField); // Hashmap of diagonally placed same color die
                    for (Map.Entry<PatternCardField, int[]> entry : list
                            .entrySet()) { // Loop though each same color die
                        PatternCardField patternCardField = entry
                                .getKey(); // Get the apttern card field from hashmap
                        if (!blockedFields.contains(
                                patternCardField)) { // Check if pattern card field is blocked
                            blockedFields
                                    .add(patternCardField); // Add pattern card field to blocked fields
                            nextIteration
                                    .add(patternCardField); // Add pattern card field to next iteration
                        }
                    }

                    if (nextIteration.size() == 0) { // Check if next iteration is zero
                        blockedFields
                                .remove(firstPatternCardField); // Remove first pattern card field from blocked fields
                        continue; // Continue to next position
                    }

                    for (int i = 0; i < nextIteration.size(); ) { // NEXT ITERATIONS
                        PatternCardField patternCardField = nextIteration.get(i);
                        HashMap<PatternCardField, int[]> listNext = checkColorDiagonals(
                                patternCardField);
                        for (Map.Entry<PatternCardField, int[]> entryNext : listNext.entrySet()) {
                            PatternCardField patternCardFieldNext = entryNext.getKey();
                            if (!blockedFields.contains(patternCardFieldNext)) {
                                blockedFields.add(patternCardFieldNext);
                                nextIteration.add(patternCardFieldNext);
                            }
                        }
                        nextIteration.remove(patternCardField);
                    }
                }
            }
        }

        for (PatternCardField patternCardField : blockedFields) {
            score += rewardScore; // Increase score
        }

        return score;
    }

    /**
     * Check for a pattern card field diagonally placed die with the same color. Return a hash map
     * with the diagonally placed die and xPos/yPos location.
     *
     * @param patternCardField The patterncard field.
     * @return The hash map.
     */
    private HashMap<PatternCardField, int[]> checkColorDiagonals(
            PatternCardField patternCardField) {
        HashMap<PatternCardField, int[]> list = new HashMap<>();

        PatternCardField patternCardFieldNE = patternCardField
                .checkNorthEastColorDie(patternCardField.getDie().getColor());
        if (patternCardFieldNE != null) {
            int[] loc = {patternCardFieldNE.getxPos(), patternCardFieldNE.getyPos()};
            list.put(patternCardFieldNE, loc);
        }

        PatternCardField patternCardFieldSE = patternCardField
                .checkSouthEastColorDie(patternCardField.getDie().getColor());
        if (patternCardFieldSE != null) {
            int[] loc = {patternCardFieldSE.getxPos(), patternCardFieldSE.getyPos()};
            list.put(patternCardFieldSE, loc);
        }

        PatternCardField patternCardFieldSW = patternCardField
                .checkSouthWestColorDie(patternCardField.getDie().getColor());
        if (patternCardFieldSW != null) {
            int[] loc = {patternCardFieldSW.getxPos(), patternCardFieldSW.getyPos()};
            list.put(patternCardFieldSW, loc);
        }

        PatternCardField patternCardFieldNW = patternCardField
                .checkNorthWestColorDie(patternCardField.getDie().getColor());
        if (patternCardFieldNW != null) {
            int[] loc = {patternCardFieldNW.getxPos(), patternCardFieldNW.getyPos()};
            list.put(patternCardFieldNW, loc);
        }

        return list;
    }
}
