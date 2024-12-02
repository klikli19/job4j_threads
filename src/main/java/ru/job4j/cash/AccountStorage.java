package ru.job4j.cash;

import net.jcip.annotations.ThreadSafe;
import net.jcip.annotations.GuardedBy;

import java.util.HashMap;
import java.util.Optional;

@ThreadSafe
public class AccountStorage {
    @GuardedBy("this")
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public synchronized boolean add(Account account) {
        return accounts.putIfAbsent(account.id(), account) == null;
    }

    public synchronized boolean update(Account account) {
        return accounts.replace(account.id(), account) != null;
    }

    public synchronized void delete(int id) {
        accounts.remove(id);
    }

    public synchronized Optional<Account> getById(int id) {
        return accounts.get(id) == null ? Optional.empty() : Optional.of(accounts.get(id));
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean result = false;
        Optional<Account> optFrom = getById(fromId);
        Optional<Account> optTo = getById(toId);
        if (fromId != toId && optFrom.isPresent() && optTo.isPresent()) {
            Account from = optFrom.get();
            Account to = optTo.get();
            if (from.amount() >= amount) {
                accounts.put(fromId, new Account(fromId, from.amount() - amount));
                accounts.put(toId, new Account(toId, to.amount() + amount));
                result = true;
            }
        }
        return result;
    }
}
