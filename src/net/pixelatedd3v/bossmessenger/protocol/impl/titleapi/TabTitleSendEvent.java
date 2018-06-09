package net.pixelatedd3v.bossmessenger.protocol.impl.titleapi;

import org.bukkit.event.*;
import org.bukkit.entity.*;

public class TabTitleSendEvent extends Event
{
    private static final HandlerList handlers;
    private final Player player;
    private String header;
    private String footer;
    private boolean cancelled;
    
    public TabTitleSendEvent(final Player player, final String header, final String footer) {
        this.cancelled = false;
        this.player = player;
        this.header = header;
        this.footer = footer;
    }
    
    public HandlerList getHandlers() {
        return TabTitleSendEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return TabTitleSendEvent.handlers;
    }
    
    public Player getPlayer() {
        return this.player;
    }
    
    public String getHeader() {
        return this.header;
    }
    
    public void setHeader(final String header) {
        this.header = header;
    }
    
    public String getFooter() {
        return this.footer;
    }
    
    public void setFooter(final String footer) {
        this.footer = footer;
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
