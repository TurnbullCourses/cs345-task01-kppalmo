package edu.ithaca.dturnbull.bank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {

    @Test
    void getBalanceTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals(200, bankAccount.getBalance(), 0.001);
    }

    @Test
    void withdrawTest() throws InsufficientFundsException {
        // Starter Tests
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(100);
        assertEquals(100, bankAccount.getBalance(), 0.001);
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(300));

        BankAccount bankAccount01 = new BankAccount("a@b.com", 500);
        assertThrows(InsufficientFundsException.class, () -> bankAccount01.withdraw(600));

        BankAccount bankAccount02 = new BankAccount("a@b.com", 600);
        bankAccount02.withdraw(300);
        assertEquals(300, bankAccount02.getBalance(), 0.001);
        bankAccount02.withdraw(300);
        assertEquals(0, bankAccount02.getBalance(), 0.001);

        BankAccount bankAccount03 = new BankAccount("e@b.com", 400);
        bankAccount03.withdraw(200.5);
        assertEquals(199.5, bankAccount03.getBalance(), 0.001);
        // Tests for Exxception
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(10.303));
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(-500.345));

    }

    @Test
    void isEmailValidTest() {
        assertTrue(BankAccount.isEmailValid("a@b.com")); // should be True
        assertTrue(BankAccount.isEmailValid("email@example.com")); // should be True
        assertTrue(BankAccount.isEmailValid("firstname.lastname@example.com")); // should be True
        assertTrue(BankAccount.isEmailValid("email@example.museum")); // should be True
        assertTrue(BankAccount.isEmailValid("firstname-lastname@example.com")); // should be True

        assertFalse(BankAccount.isEmailValid("a@")); // should be False - no .com domain name
        assertFalse(BankAccount.isEmailValid("a@d")); // should be False - no .com domain name
        assertFalse(BankAccount.isEmailValid("@")); // should be False - only @ symbol, no prefix or suffix
        assertFalse(BankAccount.isEmailValid(".email@example.com")); // should be False - cant start with a period
        assertFalse(BankAccount.isEmailValid("email..email@example.com")); // should be False - cant have ..
        assertFalse(BankAccount.isEmailValid("email@example.com (Joe Smith)")); // should be False - outside text
        assertFalse(BankAccount.isEmailValid("")); // empty string
    }

    @Test
    void getBalanceValidTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        assertEquals(200, bankAccount.getBalance(), 0.001);

        BankAccount bankAccount02 = new BankAccount("a@b.com", 0);
        assertEquals(0, bankAccount02.getBalance(), 0.001);

    }

    @Test
    void constructorTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals("a@b.com", bankAccount.getEmail());
        assertEquals(200, bankAccount.getBalance(), 0.001);
        // check for exception thrown correctly
        assertThrows(IllegalArgumentException.class, () -> new BankAccount("", 100));

        // Tests should throw b/c of inValid double amounts
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(10.303));
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(-50));
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(-10.452));

    }

    @Test
    void amountValidTest() { // comments for tests needed
        assertTrue(BankAccount.isAmountValid(10.9));
        assertTrue(BankAccount.isAmountValid(3.1));
        assertTrue(BankAccount.isAmountValid(3.33));
        assertTrue(BankAccount.isAmountValid(99.99));
        assertTrue(BankAccount.isAmountValid(500.40));

        assertFalse(BankAccount.isAmountValid(-10));
        assertFalse(BankAccount.isAmountValid(-700.7));
        assertFalse(BankAccount.isAmountValid(40.4556));
        assertFalse(BankAccount.isAmountValid(1.111));
        assertFalse(BankAccount.isAmountValid(-.42));
    }

    @Test
    void depositTest() throws InsufficientFundsException {
        BankAccount bankAccount = new BankAccount("a@b.com", 500);
        bankAccount.Deposit(100);
        assertEquals(600, bankAccount.getBalance(), 0.001);
        bankAccount.Deposit(600);
        assertEquals(1200, bankAccount.getBalance(), 0.001);
        bankAccount.Deposit(50.4);
        assertEquals(1250.4, bankAccount.getBalance(), 0.001);
        bankAccount.withdraw(.4);
        assertEquals(1250, bankAccount.getBalance(), 0.001);
        bankAccount.Deposit(400);
        assertEquals(1650, bankAccount.getBalance(), 0.001);
        // Error Cases
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(-10.452));
        assertThrows(InsufficientFundsException.class, () -> bankAccount.Deposit(-10.452));
        assertThrows(InsufficientFundsException.class, () -> bankAccount.Deposit(100000.00003));
        assertThrows(InsufficientFundsException.class, () -> bankAccount.Deposit(-45.566423));
    }

    @Test
    void transferTest() throws InsufficientFundsException {
        BankAccount bankAccount = new BankAccount("a@b.com", 500);
        BankAccount bankAccount02 = new BankAccount("b@a.net", 1000);
        assertEquals(500, bankAccount.getBalance(), 0.001);
        assertEquals(1000, bankAccount02.getBalance(), 0.001);
        bankAccount.Transfer(bankAccount02, 300);
        assertEquals(200, bankAccount.getBalance(), 0.001);
        assertEquals(1300, bankAccount02.getBalance(), 0.001);
        bankAccount02.Transfer(bankAccount, 400);
        assertEquals(600, bankAccount.getBalance(), 0.001);
        assertEquals(900, bankAccount02.getBalance(), 0.001);
        // Error cases
        assertThrows(InsufficientFundsException.class, () -> bankAccount.Transfer(bankAccount02, -45.566423));
        assertThrows(InsufficientFundsException.class, () -> bankAccount.Transfer(bankAccount02, -45.566423));
        assertThrows(InsufficientFundsException.class, () -> bankAccount.Transfer(bankAccount02, 40.523445));
        assertThrows(InsufficientFundsException.class, () -> bankAccount.Transfer(bankAccount02, -10));

    }

}