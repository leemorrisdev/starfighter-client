package uk.co.leemorris.starfighter.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import uk.co.leemorris.starfighter.model.BaseResponse;
import uk.co.leemorris.starfighter.model.Symbol;

import java.util.List;

/**
 * @author lmorris
 */
public class VenueStocks extends BaseResponse {

    private boolean ok;
    private List<Symbol> symbols;

    @JsonCreator
    public VenueStocks(@JsonProperty("ok") boolean ok,
                       @JsonProperty(value="error", required=false) String error,
                       @JsonProperty("symbols") List<Symbol> symbols) {
        super(ok, error);
        this.symbols = symbols;
    }

    public List<Symbol> getSymbols() {
        return symbols;
    }
}
