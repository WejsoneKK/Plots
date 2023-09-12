package com.eternalcode.plots.user;

import panda.std.Option;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class UserManager {

    private final Map<UUID, User> usersByUUID = new ConcurrentHashMap<>();
    private final UserRepository userRepository;
    private final UserFactory userFactory;

    public UserManager(UserRepository userRepository, UserFactory userFactory) {
        this.userRepository = userRepository;
        this.userFactory = userFactory;
    }

    public Set<User> getUsers() {
        return new HashSet<>(this.usersByUUID.values());
    }

    public User getOrCreate(UUID uuid, String name) {
        return create(uuid, name).orElseGet(this.usersByUUID.get(uuid));
    }

    public Option<User> create(UUID uuid, String name) {
        if (this.usersByUUID.containsKey(uuid)) {
            return Option.none();
        }

        User user = userFactory.create(uuid, name);
        this.usersByUUID.put(uuid, user);

        this.userRepository.saveUser(user);

        return Option.of(user);
    }

    public void updateUser(User user) {
        this.userRepository.saveUser(user);
    }

    public void updateUsername(User user, String name) {
        user.setName(name);

        this.userRepository.saveUser(user);
    }

    public Collection<User> getUsersByUUID() {
        return Collections.unmodifiableCollection(this.usersByUUID.values());
    }

}
