package chocan.cli;

import chocan.IOTestUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runners.model.TestTimedOutException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;

public class CommandLineInterfaceTest {
	
	private static final String TEST_NAME = "test";
	private static final String TEST_DESCRIPTION = "this is a description for doing t3sting";
	private static final String TEST_HELP = "this is some help text for the t3sting command";
	
	private volatile boolean executedCommand = false;
	
	@Test(timeout=1000)
	public void testExit() throws IOException, InterruptedException {
		final CommandLineInterface cli = new CommandLineInterface();
		IOTestUtils.configurePipes(cli::run, (final OutputStream toInput, final InputStream fromOutput) -> {
			final PrintStream printStream = new PrintStream(toInput);
			printStream.println("exit");
			// Check output
			IOTestUtils.drain(fromOutput);
		});
	}
	
	@Test(timeout=2000)
	public void testNoExit() throws IOException, InterruptedException {
		final CommandLineInterface cli = new CommandLineInterface();
		Assert.assertFalse("CLI quit without invoking exit command.", 
			IOTestUtils.configurePipes(cli::run, (final OutputStream toInput, final InputStream fromOutput) -> {}, 1000));
	}

	@Test(timeout=1000)
	public void testCommandExecute() throws IOException, InterruptedException {
		this.executedCommand = false;
		final CommandLineInterface cli = new CommandLineInterface();
		cli.addCommand(new Command(TEST_NAME, TEST_DESCRIPTION, TEST_HELP, (final List<String> args) -> {
			this.executedCommand = true;
			return true;
		}));
		IOTestUtils.configurePipes(cli::run, (final OutputStream toInput, final InputStream fromOutput) -> {
			final PrintStream printStream = new PrintStream(toInput);
			printStream.println(TEST_NAME);
			printStream.println("exit");
			// Check output
			IOTestUtils.drain(fromOutput);
			Assert.assertTrue("Failed to execute command.", this.executedCommand);
		});
	}

	@Test(timeout=1000)
	public void testCommandProducedOutput() throws IOException, InterruptedException {
		final String testString = "abcdefghijklmopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		final CommandLineInterface cli = new CommandLineInterface();
		cli.setHelpOnStart(false);
		cli.addCommand(new Command(TEST_NAME, TEST_DESCRIPTION, TEST_HELP, (final List<String> args) -> {
			System.out.println(testString);
			return true;
		}));
		IOTestUtils.configurePipes(cli::run, (final OutputStream toInput, final InputStream fromOutput) -> {
			final PrintStream printStream = new PrintStream(toInput);
			printStream.println(TEST_NAME);
			printStream.println("exit");
			// Check output
			final String output = IOTestUtils.toString(fromOutput);
			Assert.assertEquals("Test command failed to produce expected output.", output,
				cli.getPrompt() + testString + System.lineSeparator() + cli.getPrompt());
		});
	}

	@Test(timeout=1000)
	public void testCommandHelp() throws IOException, InterruptedException {
		final CommandLineInterface cli = new CommandLineInterface();
		cli.setHelpOnStart(false);
		cli.addCommand(new Command(TEST_NAME, TEST_DESCRIPTION, TEST_HELP, (final List<String> args) -> true));
		IOTestUtils.configurePipes(cli::run, (final OutputStream toInput, final InputStream fromOutput) -> {
			final PrintStream printStream = new PrintStream(toInput);
			printStream.println("help " + TEST_NAME);
			printStream.println("exit");
			// Check output
			final String output = IOTestUtils.toString(fromOutput);
			Assert.assertTrue("Help command output failed to include test command name.", output.contains(TEST_NAME));
			Assert.assertTrue("Help command output failed to include test command description.", output.contains(TEST_DESCRIPTION));
			Assert.assertTrue("Help command output failed to include test command help.", output.contains(TEST_HELP));
		});
	}

	@Test(timeout=1000)
	public void testHelp() throws IOException, InterruptedException {
		final CommandLineInterface cli = new CommandLineInterface();
		cli.setHelpOnStart(false);
		cli.addCommand(new Command(TEST_NAME, TEST_DESCRIPTION, TEST_HELP, (final List<String> args) -> true));
		IOTestUtils.configurePipes(cli::run, (final OutputStream toInput, final InputStream fromOutput) -> {
			final PrintStream printStream = new PrintStream(toInput);
			printStream.println("help");
			printStream.println("exit");
			// Check output
			final String output = IOTestUtils.toString(fromOutput);
			Assert.assertTrue("Help command output failed to include test command name.", output.contains(TEST_NAME));
			Assert.assertTrue("Help command output failed to include test command description.", output.contains(TEST_DESCRIPTION));
		});
	}

	@Test(timeout=1000)
	public void testAutoHelp() throws IOException, InterruptedException {
		final CommandLineInterface cli = new CommandLineInterface();
		cli.setHelpOnStart(false);
		cli.addCommand(new Command(TEST_NAME, TEST_DESCRIPTION, TEST_HELP, (final List<String> args) -> true));
		IOTestUtils.configurePipes(cli::run, (final OutputStream toInput, final InputStream fromOutput) -> {
			final PrintStream printStream = new PrintStream(toInput);
			printStream.println(); // triggers automatic help
			printStream.println("exit");
			// Check output
			final String output = IOTestUtils.toString(fromOutput);
			Assert.assertTrue("Help command output failed to include test command name.", output.contains(TEST_NAME));
			Assert.assertTrue("Help command output failed to include test command description.", output.contains(TEST_DESCRIPTION));
		});
	}

	@Test(timeout=1000)
	public void testNoAutoHelp() throws IOException, InterruptedException {
		final CommandLineInterface cli = new CommandLineInterface();
		cli.setHelpOnStart(false);
		cli.setHelpOnEmpty(false);
		cli.addCommand(new Command(TEST_NAME, TEST_DESCRIPTION, TEST_HELP, (final List<String> args) -> true));
		IOTestUtils.configurePipes(cli::run, (final OutputStream toInput, final InputStream fromOutput) -> {
			final PrintStream printStream = new PrintStream(toInput);
			printStream.println(); // triggers automatic help
			printStream.println("exit");
			// Check output
			final String output = IOTestUtils.toString(fromOutput);
			Assert.assertFalse("Help command output failed to include test command name.", output.contains(TEST_NAME));
			Assert.assertFalse("Help command output failed to include test command description.", output.contains(TEST_DESCRIPTION));
		});
	}
	
}
