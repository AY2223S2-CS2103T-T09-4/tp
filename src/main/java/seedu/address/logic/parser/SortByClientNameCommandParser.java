package seedu.address.logic.parser;

import seedu.address.logic.commands.SortCommand.SortByClientNameCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

public class SortByClientNameCommandParser implements Parser<SortByClientNameCommand> {

    /**
     *
     * @param args any integer number (inOrder) or 0 (reverseOrder)
     * @return
     * @throws ParseException
     */
    @Override
    public SortByClientNameCommand parse(String args) throws ParseException {
        requireNonNull(args);
        try {
            args = args.trim();
            int index = Integer.parseInt(args);
            if (index == 0) {
                return new SortByClientNameCommand(false);
            }
            return new SortByClientNameCommand(true);
        } catch (NumberFormatException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortByClientNameCommand.MESSAGE_USAGE), ive);
        }
    }
}
