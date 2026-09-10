package dev1.alexkjam64.SpringBootProject.repository.Game;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_EMPTY)
public record GameInfo(int id, String title, String publisher, String developer, int seriesId, boolean deleteIndicator){
    public MapSqlParameterSource mapInfo(int id){
        return new MapSqlParameterSource()
            .addValue("ID", id)
            .addValue("TITLE", title)
            .addValue("PUBLISHER", publisher)
            .addValue("DEVELOPER", developer)
            .addValue("SERIESID", seriesId)
            .addValue("DELETEINDICATOR", deleteIndicator);
    }
}
