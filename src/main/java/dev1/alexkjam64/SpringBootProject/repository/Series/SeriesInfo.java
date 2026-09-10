package dev1.alexkjam64.SpringBootProject.repository.Series;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_EMPTY)
public record SeriesInfo(int id, String title, String owner){
    public MapSqlParameterSource mapInfo(int id){
        return new MapSqlParameterSource()
            .addValue("ID", id)
            .addValue("TITLE", title)
            .addValue("OWNER", owner);
    }
}
