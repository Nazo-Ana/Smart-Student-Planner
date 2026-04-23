import java.time.LocalDate;

public class Assignment {

    private String subject;
    private String title;
    private String priority;
    private LocalDate dueDate;
    private boolean done;

    public Assignment(String subject, String title, LocalDate dueDate, String priority) {
        this.subject  = subject;
        this.title    = title;
        this.dueDate  = dueDate;
        this.priority = priority;
        this.done     = false;
    }

    // Getters
    public String    getSubject()  { return subject; }
    public String    getTitle()    { return title; }
    public String    getPriority() { return priority; }
    public LocalDate getDueDate()  { return dueDate; }
    public boolean   isDone()      { return done; }

    // Setter
    public void setDone(boolean done) { this.done = done; }
}