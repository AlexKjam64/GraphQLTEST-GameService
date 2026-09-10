package dev1.alexkjam64.SpringBootProject.repository.Series;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class SeriesMapper implements RowMapper<SeriesInfo>{
    
    @Override
    public SeriesInfo mapRow(@SuppressWarnings("null") ResultSet rs, int rowNum) throws SQLException {
        return new SeriesInfo(rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("owner"));
    }
}
