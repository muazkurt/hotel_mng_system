package  gtu.kurt.muaz.hms;

/**
 * A interface for basic person methods.
 */
public interface Person
{
    /**
     * Getter for name of this person.
     * @return  Name of this person.
     */
    public String get_name();

    /**
     * Getter for surname of a person.
     * @return  Surname of this person
     */
    public String get_surname();

    /**
     * Checks both persons are same or not with
     *  name and surname equality
     * @param other Person with it's own info.
     * @return  true: name&surname equals, false otherwise.
     */
    public boolean equals(Person other);

    /**
     * Tostring.
     * @return String in csv format.
     */
    public String toString();
}