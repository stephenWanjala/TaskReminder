# Screencast 2  Notes

1. **Introduction**
    - Briefly introduce the Task Reminder app and its main features.
    - Mention the technologies used: Java, Gradle, Android Studio, SQLite(Room).

2. **Activities and Fragments**
    - Explain the role of Activities and Fragments in Android.
    - Receiver and The Service to Scheduler reminders 10 minutes before the task's due date.
    - Show the `nav_graph.xml` file and explain how it defines the navigation between different fragments in the app.

3. **TaskRepository**
    - Open `TaskRepository.java` and explain its role in managing data operations.
    - Discuss how it uses SQLite to store and retrieve tasks.
    - Explain the methods for inserting, updating, and deleting tasks.

4. **TaskAdapter**
    - Discuss the role of the `TaskAdapter` in displaying the list of tasks.
    - Explain how it changes the color of the task item based on its status.

5. **TaskViewFragment**
    - Open `TaskViewFragment.java` and explain how it handles editing and deletion of tasks.
    - Discuss how it uses the `TaskViewViewModel` to interact with the `TaskRepository`.

6. **MVC Architecture**
    - Explain the Model-View-Controller (MVC) architecture.
    - Point out the segregation among MVC components in the app: `Task` (Model), `TaskViewFragment` (View), `TaskViewViewModel` (Controller).

7. **GitHub Actions**
    - Open `.github/workflows/android.yml` and explain how it automates the build and release process of the app.

8. **Resources**
    - Show the `colors.xml` file and explain how it defines the colors for completed and overdue tasks.
    - Discuss other resources used in the app, such as layouts and navigation graphs.

