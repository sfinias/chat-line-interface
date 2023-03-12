package io.sfinias.cli.telegram.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;

public enum CommandUtils {
    INSTANT;

    public static final Set<Character> DEFAULT_DELIMITERS = Set.of('\'', '"');

    public List<String> parseCommand(String command) {

        return parseCommand(command, DEFAULT_DELIMITERS);
    }

    public List<String> parseCommand(String command, Set<Character> delimiters) {

        command = StringUtils.trimToEmpty(command).replace("“", "\"").replace("‘", "'");
        if (command.isBlank()) {
            return List.of();
        }
        final List<String> results = new ArrayList<>();
        boolean inDelimiter = false;
        Character delimiter = null;
        final StringBuilder sb = new StringBuilder();
        for (char c : command.toCharArray()) {
            if ((inDelimiter && c == delimiter) || (!inDelimiter && Character.isWhitespace(c) && sb.length() > 0)) {
                results.add(sb.toString());
                sb.setLength(0);
                inDelimiter = false;
            } else {
                Optional<Character> optionalDelimiter = delimiters.stream()
                        .filter(dl -> dl == c)
                        .findFirst();
                if (!inDelimiter && optionalDelimiter.isPresent()) {
                    inDelimiter = true;
                    delimiter = optionalDelimiter.get();
                } else if (inDelimiter || !Character.isWhitespace(c)) {
                    sb.append(c);
                }
            }
        }
        if (sb.length() > 0) {
            results.add(sb.toString());
        }
        return results;
    }
}
