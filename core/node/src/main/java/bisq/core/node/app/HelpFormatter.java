/*
 * This file is part of Bisq.
 *
 * Bisq is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version.
 *
 * Bisq is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with Bisq. If not, see <http://www.gnu.org/licenses/>.
 */

package bisq.core.node.app;

import joptsimple.OptionDescriptor;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class HelpFormatter implements joptsimple.HelpFormatter {

    private final String fullName;
    private final String scriptName;
    private final String version;
    private final String description;

    public HelpFormatter(String fullName, String scriptName, String version, String description) {
        this.fullName = fullName;
        this.scriptName = scriptName;
        this.version = version;
        this.description = description;
    }

    public String format(Map<String, ? extends OptionDescriptor> descriptors) {

        var output = new StringBuilder();
        output.append(String.format("%s version %s\n\n", fullName, version));
        output.append(formatUsage(scriptName, description));
        output.append("Options:\n\n");

        var uniqueDescriptors = new LinkedHashSet<OptionDescriptor>(descriptors.values());

        for (OptionDescriptor optionDesc : uniqueDescriptors) {
            if (optionDesc.representsNonOptions())
                continue;

            output.append(String.format("%s\n", formatOptionSyntax(optionDesc)));
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

    private String formatOptionSyntax(OptionDescriptor optionDesc) {
        var result = new StringBuilder("  ");
        var options = optionDesc.options();
        int nOptions = options.size();
        for (int i = 0; i < nOptions; ) {
            var optionName = options.get(i);
            var argDesc = formatArgDescription(optionDesc);
            if (optionName.length() == 1) {
                // format short option
                result.append(String.format("-%s", optionName));
                if (optionDesc.acceptsArguments()) {
                    if (optionDesc.requiresArgument()) {
                        result.append(String.format(" <%s>", argDesc));
                    }
                }
            } else {
                // format long option
                result.append(String.format("--%s", optionName));
                if (optionDesc.acceptsArguments()) {
                    if (optionDesc.requiresArgument()) {
                        result.append(String.format("=<%s>", argDesc));
                    } else {
                        result.append(String.format("[=<%s>]", argDesc));
                    }
                }
            }
            if (nOptions > ++i)
                result.append(", ");
        }
        return result.toString();
    }

    private String formatArgDescription(OptionDescriptor optionDesc) {
        var argDescription = optionDesc.argumentDescription();

        if (!argDescription.isEmpty())
            return argDescription;

        var typeIndicator = optionDesc.argumentTypeIndicator();

        if (typeIndicator == null)
            return "value";

        try {
            var type = Class.forName(typeIndicator);
            return type.isEnum() ?
                    Arrays.stream(type.getEnumConstants()).map(Object::toString).collect(Collectors.joining("|")) :
                    typeIndicator.substring(typeIndicator.lastIndexOf('.') + 1).toLowerCase();
        } catch (ClassNotFoundException ex) {
            // typeIndicator is something other than a class name, which can occur
            // in certain cases e.g. where OptionParser.withValuesConvertedBy is used.
            return typeIndicator;
        }
    }

    private String formatOptionDescription(OptionDescriptor optionDesc) {
        var output = new StringBuilder();

        var remainder = optionDesc.description().trim();

        // Wrap description text at 80 characters with 7 spaces of indentation and a
        // maximum of 73 chars of text, wrapping on spaces. Strings longer than 73 chars
        // without any spaces (e.g. a URL) are allowed to overflow the 80-char margin.
        while (remainder.length() > 73) {
            int idxFirstSpace = remainder.indexOf(' ');
            int chunkLen = idxFirstSpace == -1 ? remainder.length() : Math.max(idxFirstSpace, 73);
            var chunk = remainder.substring(0, chunkLen);
            int idxLastSpace = chunk.lastIndexOf(' ');
            int idxBreak = idxLastSpace > 0 ? idxLastSpace : chunk.length();
            var line = remainder.substring(0, idxBreak);
            output.append(formatLine(line));
            remainder = remainder.substring(chunk.length() - (chunk.length() - idxBreak)).trim();
        }

        if (!remainder.isEmpty())
            output.append(formatLine(remainder));

        var defaultValues = optionDesc.defaultValues();
        if (!defaultValues.isEmpty())
            output.append(formatLine(String.format(" (default: %s)", formatDefaultValues(defaultValues))));

        return output.toString();
    }

    private Object formatDefaultValues(List<?> defaultValues) {
        return defaultValues.size() == 1 ?
                defaultValues.getFirst() :
                defaultValues.toString();
    }

    private String formatLine(String line) {
        return String.format("       %s\n", line.trim());
    }
}
