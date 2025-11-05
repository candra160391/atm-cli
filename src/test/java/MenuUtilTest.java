import com.dkatalis.exercise.atm.provider.AuthenticationProvider;
import com.dkatalis.exercise.atm.provider.MessageProvider;
import com.dkatalis.exercise.atm.util.MenuUtil;

import com.dkatalis.exercise.atm.exception.InvalidCommandException;
import com.dkatalis.exercise.atm.exception.SessionLoginException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MenuUtilTest {

    private AuthenticationProvider authProvider;
    private MessageProvider messageProvider;

    @BeforeEach
    void setUp() {
        authProvider = mock(AuthenticationProvider.class);
        messageProvider = mock(MessageProvider.class);

        when(messageProvider.get(anyString())).thenAnswer(invocation ->invocation.getArgument(0));
    }

    @Test
    void shouldThrowInvalidCommandWhenLoginWithoutUsername() {
        String[] cmd = {"login"};
        InvalidCommandException ex = assertThrows(
                InvalidCommandException.class,
                () -> MenuUtil.validateUserInput(cmd, authProvider, messageProvider)
        );
        assertTrue(ex.getMessage().contains("error.invalid.login.command"));
    }

    @Test
    void shouldPassLoginWithUsername() {
        String[] cmd = {"login", "Alice"};
        assertDoesNotThrow(() ->
                MenuUtil.validateUserInput(cmd, authProvider, messageProvider)
        );
    }

    @Test
    void shouldThrowSessionExceptionIfNotLoggedInDeposit() {
        String[] cmd = {"deposit", "100"};
        when(authProvider.isAuthenticated()).thenReturn(false);

        SessionLoginException ex = assertThrows(
                SessionLoginException.class,
                () -> MenuUtil.validateUserInput(cmd, authProvider, messageProvider)
        );
        assertTrue(ex.getMessage().contains("error.force.access"));
    }

    @Test
    void shouldPassDepositWithValidAmount() {
        String[] cmd = {"deposit", "100"};
        when(authProvider.isAuthenticated()).thenReturn(true);

        assertDoesNotThrow(() ->
                MenuUtil.validateUserInput(cmd, authProvider, messageProvider)
        );
    }

    @Test
    void shouldThrowInvalidAmountForDeposit() {
        String[] cmd = {"deposit", "abc"};
        when(authProvider.isAuthenticated()).thenReturn(true);

        InvalidCommandException ex = assertThrows(
                InvalidCommandException.class,
                () -> MenuUtil.validateUserInput(cmd, authProvider, messageProvider)
        );
        assertTrue(ex.getMessage().contains("error.invalid.amount.format"));
    }

    @Test
    void shouldThrowSessionExceptionIfNotLoggedInWithdraw() {
        String[] cmd = {"withdraw", "100"};
        when(authProvider.isAuthenticated()).thenReturn(false);

        SessionLoginException ex = assertThrows(
                SessionLoginException.class,
                () -> MenuUtil.validateUserInput(cmd, authProvider, messageProvider)
        );
        assertTrue(ex.getMessage().contains("error.force.access"));
    }

    @Test
    void shouldPassWithdrawWithValidAmount() {
        String[] cmd = {"withdraw", "50"};
        when(authProvider.isAuthenticated()).thenReturn(true);

        assertDoesNotThrow(() ->
                MenuUtil.validateUserInput(cmd, authProvider, messageProvider)
        );
    }

    @Test
    void shouldThrowInvalidCommandForTransferMissingArgs() {
        String[] cmd = {"transfer", "Bob"};
        when(authProvider.isAuthenticated()).thenReturn(true);

        InvalidCommandException ex = assertThrows(
                InvalidCommandException.class,
                () -> MenuUtil.validateUserInput(cmd, authProvider, messageProvider)
        );
        assertTrue(ex.getMessage().contains("error.invalid.transfer.command"));
    }

    @Test
    void shouldThrowInvalidAmountForTransfer() {
        String[] cmd = {"transfer", "Bob", "abc"};
        when(authProvider.isAuthenticated()).thenReturn(true);

        InvalidCommandException ex = assertThrows(
                InvalidCommandException.class,
                () -> MenuUtil.validateUserInput(cmd, authProvider, messageProvider)
        );
        assertTrue(ex.getMessage().contains("error.invalid.amount.format"));
    }

    @Test
    void shouldPassTransferValid() {
        String[] cmd = {"transfer", "Bob", "100"};
        when(authProvider.isAuthenticated()).thenReturn(true);

        assertDoesNotThrow(() ->
                MenuUtil.validateUserInput(cmd, authProvider, messageProvider)
        );
    }

    @Test
    void shouldThrowSessionExceptionIfNotLoggedInBalance() {
        String[] cmd = {"balance"};
        when(authProvider.isAuthenticated()).thenReturn(false);

        SessionLoginException ex = assertThrows(
                SessionLoginException.class,
                () -> MenuUtil.validateUserInput(cmd, authProvider, messageProvider)
        );
        assertTrue(ex.getMessage().contains("error.force.access"));
    }

    @Test
    void shouldPassBalanceWhenLoggedIn() {
        String[] cmd = {"balance"};
        when(authProvider.isAuthenticated()).thenReturn(true);

        assertDoesNotThrow(() ->
                MenuUtil.validateUserInput(cmd, authProvider, messageProvider)
        );
    }

    @Test
    void shouldThrowSessionExceptionIfNotLoggedInLogout() {
        String[] cmd = {"logout"};
        when(authProvider.isAuthenticated()).thenReturn(false);

        SessionLoginException ex = assertThrows(
                SessionLoginException.class,
                () -> MenuUtil.validateUserInput(cmd, authProvider, messageProvider)
        );
        assertTrue(ex.getMessage().contains("error.force.access"));
    }

    @Test
    void shouldPassLogoutWhenLoggedIn() {
        String[] cmd = {"logout"};
        when(authProvider.isAuthenticated()).thenReturn(true);

        assertDoesNotThrow(() ->
                MenuUtil.validateUserInput(cmd, authProvider, messageProvider)
        );
    }

    @Test
    void shouldPassHelpCommand() {
        String[] cmd = {"help"};
        assertDoesNotThrow(() ->
                MenuUtil.validateUserInput(cmd, authProvider, messageProvider)
        );
    }

    @Test
    void shouldPassExitCommand() {
        String[] cmd = {"exit"};
        assertDoesNotThrow(() ->
                MenuUtil.validateUserInput(cmd, authProvider, messageProvider)
        );
    }

    @Test
    void shouldThrowUnknownCommand() {
        String[] cmd = {"unknown"};

        InvalidCommandException ex = assertThrows(
                InvalidCommandException.class,
                () -> MenuUtil.validateUserInput(cmd, authProvider, messageProvider)
        );
        assertTrue(ex.getMessage().contains("error.unknown.command"));
    }
}
