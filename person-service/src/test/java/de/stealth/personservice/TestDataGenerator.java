package de.stealth.personservice;

import de.stealth.personservice.entity.Address;
import de.stealth.personservice.entity.Person;

import java.util.Random;

public class TestDataGenerator {

    public static Person generatePerson() {
        Person person = new Person();
        person.setId(randomInt(4));
        person.setFirstName("Dmitrij");
        person.setLastName("Gusev");
        person.setEmail("a@a.de");
        person.setKeycloak(randomWordTailGenerator("keycloak-", 15));
        person.setAddress(generateAddress());
        return person;
    }

    public static Address generateAddress() {
        Address address = new Address();
        address.setId(randomInt(3));
        address.setStreet(randomWordTailGenerator("Sedulinos"));
        address.setCity(randomWordTailGenerator("Visaginas"));
        address.setZip("" + randomInt(4));
        address.setCountry("Lithuania");
        return address;
    }

    private static String randomWordTailGenerator(String word) {
        return randomWordTailGenerator(word, 3);
    }

    private static String randomWordTailGenerator(String word, int letters) {
        return word + random(letters, false);
    }

    private static int randomInt(int letters) {
        return Integer.parseInt(random(letters, true));
    }

    private static String random(int letters, boolean isNumbers) {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        int[] range = getRange(isNumbers);
        int diff = range[1] - range[0];
        for (int i = 0; i < letters; i++) {
            int x = random.nextInt(diff + 1);
            x += range[0];
            stringBuilder.append(Character.toChars(x)[0]);
        }
        return stringBuilder.toString();
    }

    private static int[] getRange(boolean isNumbers) {
        return isNumbers ?  new int[] {48, 57} : new int[] {65, 90};
    }
}
