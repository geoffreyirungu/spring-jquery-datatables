package jeff.dt.controller;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jeff.dt.dto.DataTableRequestDto;
import jeff.dt.dto.DataTableResponseDto;
import jeff.dt.entity.Item;
import jeff.dt.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping("/items")
    public DataTableResponseDto<Item> getItems(DataTableRequestDto request) {

        log.info("Request is:\n{}",request);
        Page<Item> page = itemService.getItems(request);

        DataTableResponseDto<Item> response = new DataTableResponseDto<>();
        response.setDraw(request.getDraw());
        response.setRecordsTotal(itemService.getTotalRecords());
        response.setRecordsFiltered(page.getTotalElements());
        response.setData(page.getContent());

        return response;
    }
}
