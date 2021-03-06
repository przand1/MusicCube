package musiccube.services.song;

import musiccube.advancedsearchv2.SongParamInterpreter;
import musiccube.entities.Album;
import musiccube.entities.Band;
import musiccube.entities.Song;
import musiccube.repositories.SongRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class SongServiceImpl implements SongService {

    @Autowired
    private SongRepository songRepository;
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Override
    public Optional<Song> getById(int id) {
        return songRepository.findById(id);
    }

    @Override
    public Iterable<Song> getAll() {
        return songRepository.findAll();
    }

    @Override
    public Iterable<Song> getAllPaging(Integer pageNr, Integer perPage) {
        return songRepository.findAll(new PageRequest(pageNr,perPage));
    }
    @Override
    public Song save(Song song) {
        return songRepository.save(song);
    }
    @Override
    public void delete(int id) {
        songRepository.deleteById(id);
    }

    @Override
    public Iterable<Song> getBySongName(String songName) {
        return songRepository.findBySongName(songName);
    }

    @Override
    public Iterable<Song> getByGenreName(String genreName) {
        return songRepository.findByGenreName(genreName);
    }

    @Override
    public Iterable<Song> getByAlbumName(String albumName) {
        return songRepository.findByAlbumName(albumName);
    }

    @Override
    public Iterable<Song> getByBandId(int id) {
        return songRepository.findByBandId(id);
    }

    @Override
    public boolean existsBySongName(String songName){
        return songRepository.existsBySongName(songName);
    }
    @Override
    public boolean existsByAlbum(Album album){
        return songRepository.existsByAlbum(album);
    }
    @Override
    public boolean existsByBand(Band band){
        return songRepository.existsByBand(band);
    }

    @Override
    public List<Song> advanced(Map<String, String> params) {
        SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
        SongParamInterpreter interpreter;
        Query<Song> query;
        try (Session session = sessionFactory.openSession()) {

            interpreter = new SongParamInterpreter(params);
            query = session.createQuery(interpreter.getQuery().toString());
            HashMap<String,Object> queryParams = interpreter.getQueryParams();

            for (Map.Entry<String,Object> entry : queryParams.entrySet()) {
                query.setParameter(entry.getKey(),entry.getValue());
            }

            return query.list();
        }
    }
}
