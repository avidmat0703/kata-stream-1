package org.iesvdm.kata1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import java.util.*;
import java.util.stream.Collectors;
import static java.lang.Math.abs;
import static java.util.stream.Collectors.*;

public class Exercise4Test extends PetDomainForKata {
    @Test
    @Tag("KATA")
    public void getAgeStatisticsOfPets() {
        // Assertions.fail("Refactor to stream. Don't forget to comment this out or delete it when you are done.");

        //TODO
        // Replace by stream of petAges
        var petAges = this.people.stream()
                .map(Person::getPets)
                .flatMap(Collection::stream)
                .map(Pet::getAge)
                .toList();

        var uniqueAges = Set.copyOf(petAges);

        var expectedSet = Set.of(1, 2, 3, 4);
        Assertions.assertEquals(expectedSet, uniqueAges);

        //TODO
        // Replace by stream
        // IntSummaryStatistics is a class in JDK 8 use it over petAges
        var stats = petAges.stream().collect(summarizingInt(Integer::intValue));
        //TODO
        // Replace 0 by stream over petAges
        Assertions.assertEquals(stats.getMin(), petAges.stream().reduce(Integer::min).orElse(0));
        Assertions.assertEquals(stats.getMax(), petAges.stream().reduce(Integer::max).orElse(0));
        Assertions.assertEquals(stats.getSum(), petAges.stream().mapToInt(Integer::intValue).sum());
        Assertions.assertEquals(stats.getAverage(), petAges.stream().collect(averagingInt(Integer::intValue)), 1);
        Assertions.assertEquals(stats.getCount(), petAges.stream().mapToInt(Integer::intValue).count());

        // What is Delta in Assertequals Java?
        //delta - the maximum delta between expected and actual for which both numbers are still considered equal.

        //TODO
        // Replace by correct stream
        // All age > 0
        Assertions.assertTrue(petAges.stream().allMatch(i -> i > 0));
        //TODO
        // No one ages == 0
        Assertions.assertFalse(petAges.stream().anyMatch(i -> i == 0));
        //TODO
        // No one age < 0
        Assertions.assertTrue(petAges.stream().noneMatch(i -> i < 0));
    }

    @Test
    @Tag("KATA")
    @DisplayName("bobSmithsPetNamesAsString - 🐱 🐶")
    public void bobSmithsPetNamesAsString() {
        // Assertions.fail("Refactor to stream. Don't forget to comment this out or delete it when you are done.");

        //TODO
        // find Bob Smith
        Person person = this.people.stream()
                .filter(person1 -> person1.getFullName().equals("Bob Smith"))
                .findAny()
                .orElse(new Person("una", "persona"));

        //TODO
        // get Bob Smith's pets' names
        String names = person.getPets().stream()
                .map(Pet::getName)
                .collect(Collectors.joining(" & "));

        Assertions.assertEquals("Dolly & Spot", names);
    }

    @Test
    @Tag("KATA")
    public void immutablePetCountsByEmoji() {
        // Assertions.fail("Refactor to stream. Don't forget to comment this out or delete it when you are done.");

        //TODO
        // Unmodificable map of counts
        Map<String, Long> countsByEmoji = this.people.stream()
                .flatMap(person -> person.getPets().stream())
                .collect(groupingBy(pet -> pet.getType().toString(), counting()));

        Assertions.assertEquals(
                Map.of("🐱", 2L, "🐶", 2L, "🐹", 2L, "🐍", 1L, "🐢", 1L, "🐦", 1L),
                countsByEmoji
        );
    }

    /**
     * The purpose of this test is to determine the top 3 pet types.
     */
    @Test
    @Tag("KATA")
    @DisplayName("topThreePets - 🐱 🐶 🐹")
    public void topThreePets() {
        // Assertions.fail("Refactor to stream. Don't forget to comment this out or delete it when you are done.");

        //TODO
        // Obtain three top pets
        var favorites = this.people.stream()
                .flatMap(person -> person.getPets().stream())
                .collect(groupingBy(Pet::getType, counting()))
                .entrySet().stream()
                .sorted((o1, o2) -> (int) (o2.getValue() - o1.getValue()))
                .limit(3)
                .toList();
        System.out.println(favorites);

        Assertions.assertEquals(3, favorites.size());

        Assertions.assertTrue(favorites.contains(new AbstractMap.SimpleEntry<>(PetType.CAT, Long.valueOf(2))));
        Assertions.assertTrue(favorites.contains(new AbstractMap.SimpleEntry<>(PetType.DOG, Long.valueOf(2))));
        Assertions.assertTrue(favorites.contains(new AbstractMap.SimpleEntry<>(PetType.HAMSTER, Long.valueOf(2))));

    }

    @Test
    @Tag("KATA")
    public void getMedianOfPetAges() {
        //   Assertions.fail("Refactor to stream. Don't forget to comment this out or delete it when you are done.");

        //TODO
        // Obtain pet ages
        var petAges = this.people.stream()
                .map(Person::getPets)
                .flatMap(Collection::stream)
                .map(Pet::getAge)
                .toList();

        //TODO
        // sort pet ages
        var sortedPetAges = petAges.stream().sorted((o1, o2) -> (int) o1 - o2).toList();

        System.out.println(sortedPetAges);
        double median;
        if (0 == sortedPetAges.size() % 2) {
            //TODO
            //
            // The median of a list of even numbers is the average of the two middle items
            median = sortedPetAges.stream().skip(3L).limit(2).collect(averagingDouble(Integer::doubleValue));
        } else {
            // The median of a list of odd numbers is the middle item
            median = sortedPetAges.get(abs(sortedPetAges.size() / 2)).doubleValue();
        }

        Assertions.assertEquals(2.0, median, 0.0);
    }
}