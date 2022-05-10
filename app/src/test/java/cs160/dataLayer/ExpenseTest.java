package cs160.dataLayer;

import junit.framework.TestCase;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ExpenseTest extends TestCase {

    @Test
    public void testGetMerchants() {
        // Given
        List<String> expectedMerchants = new ArrayList<>();
        String testedMerchant = "Test Expense 1";
        expectedMerchants.add(testedMerchant);
        Expense testedExpense = new Expense();
        testedExpense.addMerchant(testedMerchant);

        // When
        List<String> actualMerchants = testedExpense.getMerchants();

        // Then

        assertEquals(expectedMerchants, actualMerchants);

    }

    @Test
    public void testAddMerchantWithValidInput() {
        // Given

        String testedMerchant = "Test Expense 1";
        Expense testedExpense = new Expense();
        testedExpense.addMerchant(testedMerchant);

        // When
        Integer numberOfMerchants = testedExpense.getMerchants().size();
        String actualMerchant = testedExpense.getMerchants().get(numberOfMerchants-1);

        // Then

        assertEquals(testedMerchant, actualMerchant);
    }

    @Test
    public void testAddMerchantWithNull() {
        // Given
        Expense testedExpense = new Expense();
        testedExpense.addMerchant(null);

        // When
        Integer numberOfMerchants = testedExpense.getMerchants().size();
        Integer expectedSize = 0;
        // Then

        assertEquals(expectedSize, numberOfMerchants);
    }

    @Test
    public void testAddMerchantWithEmptyString() {
        // Given
        Expense testedExpense = new Expense();
        testedExpense.addMerchant("");

        // When
        Integer numberOfMerchants = testedExpense.getMerchants().size();
        Integer expectedSize = 0;
        // Then

        assertEquals(expectedSize, numberOfMerchants);
    }

    @Test
    public void testContainsMerchant() {
        // Given
        String testedMerchant = "Test Expense 1";
        Expense testedExpense = new Expense();
        testedExpense.addMerchant(testedMerchant);

        // When
        Boolean contains = testedExpense.containsMerchant(testedMerchant);

        // Then
        assertTrue(contains);
    }

    @Test
    public void testSpendWithDefaultConstructor() {
        Expense testedExpense = new Expense();

        Boolean spent = testedExpense.spend(10.00);

        assertFalse(spent);
    }

    @Test
    public void testSpendWithValidInput() {
        String expectedTitle = "Test Title";
        Double expectedAmount = 100.00;
        Expense testedExpense = new Expense(expectedTitle, Frequency.MONTHLY, expectedAmount, expectedAmount);

        Boolean spent = testedExpense.spend(10.00);

        assertTrue(spent);
    }

    @Test
    public void testSpendWithNull() {
        String expectedTitle = "Test Title";
        Double expectedAmount = 100.00;
        Expense testedExpense = new Expense(expectedTitle, Frequency.MONTHLY, expectedAmount, expectedAmount);

        Boolean spent = testedExpense.spend(null);

        assertFalse(spent);
    }

    @Test
    public void testSpendWithOverflowInput() {
        String expectedTitle = "Test Title";
        Double expectedAmount = 100.00;
        Expense testedExpense = new Expense(expectedTitle, Frequency.MONTHLY, expectedAmount, expectedAmount);

        Boolean spent = testedExpense.spend(Double.MAX_VALUE);

        assertFalse(spent);
    }

    @Test
    public void testSpendWithUnderflowInput() {
        String expectedTitle = "Test Title";
        Double expectedAmount = 100.00;
        Expense testedExpense = new Expense(expectedTitle, Frequency.MONTHLY, expectedAmount, expectedAmount);

        Boolean spent = testedExpense.spend(Double.MIN_VALUE);

        assertFalse(spent);
    }

    @Test
    public void testSpendWithLargerInput() {
        String expectedTitle = "Test Title";
        Double expectedAmount = 100.00;
        Expense testedExpense = new Expense(expectedTitle, Frequency.MONTHLY, expectedAmount, expectedAmount);

        Boolean spent = testedExpense.spend(110.00);

        assertFalse(spent);
    }
}