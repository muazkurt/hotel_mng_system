public class Room
{
    /**
     * 0 = empty
     * 1 = reserved
     * 2 = filled
     */
    private final int room_no;
    private String _name;
    private String _surname;
    private int _situation;


    public Room(int counter)
    {
        this("", "", 0, counter);
    }

    public Room(Person input, int situation, int counter)
    {
        this(input.get_name(), input.get_surname(), situation, counter);
    }
    
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


    public Room set_situation(int input)
    {
        _situation = input;
        return this;
    }

    public boolean room_taken(Person human)
    {
        return ((_situation > 0) && (_name.equals(human.get_name()) && _surname.equals(human.get_surname())));
    }

    public String toString()
    {
        if(_situation == 0)
            return String.format("\"%d\", \"\", \"\", \"empty\"", room_no);
        return String.format("\"%d\", \"%s\", \"%s\", \"%s\"", room_no, _name, _surname, ((_situation == 1) ? "booked" : "filled"));
    }
}