package jeff.dt;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import jeff.dt.entity.Item;
import jeff.dt.repository.ItemRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class DtApplication {

	public static void main(String[] args) {
		SpringApplication.run(DtApplication.class, args);
	}

	@Bean
    public CommandLineRunner loadData(ItemRepository itemRepository) {
        return args -> {
            List<Item> items = Arrays.asList(
                new Item("Apple", "A juicy red apple"),
                new Item("Banana", "A ripe yellow banana"),
                new Item("Cherry", "A sweet red cherry"),
                new Item("Date", "A dried date fruit"),
                new Item("Apple1", "A juicy green apple"),
                new Item("Elderberry", "A small dark berry"),
                new Item("Fig", "A soft purple fruit"),
                new Item("Grape", "A bunch of grapes"),
                new Item("Apple2", "A juicy pink apple"),
                new Item("Application", "Not a fruit"),
                new Item("Honeydew", "A sweet green melon"),
                new Item("Kiwi", "A small brown fuzzy fruit"),
                new Item("Lemon", "A tart yellow citrus fruit")
            );

            itemRepository.saveAll(items);
            log.info("items have been added to the database:\n{}",items);
        };
    }

}
