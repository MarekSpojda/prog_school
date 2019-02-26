package programming_school;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Solution {
    private int id;
    private String created;
    private String updated;
    private String description;
    private int exercise_id;
    private int users_id;

    public Solution() {
    }

    public Solution(String created, String updated, int exercise_id, int users_id) {
        this.created = created;
        this.updated = updated;
        this.exercise_id = exercise_id;
        this.users_id = users_id;
    }

    public Solution(String created, String updated, String description, int exercise_id, int users_id) {
        this.created = created;
        this.updated = updated;
        this.description = description;
        this.exercise_id = exercise_id;
        this.users_id = users_id;
    }

    public int getId() {
        return this.id;
    }

    public String getCreated() {
        return this.created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUpdated() {
        return this.updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getExercise_id() {
        return this.exercise_id;
    }

    public void setExercise_id(int exercise_id) {
        this.exercise_id = exercise_id;
    }

    public int getUsers_id() {
        return this.users_id;
    }

    public void setUsers_id(int users_id) {
        this.users_id = users_id;
    }

    @SuppressWarnings("Duplicates")
    public Solution[] loadAll() throws SQLException {
        try (Connection connection = ConnectionManager.getConnection()) {
            ArrayList<Solution> solutions = new ArrayList<Solution>();
            String sql = "SELECT * FROM solution";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Solution loadedSolution = new Solution();
                loadedSolution.id = resultSet.getInt("id");
                loadedSolution.created = resultSet.getString("created");
                loadedSolution.updated = resultSet.getString("updated");
                loadedSolution.description = resultSet.getString("description");
                loadedSolution.exercise_id = resultSet.getInt("exercise_id");
                loadedSolution.users_id = resultSet.getInt("users_id");
                solutions.add(loadedSolution);
            }
            Solution[] solArray = new Solution[solutions.size()];
            solArray = solutions.toArray(solArray);

            return solArray;
        } catch (Exception e) {
            System.err.println("Failed to load all solutions.");
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("Duplicates")
    public Solution loadById(int id) {
        try (Connection connection = ConnectionManager.getConnection()) {
            String sql = "SELECT * FROM solution where id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Solution loadedSolution = new Solution();
                loadedSolution.id = resultSet.getInt("id");
                loadedSolution.created = resultSet.getString("created");
                loadedSolution.updated = resultSet.getString("updated");
                loadedSolution.description = resultSet.getString("description");
                loadedSolution.exercise_id = resultSet.getInt("exercise_id");
                loadedSolution.users_id = resultSet.getInt("users_id");
                return loadedSolution;
            }
            return null;
        } catch (Exception e) {
            System.err.println("Failed to load solution. Please check if result is correct.");
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("Duplicates")
    public Solution[] loadAllByUserId(int userId) {
        try (Connection connection = ConnectionManager.getConnection()) {
            ArrayList<Solution> solutions = new ArrayList<Solution>();
            String sql = "SELECT * FROM solution where users_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Solution loadedSolution = new Solution();
                loadedSolution.id = resultSet.getInt("id");
                loadedSolution.created = resultSet.getString("created");
                loadedSolution.updated = resultSet.getString("updated");
                loadedSolution.description = resultSet.getString("description");
                loadedSolution.exercise_id = resultSet.getInt("exercise_id");
                loadedSolution.users_id = resultSet.getInt("users_id");
                solutions.add(loadedSolution);
            }
            Solution[] solArray = new Solution[solutions.size()];
            solArray = solutions.toArray(solArray);

            return solArray;
        } catch (Exception e) {
            System.err.println("Failed to load all solutions by user ID.");
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("Duplicates")
    public Solution[] loadAllByExerciseId(int exerciseId) {
        try (Connection connection = ConnectionManager.getConnection()) {
            ArrayList<Solution> solutions = new ArrayList<Solution>();
            String sql = "SELECT * FROM solution where exercise_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, exerciseId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Solution loadedSolution = new Solution();
                loadedSolution.id = resultSet.getInt("id");
                loadedSolution.created = resultSet.getString("created");
                loadedSolution.updated = resultSet.getString("updated");
                loadedSolution.description = resultSet.getString("description");
                loadedSolution.exercise_id = resultSet.getInt("exercise_id");
                loadedSolution.users_id = resultSet.getInt("users_id");
                solutions.add(loadedSolution);
            }
            Solution[] solArray = new Solution[solutions.size()];
            solArray = solutions.toArray(solArray);

            return solArray;
        } catch (Exception e) {
            System.err.println("Failed to load all solutions by exercise ID.");
            e.printStackTrace();
        }
        return null;
    }

    public void delete() {
        try (Connection connection = ConnectionManager.getConnection()) {
            if (this.id != 0) {
                String sql = "DELETE FROM solution WHERE id=?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, this.id);
                preparedStatement.executeUpdate();
                this.id = 0;
            }
        } catch (Exception e) {
            System.err.println("Failed to delete solution.");
            e.printStackTrace();
        }
    }

    public void saveToDb() {
        try (Connection connection = ConnectionManager.getConnection()) {
            String insertSolutionQuery =
                    "INSERT INTO solution (created,updated,description,exercise_id,users_id) VALUES (?,?,?,?,?)";
            PreparedStatement ps =
                    connection.prepareStatement(insertSolutionQuery, PreparedStatement.RETURN_GENERATED_KEYS);
            int idx = 0; // because of pre incrementation
            ps.setString(++idx, this.created);
            ps.setString(++idx, this.updated);
            ps.setString(++idx, this.description);
            ps.setInt(++idx, this.exercise_id);
            ps.setInt(++idx, this.users_id);
            ps.execute();
            System.out.println("Solution saved into database");

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                this.id = rs.getInt(1);
            }

        } catch (SQLException e) {
            System.err.println("Can't create solution.");
            e.printStackTrace();
        }
    }
}