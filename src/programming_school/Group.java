package programming_school;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Group {
    private int id;
    private String name;

    public Group() {
    }

    public Group(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Group[] loadAll() {
        try (Connection connection = ConnectionManager.getConnection()) {
            ArrayList<Group> groups = new ArrayList<Group>();
            String sql = "SELECT * FROM user_group";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Group loadedGroup = new Group();
                loadedGroup.id = resultSet.getInt("id");
                loadedGroup.name = resultSet.getString("name");
                groups.add(loadedGroup);
            }
            Group[] gArray = new Group[groups.size()];
            gArray = groups.toArray(gArray);

            return gArray;
        } catch (Exception e) {
            System.err.println("Failed to load all groups.");
            e.printStackTrace();
        }
        return null;
    }

    public Group loadById(int id) {
        try (Connection connection = ConnectionManager.getConnection()) {
            String sql = "SELECT * FROM user_group where id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Group loadedGroup = new Group();
                loadedGroup.id = resultSet.getInt("id");
                loadedGroup.name = resultSet.getString("name");
                return loadedGroup;
            }
            return null;
        } catch (Exception e) {
            System.err.println("Failed to load user group. Please check if result is correct.");
            e.printStackTrace();
        }
        return null;
    }

    public void delete() {
        try (Connection connection = ConnectionManager.getConnection()) {
            if (this.id != 0) {
                String sql = "DELETE FROM user_group WHERE id=?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, this.id);
                preparedStatement.executeUpdate();
                this.id = 0;
            }
        } catch (Exception e) {
            System.err.println("Failed to delete user group.");
            e.printStackTrace();
        }
    }

    @SuppressWarnings("Duplicates")
    public void saveToDb() {
        try (Connection connection = ConnectionManager.getConnection()) {
            String insertGroupQuery =
                    "INSERT INTO user_group (name) VALUES (?)";
            PreparedStatement ps =
                    connection.prepareStatement(insertGroupQuery, PreparedStatement.RETURN_GENERATED_KEYS);
            int idx = 0; // because of pre incrementation
            ps.setString(++idx, this.name);
            ps.execute();
            System.out.println("Group saved into database");

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                this.id = rs.getInt(1);
            }

        } catch (SQLException e) {
            System.err.println("Can't create group.");
            e.printStackTrace();
        }
    }

    @SuppressWarnings("Duplicates")
    public void update() {
        try (Connection connection = ConnectionManager.getConnection()) {
            if (this.id == 0) {
                String insertGroupQuery =
                        "INSERT INTO user_group (name) VALUES (?)";
                PreparedStatement ps =
                        connection.prepareStatement(insertGroupQuery, PreparedStatement.RETURN_GENERATED_KEYS);
                int idx = 0; // because of pre incrementation
                ps.setString(++idx, this.name);
                ps.execute();
                System.out.println("Group saved into database");

                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    this.id = rs.getInt(1);
                }
            } else {
                String sql = "UPDATE user_group SET name=? where id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, this.name);
                preparedStatement.setInt(2, this.id);
                preparedStatement.executeUpdate();
            }
        } catch (Exception e) {
            System.err.println("Failed to update group record.");
            e.printStackTrace();
        }
    }
}