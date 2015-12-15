package uk.co.leemorris.starfighter.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import uk.co.leemorris.starfighter.model.BaseResponse;

/**
 * @author lmorris
 */
public class VenueResult extends BaseResponse {

    private boolean ok;
    private String venue;

    @JsonCreator
    public VenueResult(@JsonProperty("ok") boolean ok,
                       @JsonProperty(value="error", required=false) String error,
                       @JsonProperty("venue") String venue) {
        super(ok, error);
        this.venue = venue;
    }

    public String getVenue() {
        return venue;
    }
}
