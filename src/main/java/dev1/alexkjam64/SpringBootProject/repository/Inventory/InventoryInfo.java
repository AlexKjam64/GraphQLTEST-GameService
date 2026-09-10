package dev1.alexkjam64.SpringBootProject.repository.Inventory;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_EMPTY)
public record InventoryInfo(int userId, int gameId){
    public MapSqlParameterSource mapInfo(){
        return new MapSqlParameterSource()
            .addValue("USERID", userId)
            .addValue("GAMEID", gameId);
    }
}
