package de.fhws.fiw.fachschaft.studiapp.database.dao;

import de.fhws.fiw.fachschaft.studiapp.models.News;
import de.fhws.fiw.sutton.database.results.NoContentResult;

import java.util.List;

public interface NewsDao {

    public News create(News news) throws Exception;

    public News read(Long id) throws Exception;

    public List<News> getAllNews() throws Exception;

    public void deleteById(Long id) throws Exception;

    public void delete(News news) throws Exception;

    void update(News news) throws Exception;
}
