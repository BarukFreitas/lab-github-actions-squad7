package com.vemser.rest.data.factory;

import com.vemser.rest.utils.Manipulation;
import net.datafaker.Faker;
import org.apache.commons.lang3.StringUtils;
import java.util.Locale;
import java.util.Properties;
import java.util.Random;

public class BaseDataFactory {

    static Faker faker = new Faker(new Locale("pt-BR"));
    static Random geradorBoolean = new Random();

    public static final Properties prop = Manipulation.getProp();

    public static String vazio = StringUtils.EMPTY;

    public static String email = faker.internet().emailAddress();

    public  static int numeroVazio = 0;

    public  static int numeroInvalido = faker.number().numberBetween(-100, 0);

}
