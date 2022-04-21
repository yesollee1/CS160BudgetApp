package cs160.dataLayer;

import junit.framework.TestCase;

import org.junit.Test;
import java.util.Date;

public class GoalTest extends TestCase {

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

    @Test
    public void testWhenProposedGoalHasNullInput() {
        // Given
        Goal testedGoal = new Goal();
        Double actualProposedAmount = 0.0;

        // When
        testedGoal.setProposedAmount(null);

        // Then
        assertEquals(testedGoal.getProposedAmount(), actualProposedAmount);
    }

    @Test
    public void testWhenProposedGoalHasUnderFlowNumberInput() {
        // Given
        Goal testedGoal = new Goal();
        Double actualProposedAmount = 0.0;

        // When
        testedGoal.setProposedAmount(Double.MIN_VALUE);

        // Then
        assertEquals(testedGoal.getProposedAmount(), actualProposedAmount);
    }

    @Test
    public void testWhenProposedGoalHasOverFlowNumberInput() {
        // Given
        Goal testedGoal = new Goal();
        Double actualProposedAmount = 0.0;

        // When
        testedGoal.setProposedAmount(Double.MAX_VALUE);

        // Then
        assertEquals(testedGoal.getProposedAmount(), actualProposedAmount);
    }

    @Test
    public void testGetDateForGoalWithDateInitialized(){
        // Given
        Date expectedDate = new Date();
        Goal testedGoal = new Goal(
                "Actual Goal Title",
                Frequency.MONTHLY,
                100.0,
                100.0,
                expectedDate
        );

        // When
        Date actualDate = testedGoal.getDate();

        // Then
        assertEquals(String.valueOf(expectedDate), String.valueOf(actualDate));
    }

    @Test
    public void testGetDateForGoalWithNullDate(){
        // Goal extends Category and Date is created in Category
        // Given
        Goal testedGoal = new Goal(
                "Actual Goal Title",
                Frequency.MONTHLY,
                100.0,
                100.0,
            null
        );

        // When
        Date actualDate = testedGoal.getDate();

        // Then
        assertNotNull(actualDate);
    }

    @Test
    public void testSetDateForGoalWithProvidedDate(){
        // Given
        Goal testedGoal = new Goal();

        // When
        Date expectedDate = new Date();
        testedGoal.setDate(expectedDate);
        Date actualDate = testedGoal.getDate();

        // Then
        assertEquals(String.valueOf(expectedDate), String.valueOf(actualDate));
    }

    @Test
    public void testSetDateForGoalWithNullDate(){
        // Given
        Goal testedGoal = new Goal();

        // When

        testedGoal.setDate(null);

        // Then
        assertNotNull(testedGoal.getDate());
    }

}