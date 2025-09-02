package jeff.dt.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import jeff.dt.dto.DataTableRequestDto;
import jeff.dt.entity.Item;
import jeff.dt.repository.ItemRepository;
import jeff.dt.specifications.ItemSpecifications;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ItemService {
    private final ItemRepository itemRepository;

    public Page<Item> getItems(DataTableRequestDto request) {
        int page = request.getStart() / request.getLength();
        //List<DataTableRequestDto.Order> orders = request.getOrder();
        List<DataTableRequestDto.Column> columns = request.getColumns();
        
        Specification<Item> spec = Specification.where(ItemSpecifications.filterBySearch(request.getSearch().getValue(), columns))
                                               .and(ItemSpecifications.sortBy(request));

        //Page<Item> pageResult = itemRepository.findAll(spec, PageRequest.of(page, request.getLength()));
        return itemRepository.findAll(spec, PageRequest.of(page, request.getLength()));
    }

    public long getTotalRecords() {
        return itemRepository.count();
    }
}
