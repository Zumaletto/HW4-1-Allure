package ru.netology.card;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class DataGenerator {

    DataGenerator() {
    }

    private Faker faker = new Faker(new Locale("ru"));

    public String getDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public String getCity() {
        String[] city = {
                "Санкт-Петербург", "Москва", "Смоленск",
                "Екатеринбург", "Челябинск", "Улан-Удэ", "Мурманск",
                "Краснодар", "Калуга", "Калининград"};
        int rnd = new Random().nextInt(city.length);
        return city[rnd];
    }

    public String getName() {

        return faker.name().lastName() + " " + faker.name().firstName();
    }

    public String getPhone() {

        return faker.numerify("+79#########");
    }

}


