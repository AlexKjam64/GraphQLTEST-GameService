package dev1.alexkjam64.SpringBootProject.repository.Inventory;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class InventoryRepository {
    private final NamedParameterJdbcTemplate template;

    public InventoryRepository(NamedParameterJdbcTemplate template){
        this.template = template;
    }

    private static final String getQuery = """
            SELECT * FROM "Nintendo"."Inventory"
            WHERE "userId" = :USERID 
            """;

    public List<InventoryInfo> getInventory(int userId){
        return template.query(getQuery, new MapSqlParameterSource("USERID", userId), new InventoryMapper());
    }

    private static final String getOneQuery = """
            SELECT "gameId"
            FROM "Nintendo"."Inventory"
            WHERE "userId" = :USERID
            """;

    public InventoryInfo getOneInventory(int userId, int gameId){
        try{
            return template.queryForObject(getOneQuery, new MapSqlParameterSource("USERID", userId).addValue("GAMEID", gameId), new InventoryMapper());
        }catch(EmptyResultDataAccessException e){
            return null;
        }
    }

    private static final String insertQuery = """
            INSERT INTO "Nintendo"."Inventory" ("userId", "gameId")
            VALUES (:USERID, :GAMEID)
            """;

    public void addInventory(InventoryInfo newInventory){
        template.update(insertQuery, newInventory.mapInfo());
    }

    private static final String updateQuery = """
            UPDATE "Nintendo"."Inventory"
            SET "userId" = :USERID, "gameId" = :GAMEID
            WHERE "userId" = :USERID AND "gameId" = :GAMEID
            """;

    public void updateInventory(InventoryInfo updateInventory){
        template.update(updateQuery, updateInventory.mapInfo());
    }

    private static final String deleteQuery = """
            DELETE FROM "Nintendo"."Inventory"
            WHERE "userId" = :USERID AND "gameId" = :GAMEID
            """;

    public void deleteInventory(int userId, int gameId){
        template.update(deleteQuery, new MapSqlParameterSource("USERID", userId).addValue("GAMEID", gameId));
    }
}
