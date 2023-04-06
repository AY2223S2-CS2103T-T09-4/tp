package seedu.address.logic.parser;
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.sortcommand.SortByClientPhoneCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parser for SortByClientPhoneCommand
 */
public class SortByClientPhoneCommandParser implements Parser<SortByClientPhoneCommand> {

    @Override
    public SortByClientPhoneCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);
        userInput = userInput.trim();
        if (userInput.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortByClientPhoneCommand.MESSAGE_USAGE));
        }
        if (userInput.equals("asc")) {
            return new SortByClientPhoneCommand(true);
        }
        if (userInput.equals("dsc")) {
            return new SortByClientPhoneCommand(false);
        }
        throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortByClientPhoneCommand.MESSAGE_USAGE));
    }
}
