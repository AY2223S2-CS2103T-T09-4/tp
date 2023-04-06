package seedu.address.logic.parser;
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.sortcommand.SortByClientEmailCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Command Parser of SortByClientEmailCommand
 */
public class SortByClientEmailCommandParser implements Parser<SortByClientEmailCommand> {
    @Override
    public SortByClientEmailCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);
        userInput = userInput.trim();
        if (userInput.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortByClientEmailCommand.MESSAGE_USAGE));
        }
        if (userInput.equals("asc")) {
            return new SortByClientEmailCommand(true);
        }
        if (userInput.equals("dsc")) {
            return new SortByClientEmailCommand(false);
        }
        throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortByClientEmailCommand.MESSAGE_USAGE));
    }
}
