package  gtu.kurt.muaz.hms;


public class Room
{
    /**
     * Will be declared by Hotel.
     */
    private final int room_no;

    /**
     * User name
     */
    private String _name;

    /**
     * User surname
     */
    private String _surname;

    /**
     * Room situation.
     * 0 = empty
     * 1 = reserved
     * 2 = filled
     */
    private int _situation;


    /**
     * Creates a empty room.
     * @param counter for room number. Declared by Hotel.
     */
    public Room(int counter)
    {
        this("", "", 0, counter);
    }

    /**
     * Creates a room with specified user info and situation.
     * @param input Person for name and surname
     * @param situation room situation
     * @param counter Room number, declared by hotel.
     */
    public Room(Person input, int situation, int counter)
    {
        this(input.get_name(), input.get_surname(), situation, counter);
    }

    /**
     * Creates a room with given input.
     * @param name User name who holds the room ("" if room is empty)
     * @param surname User surname who holds the room ("" if room is empty)
     * @param situation Room situation.
     * @param counter Room number, declared by Hotel.
     */
    public Room(String name, String surname, int situation, int counter)
    {
        _name = name;
        _surname = surname;
        room_no = counter;
        _situation = situation;
    }

    public int get_situation()
    {
        return _situation;
    }

    public String get_name()
    {
        return _name;
    }

    public String get_surname()
    {
        return _surname;
    }

    public int room_number()
    {
        return room_no;
    }

    /**
     * Updates current situation by given input.
     * @param input situation info.
     * @return this room.
     */
    public Room set_situation(int input)
    {
        if(input > -1 && input < 3)
            _situation = input;
        return this;
    }

    /**
     * Checks if human is the user of this room.
     * @param human Another person to check if they are same.
     * @return True: name and surname are equal. False otherwise.
     */
    public boolean room_taken(Person human)
    {
        return ((_situation > 0) && (_name.equals(human.get_name()) && _surname.equals(human.get_surname())));
    }

    /**
     * Creates a String for print it to csv file.
     * @return Csv formated string of this room.
     */
    public String toString()
    {
        if(_situation == 0)
            return String.format("\"%d\", \"\", \"\", \"empty\"", room_no);
        return String.format("\"%d\", \"%s\", \"%s\", \"%s\"", room_no, _name, _surname, ((_situation == 1) ? "booked" : "filled"));
    }
}