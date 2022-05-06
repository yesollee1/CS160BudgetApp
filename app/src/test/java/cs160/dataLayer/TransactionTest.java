package cs160.dataLayer;

import junit.framework.TestCase;

import org.junit.Test;

import java.util.Date;

public class TransactionTest extends TestCase {

    // Constructor Tests
    @Test
    public void testGetMerchantWithDefaultContructor(){
        // Given
        Transaction testedTransaction= new Transaction();

        // When
        String actualMerchant = testedTransaction.getMerchant();

        // Then
        assertNull( actualMerchant);
    }

    @Test
    public void testGetMerchantWithParameterizedContructor(){
        // Given
        String expectMerchant = "Test Title";
        String expectExpenseName = "Test Expense";
        Date expectDate = new Date();
        Transaction testedTransaction= new Transaction(expectMerchant, 0.0, expectExpenseName, expectDate);

        // When
        String actualMerchant = testedTransaction.getMerchant();

        // Then
        assertEquals(expectMerchant, actualMerchant);
    }

    // Date Tests
    @Test
    public void testSetDateForTransactionWithProvidedDate(){
        // Given
        Transaction testedTransaction= new Transaction();

        // When
        Date expectedDate = new Date();
        testedTransaction.setDate(expectedDate);
        Date actualDate = testedTransaction.getDate();

        // Then
        assertEquals(String.valueOf(expectedDate), String.valueOf(actualDate));
    }

    @Test
    public void testSetDateForTransactionWithNullDate(){
        // Given
        Transaction testedTransaction= new Transaction();

        // When
        testedTransaction.setDate(null);

        // Then
        assertNotNull(testedTransaction.getDate());
    }

    // Amount Tests
    @Test
    public void testSetAmountForTransactionWithPositiveAmount(){
        // Given
        Transaction testedTransaction= new Transaction();

        // When
        Double expectAmount = 100.0;
        testedTransaction.setAmount(expectAmount);
        Double actualAmount = testedTransaction.getAmount();

        // Then
        assertEquals(expectAmount, actualAmount);
    }

    @Test
    public void testSetAmountForTransactionWithNegativeAmount(){
        // Given
        Transaction testedTransaction= new Transaction();

        // When
        Double expectAmount = 0.0;
        testedTransaction.setAmount(-100.00);
        Double actualAmount = testedTransaction.getAmount();

        // Then
        assertEquals(expectAmount, actualAmount);
    }

    @Test
    public void testSetAmountForTransactionWithNullAmount(){
        // Given
        Transaction testedTransaction= new Transaction();

        // When
        Double expectAmount = 0.0;
        testedTransaction.setAmount(null);
        Double actualAmount = testedTransaction.getAmount();

        // Then
        assertEquals(expectAmount, actualAmount);
    }

    @Test
    public void testSetAmountForTransactionWithUnderFlowAmount(){
        // Given
        Transaction testedTransaction= new Transaction();

        // When
        Double expectAmount = 0.0;
        testedTransaction.setAmount(Double.MIN_VALUE);
        Double actualAmount = testedTransaction.getAmount();

        // Then
        assertEquals(expectAmount, actualAmount);
    }

    @Test
    public void testSetAmountForTransactionWithOverFlowAmount(){
        // Given default Constructor
        Transaction testedTransaction= new Transaction();

        // When
        Double expectAmount = 0.0;
        testedTransaction.setAmount(Double.MAX_VALUE);
        Double actualAmount = testedTransaction.getAmount();

        // Then
        assertEquals(expectAmount, actualAmount);
    }

    @Test
    public void testAmountForTransactionWithPositiveAmount(){
        // Given Transaction(Title, Amount)
        String expectMerchant = "Test Title";
        String expectExpenseName = "Test Expense";
        Date expectDate = new Date();
        Double testedTransactionAmount = 100.00;
        Transaction testedTransaction= new Transaction(expectMerchant, testedTransactionAmount, expectExpenseName, expectDate);

        // When
        Double actualAmount = testedTransaction.getAmount();
        Double expectAmount = testedTransactionAmount ;
        // Then
        assertEquals(expectAmount, actualAmount);
    }

    @Test
    public void testAmountForTransactionWithNegativeAmount(){
        // Given Transaction(Title, Amount)
        String expectMerchant = "Test Title";
        String expectExpenseName = "Test Expense";
        Date expectDate = new Date();
        Double testedTransactionAmount = -100.00;
        Transaction testedTransaction= new Transaction(expectMerchant, testedTransactionAmount, expectExpenseName, expectDate);


        // When
        Double actualAmount = testedTransaction.getAmount();
        Double expectAmount = 0.0 ;

        // Then
        assertEquals(expectAmount, actualAmount);
    }

    @Test
    public void testAmountForTransactionWithNullAmount(){
        // Given Transaction(Title, Amount)
        String expectMerchant = "Test Title";
        String expectExpenseName = "Test Expense";
        Date expectDate = new Date();
        Double testedTransactionAmount = null;
        Transaction testedTransaction= new Transaction(expectMerchant, testedTransactionAmount, expectExpenseName, expectDate);

        // When
        Double actualAmount = testedTransaction.getAmount();
        Double expectAmount = 0.0 ;

        // Then
        assertEquals(expectAmount, actualAmount);
    }

    @Test
    public void testAmountForTransactionWithUnderFlowAmount(){
        // Given Transaction(Title, Amount)
        String expectMerchant = "Test Title";
        String expectExpenseName = "Test Expense";
        Date expectDate = new Date();
        Double testedTransactionAmount = Double.MIN_VALUE;
        Transaction testedTransaction= new Transaction(expectMerchant, testedTransactionAmount, expectExpenseName, expectDate);

        // When
        Double actualAmount = testedTransaction.getAmount();
        Double expectAmount = 0.0 ;

        // Then
        assertEquals(expectAmount, actualAmount);
    }

    @Test
    public void testAmountForTransactionWithOverFlowAmount(){
        // Given Transaction(Title, Amount)
        String expectMerchant = "Test Title";
        String expectExpenseName = "Test Expense";
        Date expectDate = new Date();
        Double testedTransactionAmount = Double.MAX_VALUE;
        Transaction testedTransaction= new Transaction(expectMerchant, testedTransactionAmount, expectExpenseName, expectDate);

        // When
        Double actualAmount = testedTransaction.getAmount();
        Double expectAmount = 0.0 ;

        // Then
        assertEquals(expectAmount, actualAmount);
    }

    // Merchant Test
    @Test
    public void testGetMerchantWithDefaultConstructor(){
        // Given
        Transaction testedTransaction = new Transaction();

        // When
        String expectedMerchant = "Untitled Transaction";
        String actualMerchant = testedTransaction.getMerchant();

        // Then
        assertNull( actualMerchant);
    }

    @Test
    public void testSetMerchantWithValidInput(){
        // Given
        Transaction testedTransaction = new Transaction();

        // When
        String expectedMerchant = "Test Merchant";
        testedTransaction.setMerchant(expectedMerchant);
        String actualMerchant = testedTransaction.getMerchant();

        // Then
        assertEquals(expectedMerchant, actualMerchant);
    }

    @Test
    public void testSetMerchantWithNull(){
        // Given
        Transaction testedTransaction = new Transaction();

        // When
        testedTransaction.setMerchant(null);
        String actualMerchant = testedTransaction.getMerchant();

        // Then
        assertNull(actualMerchant);
    }

    @Test
    public void testSetMerchantWithValidInput_(){
        // Given Transaction(Title, Amount)
        String expectMerchant = "Test Title";
        String expectExpenseName = "Test Expense";
        Date expectDate = new Date();
        Double testedTransactionAmount = 100.0;
        Transaction testedTransaction= new Transaction(expectMerchant, testedTransactionAmount, expectExpenseName, expectDate);

        // When
        String expectedMerchant = "Test Merchant";
        testedTransaction.setMerchant(expectedMerchant);
        String actualMerchant = testedTransaction.getMerchant();

        // Then
        assertEquals(expectedMerchant, actualMerchant);
    }

    @Test
    public void testSetMerchantWithNull_(){
        // Given Transaction(Title, Amount)
        String expectMerchant = "Test Title";
        String expectExpenseName = "Test Expense";
        Date expectDate = new Date();
        Double testedTransactionAmount = 100.0;
        Transaction testedTransaction= new Transaction(expectMerchant, testedTransactionAmount, expectExpenseName, expectDate);

        // When
        String expectedMerchant = expectMerchant;
        testedTransaction.setMerchant(null);
        String actualMerchant = testedTransaction.getMerchant();

        // Then
        assertEquals(expectedMerchant, actualMerchant);
    }

    @Test
    public void testSetMerchantWithEmptyString_(){
        // Given
        String expectMerchant = "Test Title";
        String expectExpenseName = "Test Expense";
        Date expectDate = new Date();
        Double testedTransactionAmount = 100.0;
        Transaction testedTransaction= new Transaction(expectMerchant, testedTransactionAmount, expectExpenseName, expectDate);

        // When
        String expectedMerchant = expectMerchant;
        testedTransaction.setMerchant("");
        String actualMerchant = testedTransaction.getMerchant();

        // Then
        assertEquals(expectedMerchant, actualMerchant);
    }

    // ExpenseName Test
    @Test
    public void testGetExpenseNameWithDefaultConstructor(){
        // Given
        Transaction testedTransaction = new Transaction();

        // When
        String actualExpenseName = testedTransaction.getExpenseName();

        // Then
        assertNull(actualExpenseName);
    }

    @Test
    public void testSetExpenseNameWithValidInput(){
        // Given
        Transaction testedTransaction = new Transaction();

        // When
        String expectedExpenseName = "Test Expense Name";
        testedTransaction.setExpenseName(expectedExpenseName);
        String actualExpense = testedTransaction.getExpenseName();

        // Then
        assertEquals(expectedExpenseName, actualExpense);
    }

    @Test
    public void testSetExpenseNameWithNull(){
        // Given
        Transaction testedTransaction = new Transaction();

        // When
        String expectedExpenseName = "Test Expense Name";
        testedTransaction.setExpenseName(expectedExpenseName);
        testedTransaction.setExpenseName(null);
        String actualExpense = testedTransaction.getExpenseName();

        // Then
        assertEquals(expectedExpenseName, actualExpense);

    }

    @Test
    public void testSetExpenseNameWithEmptyString(){
        // Given
        Transaction testedTransaction = new Transaction();

        // When
        String expectedExpenseName = "Test Expense Name";
        testedTransaction.setExpenseName(expectedExpenseName);
        testedTransaction.setExpenseName("");
        String actualExpense = testedTransaction.getExpenseName();

        // Then
        assertEquals(expectedExpenseName, actualExpense);
    }

    // Note Test
    @Test
    public void testGetNoteWithDefaultConstructor(){
        // Given
        Transaction testedTransaction = new Transaction();

        // When
        String actualNotes = testedTransaction.getNotes();

        // Then
        assertNull(actualNotes);
    }

    @Test
    public void testSetNotesWithValidInput(){
        // Given
        Transaction testedTransaction = new Transaction();

        // When
        String expectedNotes = "Test Notes";
        testedTransaction.setNotes(expectedNotes);
        String actualNotes = testedTransaction.getNotes();

        // Then
        assertEquals(expectedNotes, actualNotes);
    }

    @Test
    public void testSetNoteWithNull(){
        // Given
        Transaction testedTransaction = new Transaction();

        // When
        String expectedNotes = "Test Notes";
        testedTransaction.setNotes(expectedNotes);
        testedTransaction.setNotes(null);
        String actualNotes = testedTransaction.getNotes();

        // Then
        assertEquals(expectedNotes, actualNotes);

    }

    @Test
    public void testSetNoteWithEmptyString(){
        // Given
        Transaction testedTransaction = new Transaction();

        // When
        String expectedNotes = "Test Notes";
        testedTransaction.setNotes(expectedNotes);
        testedTransaction.setNotes("");
        String actualNotes = testedTransaction.getNotes();

        // Then
        assertEquals(expectedNotes, actualNotes);
    }

    // Categorized tests
    @Test
    public void testIsCategorizedForTransaction(){
        // Given
        Transaction testedTransaction = new Transaction();

        // When


        // Then
        assertFalse(testedTransaction.isCategorized());
    }

    @Test
    public void testsetIsCategorizedForTransaction(){
        // Given
        Transaction testedTransaction = new Transaction();

        // When
        testedTransaction.setCategorized(true);

        // Then
        assertTrue(testedTransaction.isCategorized());
    }

}