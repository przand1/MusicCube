package musiccube.advancedsearch;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SongParamInterpreterTests {


    @Test
    void emptyParamMapTest() {
        Map<String,String> titles = new HashMap<>();
        AbstractParamInterpreter interpreter = new SongParamInterpreter(titles);

        String query = interpreter.getQuery();

        String excepted = "SELECT s FROM Song s ";
        assertEquals(excepted,query);
    }
    @Test
    void titleOnlyProcessingTest() {
        Map<String,String> titles = new HashMap<>();
        titles.put("title","sun,rain,clouds");
        AbstractParamInterpreter interpreter = new SongParamInterpreter(titles);

        String query = interpreter.getQuery();

        String excepted = "SELECT s FROM Song s WHERE (LOWER(s.songName) IN( LOWER(sun), LOWER(rain), LOWER(clouds) ) ";
        assertEquals(excepted,query);
    }
    @Test
    void notInTitleOnlyProcessingTest() {

        Map<String,String> titles = new HashMap<>();
        titles.put("notitle","sun,rain,clouds");
        AbstractParamInterpreter interpreter = new SongParamInterpreter(titles);

        String query = interpreter.getQuery();

        String excepted = "SELECT s FROM Song s WHERE (LOWER(s.songName) NOT IN( LOWER(sun), LOWER(rain), LOWER(clouds) ) ";
        assertEquals(excepted,query);
    }
    @Test
    void searchByMaxSongLength() {
        Map<String,String> params = new HashMap<>();
        params.put("maxlength","250");
        AbstractParamInterpreter interpreter = new SongParamInterpreter(params);

        String query = interpreter.getQuery();

        String excepted = "SELECT s FROM Song s WHERE (s.songLengthSeconds <= 250 ) ";
        assertEquals(excepted,query);
    }
    @Test
    void searchByMinSongLength() {
        Map<String,String> params = new HashMap<>();
        params.put("minlength","250");
        AbstractParamInterpreter interpreter = new SongParamInterpreter(params);

        String query = interpreter.getQuery();

        String excepted = "SELECT s FROM Song s WHERE (s.songLengthSeconds >= 250 ) ";
        assertEquals(excepted,query);
    }
    @Test
    void searchByBands() {
        Map<String,String> params = new HashMap<>();
        params.put("band","beatles,rolling stones,the who");
        AbstractParamInterpreter interpreter = new SongParamInterpreter(params);

        String query = interpreter.getQuery();

        String excepted = "SELECT s FROM Song s WHERE (LOWER(s.band.bandName) IN( LOWER(beatles), LOWER(rolling stones), LOWER(the who) ) ";;
        assertEquals(excepted,query);
    }
    @Test
    void searchByGenres() {
        Map<String,String> params = new HashMap<>();
        params.put("genre","rock,blues,jazz");
        AbstractParamInterpreter interpreter = new SongParamInterpreter(params);

        String query = interpreter.getQuery();

        String excepted = "SELECT s FROM Song s WHERE (LOWER(s.genre.genreName) IN( LOWER(rock), LOWER(blues), LOWER(jazz) ) ";;
        assertEquals(excepted,query);
    }
    @Test
    void searchByInstruments() {
        Map<String,String> params = new HashMap<>();
        params.put("instrument","trumpet,saxophone");
        AbstractParamInterpreter interpreter = new SongParamInterpreter(params);

        String query = interpreter.getQuery();

        String excepted = "SELECT s FROM Song s WHERE (s.id IN (SELECT si.song.id FROM SongInstrument si WHERE (LOWER(si.instrument.instrumentName) IN( LOWER(trumpet), LOWER(saxophone) ) ) ) ) ";
        assertEquals(excepted,query);

    }
    @Test
    void queryWith4ParametersTest() {
        Map<String,String> params = new HashMap<>();
        params.put("title","sun,rain");
        params.put("notitle","clouds");
        params.put("minlength","100");
        params.put("maxlength","600");
        AbstractParamInterpreter interpreter = new SongParamInterpreter(params);

        String query = interpreter.getQuery();

        String excepted = (new StringBuilder())
                .append("SELECT s FROM Song s WHERE ")
                .append("(LOWER(s.songName) IN( LOWER(sun), LOWER(rain) ) ")
                .append("AND (LOWER(s.songName) NOT IN( LOWER(clouds) ) ")
                .append("AND (s.songLengthSeconds >= 100 ) ")
                .append("AND (s.songLengthSeconds <= 600 ) ")
                .toString();
        assertEquals(excepted,query);
    }
    @Test
    void integrateSearchByInstrumentWithOtherSearches() {
        Map<String,String> params = new HashMap<>();
        params.put("instrument","trumpet");
        params.put("noinstrument","guitar,drums");
        params.put("band","some band");
        params.put("maxlength","555");
        AbstractParamInterpreter interpreter = new SongParamInterpreter(params);
        
        String query = interpreter.getQuery();
        
        String excepted = (new StringBuilder())
                .append("SELECT s FROM Song s WHERE ")
                .append("(s.songLengthSeconds <= 555 ) ")
                .append("AND (LOWER(s.band.bandName) IN( LOWER(some band) ) ")
                .append("AND (s.id IN (SELECT si.song.id FROM SongInstrument si WHERE (LOWER(si.instrument.instrumentName) IN( LOWER(trumpet) ) ) ) ) ")
                .append("AND (s.id NOT IN (SELECT si.song.id FROM SongInstrument si WHERE (LOWER(si.instrument.instrumentName) IN( LOWER(guitar), LOWER(drums) ) ) ) ) ")
                .toString();
        assertEquals(excepted,query);
    }
}