import java.util.Scanner;

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


    public String get_name() 
    {
        return name;
    }

    
    public String get_surname() 
    {
        return surname;
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
     * Checks two person are same.
     */
    public boolean equals(Person other)
    {
        return (get_name() == other.get_name()) && (get_surname() == other.get_surname());
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
     * Base operation
     */
    public abstract boolean show_options();

    protected abstract void cancel_room();


}