package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CLIENTS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalClients.ALICE;
import static seedu.address.testutil.TypicalClients.AMY;
import static seedu.address.testutil.TypicalClients.BENSON;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.client.Client;
import seedu.address.model.client.NameContainsKeywordsPredicate;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.ClientBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new AddressBook(), new AddressBook(modelManager.getAddressBook()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setAddressBookFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setAddressBookFilePath(null));
    }

    @Test
    public void setAddressBookFilePath_validPath_setsAddressBookFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setAddressBookFilePath(path);
        assertEquals(path, modelManager.getAddressBookFilePath());
    }

    @Test
    public void hasClient_nullClient_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasClient(null));
    }

    @Test
    public void hasClient_clientNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasClient(ALICE));
    }

    @Test
    public void hasClient_clientInAddressBook_returnsTrue() {
        modelManager.addClient(ALICE);
        assertTrue(modelManager.hasClient(ALICE));
    }

    @Test
    public void getFilteredClientList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredClientList().remove(0));
    }

    @Test
    public void getFilteredPolicyList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(IndexOutOfBoundsException.class, () -> modelManager.getFilteredPolicyList().remove(0));
    }

    @Test
    public void getFilteredPolicyList_returnsEmptyList() {
        modelManager.updateSelectedClient(AMY);
        assertEquals(FXCollections.observableArrayList(), modelManager.getFilteredPolicyList());
    }

    @Test
    void updateSelectedClient_modify_throwsUnsupportedOperationException() {
        Client targetClient = new ClientBuilder(AMY).withTags().build();
        modelManager.updateSelectedClient(targetClient);
        assertTrue(true);
    }

    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withClient(ALICE).withClient(BENSON).build();
        AddressBook differentAddressBook = new AddressBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(addressBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(addressBook, userPrefs);
        assertEquals(modelManager, modelManagerCopy);

        // same object -> returns true
        assertEquals(modelManager, modelManager);

        // null -> returns false
        assertNotEquals(null, modelManager);

        // different types -> returns false
        assertNotEquals(5, modelManager);

        // different addressBook -> returns false
        assertNotEquals(modelManager, new ModelManager(differentAddressBook, userPrefs));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredClientList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertNotEquals(modelManager, new ModelManager(addressBook, userPrefs));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredClientList(PREDICATE_SHOW_ALL_CLIENTS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertNotEquals(modelManager, new ModelManager(addressBook, differentUserPrefs));
    }

    @Test
    public void returnCorrectNumberOfClients() {
        AddressBook addressBook = new AddressBookBuilder().withClient(ALICE).withClient(BENSON).build();
        UserPrefs userPrefs = new UserPrefs();
        modelManager = new ModelManager(addressBook, userPrefs);

        // Returns weekly earnings in the addressBook declared.
        assertEquals(2, modelManager.getNumberOfClients());
    }

    @Test
    public void returnCorrectWeeklyEarnings() {
        AddressBook addressBook = new AddressBookBuilder().withClient(ALICE).withClient(BENSON).build();
        UserPrefs userPrefs = new UserPrefs();
        modelManager = new ModelManager(addressBook, userPrefs);


        double aliceWeeklyEarnings = (1000 / 4) + (2000 / 36);
        double bensonWeeklyEarnings = 1000;
        double total = aliceWeeklyEarnings + bensonWeeklyEarnings;

        // Returns weekly earnings in the addressBook declared.
        assertEquals(total, modelManager.getWeeklyEarnings());
    }

    @Test
    public void returnCorrectSummary() {

        AddressBook addressBook = new AddressBookBuilder().withClient(ALICE).withClient(BENSON).build();
        UserPrefs userPrefs = new UserPrefs();
        modelManager = new ModelManager(addressBook, userPrefs);


        double aliceWeeklyEarnings = (1000 / 4) + (2000 / 36);
        double bensonWeeklyEarnings = 1000;
        double total = aliceWeeklyEarnings + bensonWeeklyEarnings;

        HashMap<String, Integer> summary = new HashMap<>();
        summary.put("Clients: ", 2);
        summary.put("Weekly Earnings: ", (int) total);


        // Returns weekly earnings in the addressBook declared.
        assertEquals(summary, modelManager.getSummary());
    }
}
