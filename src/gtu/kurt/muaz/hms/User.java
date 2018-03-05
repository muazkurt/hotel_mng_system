package  gtu.kurt.muaz.hms;

import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Abstract class for Hotel system. This methods should used by the system user because of encapsulation.
 */
public abstract class User implements Person
{
    /**
     * Name of a person.
     */
    protected String name;

    /**
     * Surname of a person.
     */
    protected String surname;

    /**
     * Main Hotel object to work on.
     */
    protected Hotel working_on;

    /**
     * Information about User have booked a room yet
     */
    protected Room booked_room;

    public boolean equals(Person other)
    {
        return (get_name() == other.get_name()) && (get_surname() == other.get_surname());
    }

    public String get_name()
    {
        return name;
    }

    public String get_surname()
    {
        return surname;
    }

    /**
     * Makes it's info printable to csv file.
     * @return Created string.
     */
    public String toString()
    {
        return String.format("\"%s\", \"%s\", \"%d\"", get_name(), get_surname(), get_room().room_number());
    }

    /**
     * A tool that search integer input from system input
     * @return number scanned from system input.
     */
    protected int input_no()
    {
        Scanner file = new Scanner(System.in);
        return file.nextInt();
    }

    /**
     * Getter.
     * @return booked | check-inned room.
     */
    public Room get_room()
    {
        return booked_room;
    }

    /**
     * Guest | Receptionist ability. To book a room, user looks for empty rooms then choses in them.
     */
    public abstract void book_room();

    /**
     * Base method for asking user to chose operations.
     * @return False if exit situation occures. True otherwise.
     * @throws FileNotFoundException for room_db didn't found.
     */
    public abstract boolean show_options() throws FileNotFoundException;

    /**
     * Guest | Receptionist ability. To cancel a room, user should have had booked a room.
     */
    protected abstract void cancel_room();
}