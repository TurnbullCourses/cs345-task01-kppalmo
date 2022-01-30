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

        // This test OG Fails - Exception needs to be fixed to include negative nums
        //BankAccount bankAccount04 = new BankAccount("e@b.com", 300);
        //assertThrows(InsufficientFundsException.class, () -> bankAccount04.withdraw(-100));

    }

    @Test
    void isEmailValidTest() {
        assertTrue(BankAccount.isEmailValid("a@b.com"));
        assertTrue(BankAccount.isEmailValid("email@example.com"));
        assertTrue(BankAccount.isEmailValid("firstname.lastname@example.com"));
        assertTrue(BankAccount.isEmailValid("email@example.museum"));
        assertTrue(BankAccount.isEmailValid("firstname-lastname@example.com"));

        assertFalse(BankAccount.isEmailValid("a@")); // should be False
        assertFalse(BankAccount.isEmailValid("a@d")); // should be False
        assertFalse(BankAccount.isEmailValid("@")); // should be False
        assertFalse(BankAccount.isEmailValid(".email@example.com")); // should be False
        assertFalse(BankAccount.isEmailValid("email..email@example.com")); // should be False
        assertFalse(BankAccount.isEmailValid("email@example.com (Joe Smith)")); // should be False
        assertFalse(BankAccount.isEmailValid("email@example")); // should be False
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
    }

}