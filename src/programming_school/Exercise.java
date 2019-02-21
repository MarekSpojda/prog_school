package programming_school;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Exercise {
    private int id;
    private String title;
    private String description;

    Exercise() {
    }

    Exercise(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public int getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @SuppressWarnings("Duplicates")
    public Exercise[] loadAll() {
        try (Connection connection = ConnectionManager.getConnection()) {
            ArrayList<Exercise> exercises = new ArrayList<Exercise>();
            String sql = "SELECT * FROM exercise";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Exercise loadedExercise = new Exercise();
                loadedExercise.id = resultSet.getInt("id");
                loadedExercise.title = resultSet.getString("title");
                loadedExercise.description = resultSet.getString("description");
                exercises.add(loadedExercise);
            }
            Exercise[] exArray = new Exercise[exercises.size()];
            exArray = exercises.toArray(exArray);

            return exArray;
        } catch (Exception e) {
            System.err.println("Failed to load all exercises.");
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("Duplicates")
    public Exercise loadById(int id) {
        try (Connection connection = ConnectionManager.getConnection()) {
            String sql = "SELECT * FROM exercise where id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Exercise loadedExercise = new Exercise();
                loadedExercise.id = resultSet.getInt("id");
                loadedExercise.title = resultSet.getString("title");
                loadedExercise.description = resultSet.getString("description");
                return loadedExercise;
            }
            return null;
        } catch (Exception e) {
            System.err.println("Failed to load exercise. Please check if result is correct.");
            e.printStackTrace();
        }
        return null;
    }

    public void delete() {
        try (Connection connection = ConnectionManager.getConnection()) {
            if (this.id != 0) {
                String sql = "DELETE FROM exercise WHERE id=?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, this.id);
                preparedStatement.executeUpdate();
                this.id = 0;
            }
        } catch (Exception e) {
            System.err.println("Failed to delete exercise.");
            e.printStackTrace();
        }
    }

    public void saveToDb() {
        try (Connection connection = ConnectionManager.getConnection()) {
            String insertExerciseQuery =
                    "INSERT INTO exercise (title,description) VALUES (?,?)";
            PreparedStatement ps =
                    connection.prepareStatement(insertExerciseQuery, PreparedStatement.RETURN_GENERATED_KEYS);
            int idx = 0; // because of pre incrementation
            ps.setString(++idx, this.title);
            ps.setString(++idx, this.description);
            ps.execute();
            System.out.println("Exercise saved into database");

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                this.id = rs.getInt(1);
            }

        } catch (SQLException e) {
            System.err.println("Can't create exercise.");
            e.printStackTrace();
        }
    }

    @SuppressWarnings("Duplicates")
    public void update() {
        try (Connection connection = ConnectionManager.getConnection()) {
            if (this.id == 0) {
                String insertExerciseQuery =
                        "INSERT INTO exercise (title, description) VALUES (?,?)";
                PreparedStatement ps =
                        connection.prepareStatement(insertExerciseQuery, PreparedStatement.RETURN_GENERATED_KEYS);
                int idx = 0; // because of pre incrementation
                ps.setString(++idx, this.title);
                ps.setString(++idx, this.description);
                ps.execute();
                System.out.println("Exercise saved into database");

                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    this.id = rs.getInt(1);
                }
            } else {
                String sql = "UPDATE exercise SET title=?, description=? where id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, this.title);
                preparedStatement.setString(2, this.description);
                preparedStatement.setInt(3, this.id);
                preparedStatement.executeUpdate();
            }
        } catch (Exception e) {
            System.err.println("Failed to update exercise record.");
            e.printStackTrace();
        }
    }
}