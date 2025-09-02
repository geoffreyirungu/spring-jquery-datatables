package jeff.dt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import jeff.dt.entity.Item;

public interface ItemRepository extends JpaRepository<Item,Long>, JpaSpecificationExecutor<Item> {}
