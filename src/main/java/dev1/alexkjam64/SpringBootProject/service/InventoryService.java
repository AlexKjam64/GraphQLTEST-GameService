package dev1.alexkjam64.SpringBootProject.service;

import org.springframework.stereotype.Service;

import dev1.alexkjam64.SpringBootProject.repository.Inventory.InventoryInfo;
import dev1.alexkjam64.SpringBootProject.repository.Inventory.InventoryRepository;

@Service
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository){
        this.inventoryRepository = inventoryRepository;
    }

    public InventoryInfo retrieve(int userId, int gameId) throws NoDataException{
        checkDataExist(userId, gameId);

        return inventoryRepository.getOneInventory(userId, gameId);
    }

    public void create(InventoryInfo request) throws InvalidDataException{
        inventoryRepository.addInventory(request);
    }

    public void update(InventoryInfo entity, int userId, int gameId) throws InvalidDataException, NoDataException{
        checkDataExist(userId, gameId);

        inventoryRepository.updateInventory(entity);
    }

    public void delete(int userId, int gameId) throws NoDataException{
        checkDataExist(userId, gameId);

        inventoryRepository.deleteInventory(userId, gameId);
    }

    protected void checkDataExist(int userId, int gameId) throws NoDataException{
        if(inventoryRepository.getOneInventory(userId, gameId) == null){
            throw new NoDataException("Data does not exist!");
        }
    }
}
