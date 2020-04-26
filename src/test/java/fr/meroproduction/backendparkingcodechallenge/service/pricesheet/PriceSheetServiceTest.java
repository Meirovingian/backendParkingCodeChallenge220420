package fr.meroproduction.backendparkingcodechallenge.service.pricesheet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import fr.meroproduction.backendparkingcodechallenge.persistence.repository.pricesheet.PriceSheetRepository;
import fr.meroproduction.backendparkingcodechallenge.service.referential.duration.ReferentialDurationService;

@SpringBootTest
public class PriceSheetServiceTest {

    @InjectMocks
    private PriceSheetService priceSheetServiceToTest;

    @Mock
    private ReferentialDurationService neededMockedReferentialDurationService;

    @Mock
    private PriceSheetRepository neededMockedPriceSheetRepository;

    @Test
    public void testMakeCoefficient() {
	BigDecimal expected = new BigDecimal("1.03");
	BigDecimal actual = priceSheetServiceToTest.makeCoefficient(new BigDecimal(3L));
	assertNotNull(actual);
	assertEquals(expected, actual);
    }

    @Test
    public void test2MakeCoefficient() {
	BigDecimal expected = new BigDecimal("0.97");
	BigDecimal actual = priceSheetServiceToTest.makeCoefficient(new BigDecimal(-3L));
	assertNotNull(actual);
	assertEquals(expected, actual);
    }
}
