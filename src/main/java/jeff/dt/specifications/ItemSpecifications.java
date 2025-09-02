package jeff.dt.specifications;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jeff.dt.dto.DataTableRequestDto;
import jeff.dt.entity.Item;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemSpecifications {
    public static Specification<Item> filterBySearch(String globalSearchValue, List<DataTableRequestDto.Column> columns) {
        return (Root<Item> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            
            // Apply global search if present
            if (globalSearchValue != null && !globalSearchValue.isEmpty()) {
                Predicate globalPredicate = criteriaBuilder.or(
                    criteriaBuilder.like(root.get("name"), "%" + globalSearchValue + "%"),
                    criteriaBuilder.like(root.get("description"), "%" + globalSearchValue + "%")
                );
                predicates.add(globalPredicate);
            }

            // Apply column-specific searches
            for (DataTableRequestDto.Column column : columns) {
                if (column.isSearchable() && column.getSearch() != null && column.getSearch().getValue() != null && !column.getSearch().getValue().isEmpty()) {
                    String columnSearchValue = column.getSearch().getValue();
                    Predicate columnPredicate = criteriaBuilder.like(root.get(column.getData()), "%" + columnSearchValue + "%");
                    predicates.add(columnPredicate);
                }
            }

            // Combine all predicates
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<Item> sortBy(DataTableRequestDto dataTableRequestDto) {
        return (Root<Item> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            List<DataTableRequestDto.Order> orders = dataTableRequestDto.getOrder();
            List<DataTableRequestDto.Column> columns = dataTableRequestDto.getColumns();
            List<Order> jpaOrders = new ArrayList<>();
            
            for (DataTableRequestDto.Order order : orders) {
                String sortColumn = columns.get(order.getColumn()).getData();
                String sortDirection = order.getDir();
                Order jpaOrder = "asc".equalsIgnoreCase(sortDirection) ?
                    criteriaBuilder.asc(root.get(sortColumn)) :
                    criteriaBuilder.desc(root.get(sortColumn));
                jpaOrders.add(jpaOrder);
            }
            
            query.orderBy(jpaOrders);
            return query.getRestriction();
        };
    }
}
