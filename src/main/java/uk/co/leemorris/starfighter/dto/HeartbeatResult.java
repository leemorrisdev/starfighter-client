package uk.co.leemorris.starfighter.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import uk.co.leemorris.starfighter.model.BaseResponse;

/**
 * Response from server health check.
 * @author lmorris
 */
public class HeartbeatResult extends BaseResponse {

    @JsonCreator
    public HeartbeatResult(@JsonProperty("ok") boolean ok,
                           @JsonProperty(value="error", required=false) String error) {
        super(ok, error);
    }

}
