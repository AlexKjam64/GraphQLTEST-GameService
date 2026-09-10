package dev1.alexkjam64.SpringBootProject.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import dev1.alexkjam64.SpringBootProject.repository.Game.GameInfo;
import dev1.alexkjam64.SpringBootProject.repository.Game.GameRepository;
import dev1.alexkjam64.SpringBootProject.repository.Inventory.InventoryInfo;
import dev1.alexkjam64.SpringBootProject.repository.Inventory.InventoryRepository;

@Service
public class GameService {
    private final GameRepository gameRepository;
    private final InventoryRepository inventoryRepository;

    public GameService(GameRepository gameRepository, InventoryRepository inventoryRepository){
        this.gameRepository = gameRepository;
        this.inventoryRepository = inventoryRepository;
    }

    public Map<Integer, List<GameInfo>> retrieveAllGames4Users(List<Integer> ids) throws NoDataException{
        Map<Integer, List<GameInfo>> allUsersGames = new HashMap<>();
        for(Integer userId : ids){
            System.out.println("userid: " + userId);
            var games = retrieveAllGames(userId);
            if(games!= null && !games.isEmpty()) {
                allUsersGames.put(userId, games);
            }
        }
        System.out.println("allUserGames: " + allUsersGames);
        return allUsersGames;
    }

    public List<GameInfo> retrieveAllGames(int userId) throws NoDataException{
        var inventories = inventoryRepository.getInventory(userId);

        if(inventories.isEmpty()){
            // throw new NoDataException("Nothing in inventory!");
            return null;
        }

        List<Integer> ids = inventories.stream().map(InventoryInfo::gameId).collect(Collectors.toList());
        return gameRepository.getAllGames(ids);
    }

    public GameInfo retrieve(int id) throws NoDataException{
        checkDataExist(id);

        return gameRepository.getGame(id);
    }

    public void create(GameInfo request, int id) throws InvalidDataException{
        sanitizeData(request);

        gameRepository.addGame(request, id);
    }

    public void update(GameInfo entity, int id) throws InvalidDataException, NoDataException{
        sanitizeData(entity);

        checkDataExist(id);

        gameRepository.updateGame(entity, id);
    }

    public void delete(int id) throws NoDataException{
        checkDataExist(id);

        gameRepository.deleteGame(id);
    }

    protected void sanitizeData(GameInfo data) throws InvalidDataException{
        // If game information is null or empty... blow up!
        if(data.title() == null || data.title().trim().isEmpty()){
            throw new InvalidDataException("Game title is null or empty!");
        }
        if(data.publisher() == null || data.publisher().trim().isEmpty()){
            throw new InvalidDataException("Game publisher is null or empty!");
        }
        if(data.developer() == null || data.developer().trim().isEmpty()){
            throw new InvalidDataException("Game developer is null or empty!");
        }

        // If any of the game info attributes are longer than the db columns... blow up!
        if(data.title().length() > 60){
            throw new InvalidDataException("Game title surpasses 60 characters!");
        }
        if(data.publisher().length() > 40){
            throw new InvalidDataException("Game publisher surpasses 40 characters!");
        }
        if(data.developer().length() > 40){
            throw new InvalidDataException("Game developer surpasses 40 character!");
        }

        // If any of the game info include special characters... blow up!
        if(data.title().matches(".*[^a-zA-Z0-9].*")){
            throw new InvalidDataException("Game title includes special characters!");
        }
        if(data.publisher().matches(".*[^a-zA-Z0-9].*")){
            throw new InvalidDataException("Game publisher includes special characters!");
        }
        if(data.developer().matches(".*[^a-zA-Z0-9].*")){
            throw new InvalidDataException("Game developer includes a special character!");
        }
    }

    protected void checkDataExist(int id) throws NoDataException{
        if(gameRepository.getGame(id) == null){
            throw new NoDataException("Data does not exist!");
        }
    }
}
