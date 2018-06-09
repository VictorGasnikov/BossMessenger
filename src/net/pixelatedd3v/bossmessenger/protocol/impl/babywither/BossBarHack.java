package net.pixelatedd3v.bossmessenger.protocol.impl.babywither;

import net.pixelatedd3v.bossmessenger.config.Settings;
import net.pixelatedd3v.bossmessenger.protocol.impl.babywither.reflection.ClassBuilder;
import net.pixelatedd3v.bossmessenger.protocol.impl.babywither.reflection.NMSClass;
import net.pixelatedd3v.bossmessenger.utils.RandomUtils;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class BossBarHack {
	protected final int ID;
	protected final Player receiver;
	protected String message;
	protected float health;
	protected Location location;
	protected World world;
	protected boolean visible;
	protected Object dataWatcher;

	protected BossBarHack(final Player player, final String message, final float health) {
		this.visible = false;
		this.ID = RandomUtils.RANDOM.nextInt();
		this.receiver = player;
		this.message = message;
		this.health = health > 0 ? health : 1;
		this.world = player.getWorld();
		this.location = this.makeLocation(player.getLocation(), health > 150);
	}

	protected Location makeLocation(final Location base, boolean healthy) {
		return base.getDirection().multiply(healthy ? Settings.PROTOCOL_HEALTHY_WITHER_RANGE : Settings.PROTOCOL_DAMAGED_WITHER_RANGE).add(base.toVector()).toLocation(this.world);
	}

	public void setHealth(final float percentage) {
		this.health = percentage * 3;
		this.sendMetadata();
	}

	public void setMessage(final String message) {
		this.message = message;
		if (this.isVisible()) {
			this.sendMetadata();
		}
	}

	public Location getLocation() {
		return this.location;
	}

	public void setVisible(final boolean flag) {
		if (flag == this.visible) {
			return;
		}
		if (flag) {
			this.spawn();
		} else {
			this.destroy();
		}
	}

	public boolean isVisible() {
		return this.visible;
	}

	protected void updateMovement() {
		if (!this.visible) {
			return;
		}
		this.location = this.makeLocation(this.receiver.getLocation(), health > 150);
		try {
			final Object packet = ClassBuilder.buildTeleportPacket(this.ID, this.getLocation(), false, false);
			OldBossBarAPI.sendPacket(this.receiver, packet);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void updateDataWatcher() {
		if (this.dataWatcher == null) {
			try {
				ClassBuilder.setDataWatcherValue(this.dataWatcher = ClassBuilder.buildDataWatcher(null), 17, new Integer(0));
				ClassBuilder.setDataWatcherValue(this.dataWatcher, 18, new Integer(0));
				ClassBuilder.setDataWatcherValue(this.dataWatcher, 19, new Integer(0));
				ClassBuilder.setDataWatcherValue(this.dataWatcher, 20, new Integer(1000));
				ClassBuilder.setDataWatcherValue(this.dataWatcher, 0, (byte) 32);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			ClassBuilder.setDataWatcherValue(this.dataWatcher, 6, this.health);
			ClassBuilder.setDataWatcherValue(this.dataWatcher, 11, (byte) 1);
			ClassBuilder.setDataWatcherValue(this.dataWatcher, 3, (byte) 1);
			ClassBuilder.setDataWatcherValue(this.dataWatcher, 10, this.message);
			ClassBuilder.setDataWatcherValue(this.dataWatcher, 2, this.message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void sendMetadata() {
		this.updateDataWatcher();
		try {
			final Object metaPacket = ClassBuilder.buildNameMetadataPacket(this.ID, this.dataWatcher, 2, 3, this.message);
			OldBossBarAPI.sendPacket(this.receiver, metaPacket);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void spawn() {
		try {
			this.updateMovement();
			this.updateDataWatcher();
			final Object packet = ClassBuilder.buildWitherSpawnPacket(this.ID, this.getLocation(), this.dataWatcher);
			OldBossBarAPI.sendPacket(this.receiver, packet);
			this.visible = true;
			this.sendMetadata();
			this.updateMovement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void destroy() {
		try {
			final Object packet = NMSClass.PacketPlayOutEntityDestroy.getConstructor(int[].class).newInstance(new int[]{this.ID});
			OldBossBarAPI.sendPacket(this.receiver, packet);
			this.visible = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
