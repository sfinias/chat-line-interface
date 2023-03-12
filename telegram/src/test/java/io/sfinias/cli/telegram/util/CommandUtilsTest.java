package io.sfinias.cli.telegram.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

class CommandUtilsTest {

    @Test
    void translateCommandline_doubleQuotes() {

        String input = "this is a \"Test '@'  args\" ";
        List<String> expected = List.of("this", "is", "a", "Test '@'  args");
        List<String> actual = CommandUtils.INSTANT.parseCommand(input);
        assertEquals(expected, actual);
    }

    @Test
    void translateCommandline_singleQuotes() {

        String input = "this is a 'Test \"@\"  args' ";
        List<String> expected = List.of("this", "is", "a", "Test \"@\"  args");
        List<String> actual = CommandUtils.INSTANT.parseCommand(input);
        assertEquals(expected, actual);
    }

    @Test
    void translateCommandline_flags() {

        String input = "cat     -g";
        List<String> expected = List.of("cat", "-g");
        List<String> actual = CommandUtils.INSTANT.parseCommand(input);
        assertEquals(expected, actual);
    }

    @Test
    void translateCommandline_empty() {

        String input = "  ";
        List<String> expected = List.of();
        List<String> actual = CommandUtils.INSTANT.parseCommand(input);
        assertEquals(expected, actual);
    }

    @Test
    void translateCommandline_null() {

        String input = "  ";
        List<String> expected = List.of();
        List<String> actual = CommandUtils.INSTANT.parseCommand(input);
        assertEquals(expected, actual);
    }

    @Test
    void translateCommandline_doubleQuotesTelegram() {

        String input = "this is a “Test ‘@‘  args“ ";
        List<String> expected = List.of("this", "is", "a", "Test '@'  args");
        List<String> actual = CommandUtils.INSTANT.parseCommand(input);
        assertEquals(expected, actual);
    }

    @Test
    void translateCommandline_singleQuotesTelegram() {

        String input = "this is a ‘Test “@“  args‘ ";
        List<String> expected = List.of("this", "is", "a", "Test \"@\"  args");
        List<String> actual = CommandUtils.INSTANT.parseCommand(input);
        assertEquals(expected, actual);
    }
}