package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

public class UserUtil {

    public static final List<User> USER_LIST = Arrays.asList(
            new User(null, "Samen",  "samen@gmail.com", "123", Role.USER),
            new User(null, "admin", "admin@gmail.com", "admin", Role.USER, Role.ADMIN),
            new User(null, "Bamen2", "samen2@gmail.com", "123", Role.USER),
            new User(null, "Yamen3", "samen3@gmail.com", "123", Role.USER),
            new User(null, "Tamen4", "samen4@gmail.com", "123", Role.USER),
            new User(null, "Wamen5", "samen5@gmail.com", "123", Role.USER),
            new User(null, "Aamen6", "samen6@gmail.com", "123", Role.USER),
            new User(null, "aamen7", "samen7@gmail.com", "123", Role.USER)
            );
    public static List<User> filterByName(Collection<User> users){
        return users.stream().sorted().collect(Collectors.toList());
    }
}
