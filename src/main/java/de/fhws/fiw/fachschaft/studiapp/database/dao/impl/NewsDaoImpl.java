package de.fhws.fiw.fachschaft.studiapp.database.dao.impl;

import de.fhws.fiw.fachschaft.studiapp.database.config.ConnectionManager;
import de.fhws.fiw.fachschaft.studiapp.database.config.ObjectMapper;
import de.fhws.fiw.fachschaft.studiapp.database.dao.NewsDao;
import de.fhws.fiw.fachschaft.studiapp.models.News;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

import static de.fhws.fiw.fachschaft.studiapp.database.config.ConnectionManager.getConnection;

public class NewsDaoImpl implements NewsDao {
    private Connection con;
    private PreparedStatement ps;

    @Override
    public News create(News news) throws Exception {
        try {
            con = getConnection();
            ps = Objects.requireNonNull(con)
                    .prepareStatement("INSERT INTO news (title, text, user_name, time, image) VALUES(?, ?, ?, ?, ?)");

            ps.setString(1, news.getTitle());
            ps.setString(2, news.getText());
            ps.setString(3, news.getUserName());
            ps.setTimestamp(4, new Timestamp(((news.getTime()).atZone( ZoneId.systemDefault()).toInstant().toEpochMilli())));
            ps.setBytes(5, Base64.getDecoder().decode(news.getImage()));

            ps.executeUpdate();

            news.setId(ObjectMapper.getGeneratedId(con));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.closeConnection(con);
        }
        return news;
    }

    @Override
    public News read(Long id) throws Exception {
        con = getConnection();
        News news = new News();

        try {
            System.out.println("Creating statement...");

            ps = con
                    .prepareStatement("SELECT * FROM news WHERE id = ? AND is_deleted = false");
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            //STEP 5: Extract data from result set
            while (rs.next()) {
                String title = rs.getString("title");
                String text = rs.getString("text");
                String userName = rs.getString("user_name");
                LocalDateTime date = rs.getTimestamp("time").toLocalDateTime();
                String image = Base64.getEncoder().encodeToString(rs.getBytes("image"));

                news = News.builder()
                        .time(date)
                        .title(title)
                        .userName(userName)
                        .image(image)
                        .id(id)
                        .text(text).build();
            }
            rs.close();
        } catch (Exception se) {
            se.printStackTrace();
        }//Handle errors for Class.forName
        finally {
            //finally block used to close resources
            try {
                if (ps != null)
                    con.close();
            } catch (SQLException ignored) {
            }// do nothing
            try {
                if (con != null)
                    con.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
        return news;
    }

    @Override
    public List<News> getAllNews() throws Exception {
        Statement stmt = null;
        con = getConnection();
        List<News> news = new ArrayList<>();

        try {
            System.out.println("Creating statement...");
            stmt = con.createStatement();

            String sql = "SELECT * FROM news where is_deleted = false";
            ResultSet rs = stmt.executeQuery(sql);
            //STEP 5: Extract data from result set
            while (rs.next()) {
                Long id = rs.getLong("id");
                String title = rs.getString("title");
                String text = rs.getString("text");
                String userName = rs.getString("user_name");
                LocalDateTime date = rs.getTimestamp("time").toLocalDateTime();
                String image = Base64.getEncoder().encodeToString(rs.getBytes("image"));

                news.add(News.builder()
                        .time(date)
                        .title(title)
                        .userName(userName)
                        .image(image)
                        .id(id)
                        .text(text).build());
            }
            rs.close();
        } catch (Exception se) {
            se.printStackTrace();
        }//Handle errors for Class.forName
        finally {
            //finally block used to close resources
            try {
                if (stmt != null)
                    con.close();
            } catch (SQLException se) {
            }// do nothing
            try {
                if (con != null)
                    con.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
        return news;
    }

    @Override
    public void deleteById(Long id) throws Exception {
        try {
            con = getConnection();

            //STEP 4: Execute a query
            System.out.println("Creating statement...");

            ps = con.prepareStatement("UPDATE  news SET is_deleted = true " +
                    "WHERE id = ?");
            ps.setLong(1, id);
            ps.executeUpdate();
            ps.close();

        } catch (Exception se) {
            //Handle errors for JDBC
            se.printStackTrace();
        }//Handle errors for Class.forName
        finally {
            //finally block used to close resources
            try {
                if (ps != null)
                    Objects.requireNonNull(con).close();
            } catch (SQLException ignored) {
            }// do nothing
            try {
                if (con != null)
                    con.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
    }

    @Override
    public void delete(News news) throws Exception {
        deleteById(news.getId());
    }

    @Override
    public void update(News news) throws Exception {
        try {

            con = getConnection();

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            ps = con.prepareStatement("UPDATE news " +
                    "SET title = ?, text = ?, user_name = ?, time = ?, image = ?  WHERE id = ?");
            ps.setString(1, news.getTitle());
            ps.setString(2, news.getText());
            ps.setString(3, news.getUserName());
            ps.setObject(4, news.getTime());
            ps.setBytes(5, Base64.getDecoder().decode(news.getImage()));
            ps.setLong(6, news.getId());
            ps.executeUpdate();
            ps.close();
        } catch (Exception se) {
            //Handle errors for JDBC
            se.printStackTrace();
        }//Handle errors for Class.forName
        finally {
            //finally block used to close resources
            try {
                if (ps != null)
                    Objects.requireNonNull(con).close();
            } catch (SQLException ignored) {
            }// do nothing
            try {
                if (con != null)
                    con.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
    }

}
