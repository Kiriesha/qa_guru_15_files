package guru.qa.model;

public class Myself {
    public String name;
    public String lastname;
    public int age;
    public boolean work;
    public Passport passport;

    public static class Passport{
        public int number;
        public String issueDate;
    }
}
