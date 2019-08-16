package musiccube.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import musiccube.entities.ArtistInBand;

import java.io.IOException;
import java.util.Iterator;

public class ArtistInBandDeserializer extends StdDeserializer<ArtistInBand> {
    public ArtistInBandDeserializer () { this(null); }
    public ArtistInBandDeserializer(Class<?> vc) { super(vc); }

    @Override
    public ArtistInBand deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        ArtistInBand artistInBand = new ArtistInBand();
        ObjectCodec objectCodec = jsonParser.getCodec();
        JsonNode jsonNode = objectCodec.readTree(jsonParser);

        artistInBand.setActive(jsonNode.get("ended").asBoolean());
        if (jsonNode.hasNonNull("begin"))
            artistInBand.setActivityStart(DateParser.parseDate(jsonNode.get("begin").asText()));
        if (jsonNode.hasNonNull("end")) {
            artistInBand.setActivityEnd(DateParser.parseDate(jsonNode.get("end").asText()));
        }
        StringBuilder stringBuilder = new StringBuilder();
        Iterator<JsonNode> jsonNodeIterator = jsonNode.get("attributes").elements();
        while (jsonNodeIterator.hasNext()) {
            String role = jsonNodeIterator.next().asText();
            if ( !role.equals("original") ) {
                stringBuilder.append(role);
                stringBuilder.append(",");
            }
        }
        artistInBand.setRoles(stringBuilder.toString());

        return artistInBand;
    }
}