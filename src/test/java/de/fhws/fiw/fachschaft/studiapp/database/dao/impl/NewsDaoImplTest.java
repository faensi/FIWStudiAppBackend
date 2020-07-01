package de.fhws.fiw.fachschaft.studiapp.database.dao.impl;

import de.fhws.fiw.fachschaft.studiapp.models.News;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class NewsDaoImplTest {

    private NewsDaoImpl newsDao;

    private News news;


    @BeforeEach
    public void setUp() {
        newsDao = new NewsDaoImpl();
        news = News.builder()
                .text("Hello ")
                .title("Welcome")
                .userName("rkrumova97")
				   .time( LocalDateTime.now( ) )
                .image("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")
                .build();

    }

    @Test
    public void testCreate() throws Exception {
        News news1 = newsDao.create(news);
        assertEquals(news, news1);
    }

    @Test
	@Ignore
    public void testRead() throws Exception {
        News read = newsDao.read(1L);
        news.setTime(read.getTime());
        assertEquals(news, read);
    }

    @Test
    public void testReadAll() throws Exception {
        List<News> allNews = newsDao.getAllNews();
        assertFalse(allNews.isEmpty());
    }

    @Test
    public void testDelete() throws Exception {
        int before = newsDao.getAllNews().size();
        newsDao.deleteById(2L);
        int after = newsDao.getAllNews().size();
        assertNotEquals(before, after);
    }
}
