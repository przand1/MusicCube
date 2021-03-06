package musiccube.services.instrument;

import musiccube.entities.Instrument;

import java.util.Optional;

public interface InstrumentService {

    Optional<Instrument> getById(int id);
    Iterable<Instrument> getAll();
    Iterable<Instrument> getAllPaging(Integer pageNr, Integer perPage);
    Instrument save(Instrument instrument);
    void delete(int id);

    Iterable<Instrument> getByInstrumentName(String instrumentName);
    Iterable<Instrument> getByInstrumentType(String instrumentTypeName);


    boolean existsByInstrumentName(String instrumentName);
    Instrument getOneByName(String name);

}
