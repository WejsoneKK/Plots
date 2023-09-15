package com.eternalcode.plots.notgood.command.oldcode.contextual;

import com.eternalcode.plots.good.user.User;
import com.eternalcode.plots.good.user.UserManager;
import dev.rollczi.litecommands.command.Invocation;
import dev.rollczi.litecommands.contextual.Contextual;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import panda.std.Result;

public class UserContextual implements Contextual<CommandSender, User> {

    private final UserManager userManager;

    public UserContextual(UserManager userManager) {
        this.userManager = userManager;
    }

    @Override
    public Result<User, Object> extract(CommandSender commandSender, Invocation<CommandSender> invocation) {
        if (!(invocation.sender().getHandle() instanceof Player player)) {
            return Result.error("&cCommand sender must be a user!");
        }

        return Result.ok(this.userManager.findOrCreate(player.getUniqueId(), player.getName()));
    }
}
