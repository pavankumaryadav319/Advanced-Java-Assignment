package org.example;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class LoanServiceTest {
    LoanService service = new LoanService();
    @Test
    void testValidEligibility() {
        assertTrue(service.isEligible(30, 50000));
    }
    @Test
    void testInvalidAgeBelow() {
        assertFalse(service.isEligible(18, 50000));
    }
    @Test
    void testInvalidAgeAbove() {
        assertFalse(service.isEligible(65, 50000));
    }
    @Test
    void testInvalidSalary() {
        assertFalse(service.isEligible(30, 20000));
    }
    @Test
    void testCalculateEMI() {
        assertEquals(10000, service.calculateEMI(120000, 1));
    }
    @Test
    void testInvalidLoanAmount() {
        assertThrows(IllegalArgumentException.class,
                () -> service.calculateEMI(0, 1));
    }
    @Test
    void testInvalidTenure() {
        assertThrows(IllegalArgumentException.class,
                () -> service.calculateEMI(10000, 0));
    }
    @Test
    void testPremiumCategory() {
        assertEquals("Premium", service.getLoanCategory(800));
    }
    @Test
    void testStandardCategory() {
        assertEquals("Standard", service.getLoanCategory(700));
    }
    @Test
    void testHighRiskCategory() {
        assertEquals("High Risk", service.getLoanCategory(500));
    }
    @Test
    void testBoundaryValues() {
        assertAll(
                () -> assertTrue(service.isEligible(21, 25000)),
                () -> assertTrue(service.isEligible(60, 30000)),
                () -> assertEquals("Standard", service.getLoanCategory(600)),
                () -> assertEquals("Premium", service.getLoanCategory(750))
        );
    }
    @Test
    void testNotNullCategory() {
        assertNotNull(service.getLoanCategory(650));
    }
}
