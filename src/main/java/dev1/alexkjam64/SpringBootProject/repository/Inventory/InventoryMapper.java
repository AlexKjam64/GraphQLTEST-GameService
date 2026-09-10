package dev1.alexkjam64.SpringBootProject.repository.Inventory;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class InventoryMapper implements RowMapper<InventoryInfo>{

    @Override
    public InventoryInfo mapRow(@SuppressWarnings("null") ResultSet rs, int rowNum) throws SQLException {
        return new InventoryInfo(rs.getInt("userId"),
                            rs.getInt("gameId"));
    }
    
}
