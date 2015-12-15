package uk.co.leemorris.starfighter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;
import java.util.Date;

/**
 * Convert inbound date formats into standard format.
 * Supports the following formats:
 *
 * 2015-07-13T05:38:17.33640392Z
 * 2015-07-05T22:16:18+00:00 (ISO-8601)
 * 2015-08-10T16:10:32.987288+09:00
 *
 * @author lmorris
 */
public class MultiformatDateDeserializer extends JsonDeserializer<Date> {

    private DateTimeFormatter millisecondFormat = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
    private DateTimeFormatter millisecondFormatWithTimezone = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZZ");

    @Override
    public Date deserialize(JsonParser jsonParser,
                            DeserializationContext deserializationContext) throws IOException, JsonProcessingException {

        Date returnDate = null;
        String dateText = jsonParser.getText();

        if(dateText.endsWith("Z") && dateText.length() == 30) {
            returnDate = millisecondFormat.parseDateTime(dateText.substring(0, 23)).toDate();
        } else if(dateText.length() == 25) {
            returnDate = millisecondFormatWithTimezone.parseDateTime(dateText).toDate();
        } else if(dateText.length() == 32) {
            String date = dateText.substring(0, 23) + dateText.substring(26, dateText.length());
            returnDate = millisecondFormatWithTimezone.parseDateTime(date).toDate();
        }

        return returnDate;
    }
}
