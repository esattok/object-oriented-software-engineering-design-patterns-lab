// Esad İsmail Tök
// 21801679
// CS319 Design Patterns Assignment

import java.util.*;

// ************ Implementation of the State Design Pattern ************

// The interface that describes the general functionality of the subclasses that implements the interface
interface TaskStatusState {
    public TaskStatusState changeState();
}

// Every task is initially in the created state and the transition when the state changes is from created to in progress state
class CreatedState implements TaskStatusState {

    private static CreatedState instance;

    private CreatedState(){};

    public static CreatedState getInstance() {
        if (instance == null)
            instance = new CreatedState();

        return instance;
    }

    @Override
    public TaskStatusState changeState() {
        return InProgressState.getInstance();
    }

    public String toString() {
        return "Created";
    }
}

// The transition when the state changes is from in progress to completed state
class InProgressState implements TaskStatusState {

    private static InProgressState instance;

    private InProgressState(){};

    public static InProgressState getInstance() {
        if (instance == null)
            instance = new InProgressState();

        return instance;
    }

    @Override
    public TaskStatusState changeState() {
        return CompletedState.getInstance();
    }

    public String toString() {
        return "In Progress";
    }
}

// When a task is in completed state it cannot move to any other state and need to stay at completed state
class CompletedState implements TaskStatusState {

    private static CompletedState instance;

    private CompletedState(){};

    public static CompletedState getInstance() {
        if (instance == null)
            instance = new CompletedState();

        return instance;
    }

    @Override
    public TaskStatusState changeState() {
        return this;
    }

    public String toString() {
        return "Completed";
    }
}
// ************ End of the implementation of the State Design Pattern ************


// ************ Implementation of the Decorator Design Pattern ************

// The interface that describes the general functionality of the subclasses that implements the interface
interface Task {
    public abstract String getDescription();
    public abstract String getDeadline();
    public abstract String getStatus();
    public abstract void updateStatus();
}

// In the PlainTask class there is no additional behaviours but only the default functionalities and properties
class PlainTask implements Task{

    private String description;
    private String deadline;
    private TaskStatusState state;


    public PlainTask(String description, String year, String month, String day) {
        this.description = description;
        this.deadline = year + "-" +month + "-" + day;
        this.state = CreatedState.getInstance();
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getDeadline() {
        return deadline;
    }

    @Override
    public String getStatus() {
        return "[" + state.toString() + "]";
    }

    @Override
    public void updateStatus() {
        state = state.changeState();
    }
}

// In the TaskWithElapsedTime class there is an additional functionality that is calculating and displaying elapsed time in addition to the default functionalities.
class TaskWithElapsedTime implements Task{

    private String description;
    private String deadline;
    private TaskStatusState state;
    private String elapsedTime;


    public TaskWithElapsedTime(String description, String year, String month, String day) {
        this.description = description;
        this.deadline = year + "-" +month + "-" + day;
        this.state = CreatedState.getInstance();
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getDeadline() {
        return deadline;
    }

    // The additional information which is the elapsed time is provided as an additional information after the regular status information as concatination
    @Override
    public String getStatus() {
        return "[" + state.toString() + "] [Elapsed time: " + getElapsedTime() + "day(s)";
    }

    @Override
    public void updateStatus() {
        state = state.changeState();
    }

    public String getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(String elapsedTime) {
        this.elapsedTime = elapsedTime;
    }
}

// In the TaskWithStatusHistory class there is an additional functionality that is calculating and displaying elapsed time in addition to the default functionalities.
class TaskWithStatusHistory implements Task{

    private String description;
    private String deadline;
    private TaskStatusState state;
    private ArrayList<TaskStatusState> statusHistory;


    public TaskWithStatusHistory(String description, String year, String month, String day) {
        this.description = description;
        this.deadline = year + "-" +month + "-" + day;
        this.state = CreatedState.getInstance();

        // Create an empty status history first and then add a created status to the list since all the tasks starts with the created status
        statusHistory = new ArrayList<>();
        statusHistory.add(CreatedState.getInstance());
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getDeadline() {
        return deadline;
    }

    // The additional information which is the status history is provided as an additional information after the regular status information as concatination
    @Override
    public String getStatus() {
        return "[" + state.toString() + "] " + getStatusHistory();
    }

    @Override
    public void updateStatus() {
        state = state.changeState();
    }

    public String getStatusHistory() {
        String result;
        result = "[";

        for (int i = 0; i < 3; i++) {
            result = result + state.toString() + "->";
        }

        return result;
    }
}

// List abstract class will be used to store the tasks and other lists
abstract class List implements Task {
    protected ArrayList<Task> taskList;
    private String description;

    public abstract void displayList();
    public abstract String getDescription();
}

// The 3 different ordering classes extends the List abstract class and implements the functionalities accordingly

// ************ End of the implementation of the Decorator Design Pattern ************



