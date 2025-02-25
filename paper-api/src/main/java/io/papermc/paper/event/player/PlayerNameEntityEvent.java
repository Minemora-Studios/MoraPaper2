package io.papermc.paper.event.player;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * Called when the player is attempting to rename a mob
 */
@NullMarked
public class PlayerNameEntityEvent extends PlayerEvent implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private LivingEntity entity;
    private @Nullable Component name;
    private boolean persistent;

    private boolean cancelled;

    @ApiStatus.Internal
    public PlayerNameEntityEvent(final Player player, final LivingEntity entity, final Component name, final boolean persistent) {
        super(player);
        this.entity = entity;
        this.name = name;
        this.persistent = persistent;
    }

    /**
     * Gets the name to be given to the entity.
     *
     * @return the name
     */
    public @Nullable Component getName() {
        return this.name;
    }

    /**
     * Sets the name to be given to the entity.
     *
     * @param name the name
     */
    public void setName(final @Nullable Component name) {
        this.name = name;
    }

    /**
     * Gets the entity involved in this event.
     *
     * @return the entity
     */
    public LivingEntity getEntity() {
        return this.entity;
    }

    /**
     * Sets the entity involved in this event.
     *
     * @param entity the entity
     */
    public void setEntity(final LivingEntity entity) {
        this.entity = entity;
    }

    /**
     * Gets whether this will set the mob to be persistent.
     *
     * @return persistent
     */
    public boolean isPersistent() {
        return this.persistent;
    }

    /**
     * Sets whether this will set the mob to be persistent.
     *
     * @param persistent persistent
     */
    public void setPersistent(final boolean persistent) {
        this.persistent = persistent;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(final boolean cancel) {
        this.cancelled = cancel;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }
}
