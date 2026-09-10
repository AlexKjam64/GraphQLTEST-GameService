package dev1.alexkjam64.SpringBootProject.repository.Series;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SeriesRepository {
    private final NamedParameterJdbcTemplate template;

    public SeriesRepository(NamedParameterJdbcTemplate template){
        this.template = template;
    }

    private static final String getQuery = """
            SELECT "id", "title", "owner"
            FROM "Nintendo"."Series"
            WHERE "id" = :ID
            """;

    public SeriesInfo getSeries(int id){
        try{
            return template.queryForObject(getQuery, new MapSqlParameterSource("ID", id), new SeriesMapper());
        }catch(EmptyResultDataAccessException e){
            return null;
        }
    }

    private static final String insertQuery = """
            INSERT INTO "Nintendo"."Series" ("id", "title", "owner")
            VALUES (:ID, :TITLE, :OWNER)
            """;
    
    public void addSeries(SeriesInfo newSeries, int id){
        template.update(insertQuery, newSeries.mapInfo(id));
    }

    private static final String updateQuery = """
            UPDATE "Nintendo"."Series"
            SET "title" = :TITLE, "owner" = :OWNER
            WHERE "id" = :ID
            """;
    
    public void updateSeries(SeriesInfo updateSeries, int id){
        template.update(updateQuery, updateSeries.mapInfo(id));
    }

    private static final String deleteQuery = """
            DELETE FROM "Nintendo"."Series"
            WHERE "id" = :ID
            """;
    
    public void deleteSeries(int id){
        template.update(deleteQuery, new MapSqlParameterSource("ID", id));
    }
}
