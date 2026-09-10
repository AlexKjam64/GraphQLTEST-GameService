package dev1.alexkjam64.SpringBootProject.repository.Game;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class GameMapper implements RowMapper<GameInfo>{

    @Override
    public GameInfo mapRow(@SuppressWarnings("null") ResultSet rs, int rowNum) throws SQLException {
        return new GameInfo(rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("publisher"),
                        rs.getString("developer"),
                        rs.getInt("seriesId"),
                        rs.getBoolean("deleteIndicator"));
    }
}
