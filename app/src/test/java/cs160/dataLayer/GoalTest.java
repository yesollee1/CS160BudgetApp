package cs160.dataLayer;

import junit.framework.TestCase;

import org.junit.Test;
import java.util.Date;

public class GoalTest extends TestCase {

    @Test
    public void testGoalCreationWithNoParameterConstructor() {
        // Given
        Goal testedGoal = new Goal();
        String actualTitle = testedGoal.getTitle();
        Frequency actualFrequency = testedGoal.getFrequency();
        Double actualProposedAmount = testedGoal.getProposedAmount();
        Double actualCurrentAmount = testedGoal.getCurrentAmount();

        // When
        String expectedTitle = null;
        Frequency expectedFrequency = Frequency.MONTHLY;
        Double expectedProposedAmount = 0.0;
        Double expectedCurrentAmount = 0.0;

        // Then
        assertEquals(expectedTitle, actualTitle);
        assertEquals(expectedFrequency, actualFrequency);
        assertEquals(expectedProposedAmount, actualProposedAmount);
        assertEquals(expectedCurrentAmount, actualCurrentAmount);
    }

    @Test
    public void testGoalCreationWithParametersConstructor() throws Exception{
        // Given
        Date testedDate = new Date();

        Goal testedGoal = new Goal(
                "Actual Goal Title",
                Frequency.MONTHLY,
                100.0,
                100.0,
                testedDate
        );
        String actualTitle = testedGoal.getTitle();
        Frequency actualFrequency = testedGoal.getFrequency();
        Double actualProposedAmount = testedGoal.getProposedAmount();
        Double actualCurrentAmount = testedGoal.getCurrentAmount();
        Date actualDate = testedGoal.getDate();


        // When
        String expectedTitle = "Actual Goal Title";
        Frequency expectedFrequency = Frequency.MONTHLY;
        Double expectedProposedAmount= 100.0;
        Double expectedCurrentAmount = 100.0;
        Date expectedDate = testedDate;

        // Then
        assertEquals(expectedTitle, actualTitle);
        assertEquals(expectedFrequency, actualFrequency);
        assertEquals(expectedProposedAmount, actualProposedAmount);
        assertEquals(expectedCurrentAmount, actualCurrentAmount);
        assertEquals(String.valueOf(expectedDate), String.valueOf(actualDate));
    }

    @Test
    public void testWhenGoalHasNullTitle() {
        // Given
        Goal goalWithNullTitle = new Goal();

        // When
        String testedGoalTitle = goalWithNullTitle.getTitle();

        // Then
        assertNull(testedGoalTitle);
    }

    @Test
    public void testWhenGoalHasEmptyTitle() {
        // Given
        Goal goalWithEmptyTitle = new Goal();

        // When
        goalWithEmptyTitle.setTitle("");

        // Then
        assertEquals(goalWithEmptyTitle.getTitle(), "");
    }

    @Test
    public void testWhenProposedGoalHasPositiveNumberInput() {
        // Given
        Goal testedGoal = new Goal();
        Double actualProposedAmount = 10.0;

        // When
        testedGoal.setProposedAmount(10.0);

        // Then
        assertEquals(testedGoal.getProposedAmount(), actualProposedAmount);
    }

    @Test
    public void testWhenProposedGoalHasNegativeNumberInput() {
        // Given
        Goal testedGoal = new Goal();
        Double actualProposedAmount = 0.0;

        // When
        testedGoal.setProposedAmount(-10.0);

        // Then
        assertEquals(testedGoal.getProposedAmount(), actualProposedAmount);
    }
}