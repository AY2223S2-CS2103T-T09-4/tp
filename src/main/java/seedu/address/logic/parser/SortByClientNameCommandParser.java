package seedu.address.logic.parser;
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.sortcommand.SortByClientNameCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Command Parser for SortByClientNameCommand
 */
public class SortByClientNameCommandParser implements Parser<SortByClientNameCommand> {
    /**
     * @param userInput any integer number (inOrder) or 0 (reverseOrder)
     * @return command
     * @throws ParseException
     */
    @Override
    public SortByClientNameCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);
        userInput = userInput.trim();
        if (userInput.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortByClientNameCommand.MESSAGE_USAGE));
        }
        if (userInput.equals("asc")) {
            return new SortByClientNameCommand(true);
        }
        if (userInput.equals("dsc")) {
            return new SortByClientNameCommand(false);
        }
        throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortByClientNameCommand.MESSAGE_USAGE));
    }
}
