package cs160.dataLayer;

import junit.framework.TestCase;

import org.junit.Test;

import java.util.Date;

public class TransactionTest extends TestCase {

    @Test
    public void testGetMerchantWithDefaultContructor(){
        // Given
        Transaction testedTransaction= new Transaction();

        // When
        String actualMerchant = testedTransaction.getMerchant();

        // Then
        assertNull(actualMerchant);
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
        // Given
        Transaction testedTransaction= new Transaction();

        // When
        Double expectAmount = 0.0;
        testedTransaction.setAmount(Double.MAX_VALUE);
        Double actualAmount = testedTransaction.getAmount();

        // Then
        assertEquals(expectAmount, actualAmount);
    }
}