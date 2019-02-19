package programming_school;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Active Record
 */
public class User {
    private int id;
    private String username;
    private String password;
    private String email;

    public User() {
    }

    public User(int id, String username, String password, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    //////////////////////
    // Queries
    //////////////////////

    @SuppressWarnings("Duplicates")
    public User[] loadAll() {
        try (Connection connection = ConnectionManager.getConnection()) {
            ArrayList<User> users = new ArrayList<User>();
            String sql = "SELECT * FROM users";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User loadedUser = new User();
                loadedUser.id = resultSet.getInt("id");
                loadedUser.username = resultSet.getString("username");
                loadedUser.password = resultSet.getString("password");
                loadedUser.email = resultSet.getString("email");
                users.add(loadedUser);
            }
            User[] uArray = new User[users.size()];
            uArray = users.toArray(uArray);
        } catch (Exception e) {
            System.err.println("Failed to load all users. Please check if result set is correct.");
            e.printStackTrace();
        }

        return null;
    }

    @SuppressWarnings("Duplicates")
    public User loadById(int id) {
        try (Connection connection = ConnectionManager.getConnection()) {
            String sql = "SELECT * FROM users where id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                User loadedUser = new User();
                loadedUser.id = resultSet.getInt("id");
                loadedUser.username = resultSet.getString("username");
                loadedUser.password = resultSet.getString("password");
                loadedUser.email = resultSet.getString("email");
                return loadedUser;
            }
            return null;
        } catch (Exception e) {
            System.err.println("Failed to load user. Please check if result set is correct.");
            e.printStackTrace();
        }
        return null;
    }

    //////////////////////
    // Data manipulation
    //////////////////////
    @SuppressWarnings("Duplicates")
    public void saveToDb() {
        try (Connection connection = ConnectionManager.getConnection()) {
            String insertUserQuery =
                    "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";
            PreparedStatement ps =
                    connection.prepareStatement(insertUserQuery, PreparedStatement.RETURN_GENERATED_KEYS);
            int idx = 0; // because of pre incrementation
            ps.setString(++idx, this.username);
            ps.setString(++idx, this.email);
            ps.setString(++idx, this.password);
            ps.execute();
            System.out.println("User saved into database");

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                this.id = rs.getInt(1);
            }

        } catch (SQLException e) {
            System.err.println("Can't create user!");
            e.printStackTrace();
        }
    }

    @SuppressWarnings("Duplicates")
    public void update() {
        try (Connection connection = ConnectionManager.getConnection()) {
            if (this.id == 0) {
                String insertUserQuery =
                        "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";
                PreparedStatement ps =
                        connection.prepareStatement(insertUserQuery, PreparedStatement.RETURN_GENERATED_KEYS);
                int idx = 0; // because of pre incrementation
                ps.setString(++idx, this.username);
                ps.setString(++idx, this.email);
                ps.setString(++idx, this.password);
                ps.execute();
                System.out.println("User saved into database");

                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    this.id = rs.getInt(1);
                }
            } else {
                String sql = "UPDATE users SET username=?, email=?, password=? where id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, this.username);
                preparedStatement.setString(2, this.email);
                preparedStatement.setString(3, this.password);
                preparedStatement.setInt(4, this.id);
                preparedStatement.executeUpdate();
            }
        } catch (Exception e) {
            System.err.println("Failed to update user record.");
            e.printStackTrace();
        }
    }

    public void delete() {
        try (Connection connection = ConnectionManager.getConnection()) {
            if (this.id != 0) {
                String sql = "DELETE FROM users WHERE id=?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, this.id);
                preparedStatement.executeUpdate();
                this.id = 0;
            }
        } catch (Exception e) {
            System.err.println("Failed to delete user record.");
            e.printStackTrace();
        }
    }

    public void deleteAll() {
        // TODO implement deleting all Users from DB - set id to 0 for each object when invoked
        try (Connection connection = ConnectionManager.getConnection()) {
            String sql = "DELETE FROM users";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.err.println("Failed to delete all users.");
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}