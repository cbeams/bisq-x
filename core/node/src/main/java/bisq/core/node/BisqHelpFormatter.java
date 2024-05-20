package bisq.core.node;

import joptsimple.HelpFormatter;
import joptsimple.OptionDescriptor;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BisqHelpFormatter implements HelpFormatter {

    private final String fullName;
    private final String scriptName;
    private final String version;
    private final String description;

    public BisqHelpFormatter(String fullName, String scriptName, String version, String description) {
        this.fullName = fullName;
        this.scriptName = scriptName;
        this.version = version;
        this.description = description;
    }

    public String format(Map<String, ? extends OptionDescriptor> descriptors) {

        StringBuilder output = new StringBuilder();
        output.append(String.format("%s version %s\n\n", fullName, version));
        output.append(formatUsage(scriptName, description));
        output.append("Options:\n\n");

        for (Map.Entry<String, ? extends OptionDescriptor> entry : descriptors.entrySet()) {
            String optionName = entry.getKey();
            OptionDescriptor optionDesc = entry.getValue();

            if (optionDesc.representsNonOptions())
                continue;

            output.append(String.format("%s\n", formatOptionSyntax(optionName, optionDesc)));
            output.append(String.format("%s\n", formatOptionDescription(optionDesc)));
        }

        return output.toString();
    }

    private static String formatUsage(String scriptName, String description) {
        var usageLeft = String.format("Usage:  %s [options]", scriptName);
        int padLen = Math.max((65 - usageLeft.length() - description.length()), 2);
        var format = "%s%" + padLen + "s%s\n\n";
        return String.format(format, usageLeft, "", description);
    }

    private String formatOptionSyntax(String optionName, OptionDescriptor optionDesc) {
        StringBuilder result = new StringBuilder(String.format("  --%s", optionName));

        if (optionDesc.acceptsArguments())
            result.append(String.format("=<%s>", formatArgDescription(optionDesc)));

        List<?> defaultValues = optionDesc.defaultValues();
        if (!defaultValues.isEmpty())
            result.append(String.format(" (default: %s)", formatDefaultValues(defaultValues)));

        return result.toString();
    }

    private String formatArgDescription(OptionDescriptor optionDesc) {
        String argDescription = optionDesc.argumentDescription();

        if (!argDescription.isEmpty())
            return argDescription;

        String typeIndicator = optionDesc.argumentTypeIndicator();

        if (typeIndicator == null)
            return "value";

        try {
            Class<?> type = Class.forName(typeIndicator);
            return type.isEnum() ?
                    Arrays.stream(type.getEnumConstants()).map(Object::toString).collect(Collectors.joining("|")) :
                    typeIndicator.substring(typeIndicator.lastIndexOf('.') + 1);
        } catch (ClassNotFoundException ex) {
            // typeIndicator is something other than a class name, which can occur
            // in certain cases e.g. where OptionParser.withValuesConvertedBy is used.
            return typeIndicator;
        }
    }

    private Object formatDefaultValues(List<?> defaultValues) {
        return defaultValues.size() == 1 ?
                defaultValues.getFirst() :
                defaultValues.toString();
    }

    private String formatOptionDescription(OptionDescriptor optionDesc) {
        StringBuilder output = new StringBuilder();

        String remainder = optionDesc.description().trim();

        // Wrap description text at 80 characters with 8 spaces of indentation and a
        // maximum of 72 chars of text, wrapping on spaces. Strings longer than 72 chars
        // without any spaces (e.g. a URL) are allowed to overflow the 80-char margin.
        while (remainder.length() > 72) {
            int idxFirstSpace = remainder.indexOf(' ');
            int chunkLen = idxFirstSpace == -1 ? remainder.length() : Math.max(idxFirstSpace, 73);
            String chunk = remainder.substring(0, chunkLen);
            int idxLastSpace = chunk.lastIndexOf(' ');
            int idxBreak = idxLastSpace > 0 ? idxLastSpace : chunk.length();
            String line = remainder.substring(0, idxBreak);
            output.append(formatLine(line));
            remainder = remainder.substring(chunk.length() - (chunk.length() - idxBreak)).trim();
        }

        if (!remainder.isEmpty())
            output.append(formatLine(remainder));

        return output.toString();
    }

    private String formatLine(String line) {
        return String.format("        %s\n", line.trim());
    }
}
