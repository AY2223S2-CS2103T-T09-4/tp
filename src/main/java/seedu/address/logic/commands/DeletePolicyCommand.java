package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CLIENTS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.client.Client;
import seedu.address.model.client.policy.Policy;

/**
 * Deletes a policy identified using it's displayed index from the client that is identified using its displayed
 * index from the address book.
 */
public class DeletePolicyCommand extends Command {
    public static final String COMMAND_WORD = "deletePolicy";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the policy from the client identified by the index number used in the displayed list and"
            + " the policy identified by the index number used in the displayed policy list"
            + " associated to the client.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1" + " pi/1";
    public static final String MESSAGE_DELETE_POLICY_SUCCESS = "Deleted Policy: %1$s";

    public final Index clientIndex;
    public final Index policyIndex;



    /**
     * Creates a DeletePolicyCommand to remove the specified {@code Policy} by the index displayed in the client policy
     * list.
     * @param clientIndex the client index displayed from the address book.
     * @param policyIndex the policy index displayed from the client.
     */
    public DeletePolicyCommand(Index clientIndex, Index policyIndex) {
        requireAllNonNull(clientIndex, policyIndex);
        this.clientIndex = clientIndex;
        this.policyIndex = policyIndex;
    }
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Client> lastShownList = model.getFilteredClientList();

        if (clientIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CLIENT_DISPLAYED_INDEX);
        }

        Client clientToDeletePolicy = lastShownList.get(clientIndex.getZeroBased());
        Client deletedPolicyClient = clientToDeletePolicy.cloneClient();
        // Work with titus on how to select the client that we are interested in first.
        CommandResult cr = new SelectCommand(clientIndex).execute(model);

        List<Policy> lastShownPolicyList = clientToDeletePolicy.getFilteredPolicyList();

        if (policyIndex.getZeroBased() >= lastShownPolicyList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_POLICY_DISPLAYED_INDEX);
        }

        Policy policyToDelete = lastShownPolicyList.get(policyIndex.getZeroBased());
        //clientToDeletePolicy.getPolicyList().remove(policyToDelete);
        deletedPolicyClient.getPolicyList().remove(policyToDelete);
        model.setClient(clientToDeletePolicy, deletedPolicyClient);
        model.updateFilteredClientList(PREDICATE_SHOW_ALL_CLIENTS);

        return new CommandResult(generateSuccessMessage(clientToDeletePolicy, policyToDelete),
                deletedPolicyClient, true, false, false);
    }

    private String generateSuccessMessage(Client client, Policy policy) {
        return String.format(
            MESSAGE_DELETE_POLICY_SUCCESS, policy.toString()) + " from: "
                + client.getName().toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DeletePolicyCommand)) {
            return false;
        }

        DeletePolicyCommand e = (DeletePolicyCommand) other;
        return policyIndex.equals(e.policyIndex)
                && clientIndex.equals(e.clientIndex);
    }
}
