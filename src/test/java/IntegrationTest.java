import com.dkatalis.exercise.atm.manager.ATMManager;
import com.dkatalis.exercise.atm.model.User;
import com.dkatalis.exercise.atm.provider.AuthenticationProvider;
import com.dkatalis.exercise.atm.provider.MessageProvider;
import com.dkatalis.exercise.atm.provider.impl.ResourceBundleMessageProvider;
import com.dkatalis.exercise.atm.repository.UserRepository;
import org.junit.jupiter.api.*;
import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class IntegrationTest {

    private ATMManager atm;
    private MessageProvider messageProvider;
    private AuthenticationProvider authenticationProvider;
    private ByteArrayOutputStream output;

    @BeforeAll
    void setupApp() {
        atm = new ATMManager();

        output = new ByteArrayOutputStream();
        messageProvider = new ResourceBundleMessageProvider();
        authenticationProvider = atm.getAuthenticationProvider();
        System.setOut(new PrintStream(output));
    }

    private String runCommand(String input) {
        atm.runOnce(input);
        String result = output.toString().trim();
        output.reset();
        return result;
    }

    public String buildExpectedResult(String...messages){
        User currentUser = authenticationProvider.getAuthenticatedUser();
        String credential = "[type command] > ";
        if(currentUser != null){
           credential = currentUser.getName() + " [" + currentUser.getId() + "] > ";
        }

        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append(credential);
        for(String message : messages) {
            messageBuilder.append(message);
            messageBuilder.append("\n");
        }

        return messageBuilder.toString().trim();
    }

    @Test
    @Order(1)
    void testScenario() {
        String expected = buildExpectedResult(
                messageProvider.get("auth.login.success", "Alice"),
                messageProvider.get("display.balance", 0));

        String result = runCommand("login Alice");

        assertEquals(expected, result);
    }

    @Test
    @Order(2)
    void testScenario2() {
        String expected = buildExpectedResult(messageProvider.get("display.balance", 100));
        String result = runCommand("deposit 100");
        assertEquals(expected, result);
    }

    @Test
    @Order(3)
    void testScenario3() {
        String expected = buildExpectedResult(messageProvider.get("auth.logout.success", "Alice"));
        String result = runCommand("logout");
        assertEquals(expected, result);
    }

    @Test
    @Order(4)
    void testScenario4() {
        String expected = buildExpectedResult(
                messageProvider.get("auth.login.success", "Bob"),
                messageProvider.get("display.balance", 0));

        String result = runCommand("login Bob");
        assertEquals(expected, result);
    }

    @Test
    @Order(5)
    void testScenario5() {
        String expected = buildExpectedResult(messageProvider.get("display.balance", 80));
        String result = runCommand("deposit 80");
        assertEquals(expected, result);
    }

    @Test
    @Order(6)
    void testScenario6() {
        String expected = buildExpectedResult(
                messageProvider.get("transfer.success", 50, "Alice"),
                messageProvider.get("display.balance", 30));

        String result = runCommand("transfer Alice 50");
        assertEquals(expected, result);
    }

    @Test
    @Order(7)
    void testScenario7() {
        String expected = buildExpectedResult(
                messageProvider.get("transfer.success", 30, "Alice"),
                messageProvider.get("display.balance", 0),
                messageProvider.get("display.debt", 70, "Alice"));

        String result = runCommand("transfer Alice 100");
        assertEquals(expected, result);
    }

    @Test
    @Order(8)
    void testScenario8() {
        String expected = buildExpectedResult(
                messageProvider.get("transfer.success", 30, "Alice"),
                messageProvider.get("display.balance", 0),
                messageProvider.get("display.debt", 40, "Alice"));

        String result = runCommand("deposit 30");
        assertEquals(expected, result);
    }

    @Test
    @Order(9)
    void testScenario9() {
        String expected = buildExpectedResult(messageProvider.get("auth.logout.success", "Bob"));
        String result = runCommand("logout");
        assertEquals(expected, result);
    }

    @Test
    @Order(10)
    void testScenario10() {
        String expected = buildExpectedResult(
                messageProvider.get("auth.login.success", "Alice"),
                messageProvider.get("display.balance", 210),
                messageProvider.get("display.receive", "40", "Bob"));

        String result = runCommand("login Alice");

        assertEquals(expected, result);
    }

    @Test
    @Order(11)
    void testScenario11() {
        String expected = buildExpectedResult(
                messageProvider.get("display.balance", 210),
                messageProvider.get("display.receive", 10, "Bob"));

        String result = runCommand("transfer Bob 30");

        assertEquals(expected, result);
    }

    @Test
    @Order(12)
    void testScenario12() {
        String expected = buildExpectedResult(messageProvider.get("auth.logout.success", "Alice"));
        String result = runCommand("logout");

        assertEquals(expected, result);
    }

    @Test
    @Order(13)
    void testScenario13() {
        String expected = buildExpectedResult(
                messageProvider.get("auth.login.success", "Bob"),
                messageProvider.get("display.balance", 0),
                messageProvider.get("display.debt", 10, "Alice")
                );

        String result = runCommand("login Bob");
        assertEquals(expected, result);
    }

    @Test
    @Order(14)
    void testScenario14() {
        String expected = buildExpectedResult(
                messageProvider.get("transfer.success", 10, "Alice"),
                messageProvider.get("display.balance", 90));

        String result = runCommand("deposit 100");
        assertEquals(expected, result);
    }
}
