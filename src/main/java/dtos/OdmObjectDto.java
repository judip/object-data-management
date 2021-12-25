package dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class OdmObjectDto {
    private Long id;
    private Timestamp creationDate;
    private String identifier;
    private String type;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, String> values;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<OdmObjectDto> children;
}
