package dev1.alexkjam64.SpringBootProject.repository.Game;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class GameRepository {
    private final NamedParameterJdbcTemplate template;

    public GameRepository(NamedParameterJdbcTemplate template){
        this.template = template;
    }

    private static final String getQuery = """
            SELECT "id", "title", "publisher", "developer", "seriesId", "deleteIndicator"
            FROM "Nintendo"."Game"
            WHERE "id" IN (:ID)
            """;

    public List<GameInfo> getAllGames(List<Integer> ids){
        return template.query(getQuery, new MapSqlParameterSource("ID", ids), new GameMapper());
    }

    public GameInfo getGame(int id){
        try{
            return template.queryForObject(getQuery, new MapSqlParameterSource("ID", id), new GameMapper());
        }catch(EmptyResultDataAccessException e){
            return null;
        }
    }

    private static final String insertQuery = """
            INSERT INTO "Nintendo"."Game" ("id", "title", "publisher", "developer", "seriesId")
            VALUES (:ID, :TITLE, :PUBLISHER, :DEVELOPER, :SERIESID)
            """;

    public void addGame(GameInfo newGame, int id){
        template.update(insertQuery, newGame.mapInfo(id));
    }

    private static final String updateQuery = """
            UPDATE "Nintendo"."Game"
            SET "title" = :TITLE, "publisher" = :PUBLISHER, "developer" = :DEVELOPER, "seriesId" = :seriesId
            WHERE "id" = :ID
            """;

    public void updateGame(GameInfo updateGame, int id){
        template.update(updateQuery, updateGame.mapInfo(id));
    }

    private static final String deleteQuery = """
            DELETE FROM "Nintendo"."Game"
            WHERE "id" = :ID
            """;

    public void deleteGame(int id){
        template.update(deleteQuery, new MapSqlParameterSource("ID", id));
    }
}
