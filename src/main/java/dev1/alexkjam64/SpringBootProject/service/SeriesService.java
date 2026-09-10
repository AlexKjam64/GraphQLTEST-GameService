package dev1.alexkjam64.SpringBootProject.service;

import org.springframework.stereotype.Service;

import dev1.alexkjam64.SpringBootProject.repository.Series.SeriesInfo;
import dev1.alexkjam64.SpringBootProject.repository.Series.SeriesRepository;

@Service
public class SeriesService {
    private final SeriesRepository seriesRepository;

    public SeriesService(SeriesRepository seriesRepository){
        this.seriesRepository = seriesRepository;
    }

    public SeriesInfo retrieve(int id) throws NoDataException{
        checkDataExist(id);

        return seriesRepository.getSeries(id);
    }

    public void create(SeriesInfo request, int id) throws InvalidDataException{
        sanitizeData(request);
        
        seriesRepository.addSeries(request, id);
    }

    public void update(SeriesInfo entity, int id) throws InvalidDataException, NoDataException{
        sanitizeData(entity);

        checkDataExist(id);

        seriesRepository.updateSeries(entity, id);
    }

    public void delete(int id) throws NoDataException{
        checkDataExist(id);

        seriesRepository.deleteSeries(id);
    }

    protected void sanitizeData(SeriesInfo data) throws InvalidDataException{
        // If series information is null or empty... blow up!
        if(data.title() == null || data.title().trim().isEmpty()){
            throw new InvalidDataException("Series title is null or empty!");
        }
        if(data.owner() == null || data.owner().trim().isEmpty()){
            throw new InvalidDataException("Series IP owner is null or empty!");
        }

        // If any of the series info attributes are longer than the db columns... blow up!
        if(data.title().length() > 60){
            throw new InvalidDataException("Series title surpasses 40 characters!");
        }
        if(data.owner().length() > 40){
            throw new InvalidDataException("Series IP owner surpasses 40 character!");
        }

        // If any of the series info include special characters... blow up!
        if(data.title().matches(".*[^a-zA-Z0-9].*")){
            throw new InvalidDataException("Series title includes special characters!");
        }
        if(data.owner().matches(".*[^a-zA-Z0-9].*")){
            throw new InvalidDataException("Series IP owner includes a special character!");
        }
    }

    protected void checkDataExist(int id) throws NoDataException{
        if(seriesRepository.getSeries(id) == null){
            throw new NoDataException("Data does not exist!");
        }
    }
}
