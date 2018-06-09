package net.pixelatedd3v.bossmessenger.protocol.impl.actionbarapi;

import org.bukkit.event.*;
import org.bukkit.entity.*;

public class ActionBarMessageEvent extends Event
{
    private static final HandlerList handlers;
    private final Player player;
    private String message;
    private boolean cancelled;
    
    public ActionBarMessageEvent(final Player player, final String message) {
        this.cancelled = false;
        this.player = player;
        this.message = message;
    }
    
    public HandlerList getHandlers() {
        return ActionBarMessageEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return ActionBarMessageEvent.handlers;
    }
    
    public Player getPlayer() {
        return this.player;
    }
    
    public String getMessage() {
        return this.message;
    }
    
    public void setMessage(final String message) {
        this.message = message;
    }
    
    public boolean isCancelled() {
        return this.cancelled;
    }
    
    public void setCancelled(final boolean cancelled) {
        this.cancelled = cancelled;
    }
    
    static {
        handlers = new HandlerList();
    }
}
