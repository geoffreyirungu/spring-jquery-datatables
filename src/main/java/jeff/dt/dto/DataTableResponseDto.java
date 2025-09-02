package jeff.dt.dto;

import java.util.List;

import lombok.Data;

@Data
public class DataTableResponseDto<I> {
    private int draw;
    private long recordsTotal;
    private long recordsFiltered;
    private List<I> data;
}
