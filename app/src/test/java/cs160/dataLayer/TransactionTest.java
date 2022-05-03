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
        String expectedMerchant = "Untitled Transaction";
        String actualMerchant = testedTransaction.getMerchant();

        // Then
        assertEquals(expectedMerchant, actualMerchant);
    }

    @Test
    public void testGetMerchantWithParameterizedContructor(){
        // Given
        String expectMerchant = "Test Title";
        Transaction testedTransaction= new Transaction(expectMerchant, 0.0);

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
        String testedTransactionMerchant = "Tested Merchant";
        Double testedTransactionAmount = 100.0;
        Transaction testedTransaction= new Transaction(testedTransactionMerchant, testedTransactionAmount);

        // When
        Double actualAmount = testedTransaction.getAmount();
        Double expectAmount = testedTransactionAmount ;
        // Then
        assertEquals(expectAmount, actualAmount);
    }

    @Test
    public void testAmountForTransactionWithNegativeAmount(){
        // Given Transaction(Title, Amount)
        String testedTransactionMerchant = "Tested Merchant";
        Double testedTransactionAmount = -100.0;
        Transaction testedTransaction= new Transaction(testedTransactionMerchant, testedTransactionAmount);


        // When
        Double actualAmount = testedTransaction.getAmount();
        Double expectAmount = 0.0 ;

        // Then
        assertEquals(expectAmount, actualAmount);
    }

    @Test
    public void testAmountForTransactionWithNullAmount(){
        // Given Transaction(Title, Amount)
        String testedTransactionMerchant = "Tested Merchant";
        Double testedTransactionAmount = null;
        Transaction testedTransaction= new Transaction(testedTransactionMerchant, testedTransactionAmount);


        // When
        Double actualAmount = testedTransaction.getAmount();
        Double expectAmount = 0.0 ;

        // Then
        assertEquals(expectAmount, actualAmount);
    }

    @Test
    public void testAmountForTransactionWithUnderFlowAmount(){
        // Given Transaction(Title, Amount)
        String testedTransactionMerchant = "Tested Merchant";
        Double testedTransactionAmount = Double.MIN_VALUE;
        Transaction testedTransaction= new Transaction(testedTransactionMerchant, testedTransactionAmount);

        // When
        Double actualAmount = testedTransaction.getAmount();
        Double expectAmount = 0.0 ;

        // Then
        assertEquals(expectAmount, actualAmount);
    }

    @Test
    public void testAmountForTransactionWithOverFlowAmount(){
        // Given Transaction(Title, Amount)

        String testedTransactionMerchant = "Tested Merchant";
        Double testedTransactionAmount = Double.MAX_VALUE;
        Transaction testedTransaction= new Transaction(testedTransactionMerchant, testedTransactionAmount);

        // When
        Double actualAmount = testedTransaction.getAmount();
        Double expectAmount = 0.0 ;

        // Then
        assertEquals(expectAmount, actualAmount);
    }

    // Merchant Test
    @Test
    public void testGetMerchantForTransactionWithDefaultConstructor(){
        // Given
        Transaction testedTransaction = new Transaction();

        // When
        String expectedMerchant = "Untitled Transaction";
        String actualMerchant = testedTransaction.getMerchant();

        // Then
        assertEquals(expectedMerchant, actualMerchant);
    }

    @Test
    public void testSetMerchantForTransactionWithValidInput(){
        // Given
        Transaction testedTransaction = new Transaction();

        // When
        String expectedMerchant = "Test Merchant";
        testedTransaction.setMerchant(expectedMerchant);
        String actualMerchant = testedTransaction.getMerchant();

        // Then
        assertEquals(expectedMerchant, actualMerchant);
    }

    public void testSetMerchantForTransactionWithNull(){
        // Given
        Transaction testedTransaction = new Transaction();

        // When
        String expectedMerchant = "Untitled Transaction";
        testedTransaction.setMerchant(null);
        String actualMerchant = testedTransaction.getMerchant();

        // Then
        assertEquals(expectedMerchant, actualMerchant);
    }

    public void testSetMerchantForTransactionWithEmptyString(){
        // Given
        Transaction testedTransaction = new Transaction();

        // When
        String expectedMerchant = "Untitled Transaction";
        testedTransaction.setMerchant("");
        String actualMerchant = testedTransaction.getMerchant();

        // Then
        assertEquals(expectedMerchant, actualMerchant);
    }

    @Test
    public void testSetMerchantWithValidInput(){
        // Given Transaction(Title, Amount)
        String testedTransactionMerchant = "Untitled";
        Double testedTransactionAmount = 100.0;
        Transaction testedTransaction= new Transaction(testedTransactionMerchant, testedTransactionAmount);

        // When
        String expectedMerchant = "Test Merchant";
        testedTransaction.setMerchant(expectedMerchant);
        String actualMerchant = testedTransaction.getMerchant();

        // Then
        assertEquals(expectedMerchant, actualMerchant);
    }

    public void testSetMerchantWithNull(){
        // Given Transaction(Title, Amount)
        String testedTransactionMerchant = "Untitled";
        Double testedTransactionAmount = 100.0;
        Transaction testedTransaction= new Transaction(testedTransactionMerchant, testedTransactionAmount);

        // When
        String expectedMerchant = testedTransactionMerchant;
        testedTransaction.setMerchant(null);
        String actualMerchant = testedTransaction.getMerchant();

        // Then
        assertEquals(expectedMerchant, actualMerchant);
    }

    public void testSetMerchantWithEmptyString(){
        // Given
        String testedTransactionMerchant = "Untitled";
        Double testedTransactionAmount = 100.0;
        Transaction testedTransaction= new Transaction(testedTransactionMerchant, testedTransactionAmount);

        // When
        String expectedMerchant = testedTransactionMerchant;
        testedTransaction.setMerchant("");
        String actualMerchant = testedTransaction.getMerchant();

        // Then
        assertEquals(expectedMerchant, actualMerchant);
    }

}