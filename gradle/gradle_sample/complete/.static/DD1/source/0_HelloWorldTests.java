package hello;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import org.junit.Before;
import org.junit.Test;
import org.junit.Ignore;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

public class HelloWorldTests {

    private ByteArrayOutputStream baos = new ByteArrayOutputStream();
    private PrintStream ps = new PrintStream(baos);

    @Before
    public void setup() {
        System.setOut(ps);
    }
	//TODO: Add javadoc comment
    @Test
    public void shouldPrintTimeToConsole() {
        HelloWorld.main(new String[] { });

        assertThat(output(), containsString("The current local time is"));
    }
	@Ignore
    @Test
    public void shouldPrintHelloWorldToConsole() {
        HelloWorld.main(new String[] { });

        assertThat(output(), containsString("Hello world!"));
    }

    private String output() {
        return new String(baos.toByteArray(), StandardCharsets.UTF_8);
    }
}
