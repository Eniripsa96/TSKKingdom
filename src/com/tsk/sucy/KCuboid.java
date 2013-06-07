package com.tsk.sucy;

import org.bukkit.Location;

/**
 * A cuboid definition for regions
 */
public class KCuboid {

    Location corner;
    int width;
    int depth;

    /**
     * Constructor from a center point and dimensions
     *
     * @param center center point
     * @param width  x-coordinate size
     * @param depth  z-coordinate size
     */
    public KCuboid(Location center, int width, int depth) {
        center.setX(center.getBlockX() - width / 2);
        center.setZ(center.getBlockZ() - depth / 2);
        this.corner = center;
        this.width = width;
        this.depth = depth;
    }

    /**
     * Constructor from two points
     *
     * @param loc1 first point
     * @param loc2 second point
     */
    public KCuboid(Location loc1, Location loc2) {
        corner = new Location(loc1.getWorld(),
                Math.min(loc1.getBlockX(), loc2.getBlockX()),
                0, Math.min(loc1.getBlockZ(), loc2.getBlockZ()));
        width = Math.abs(loc1.getBlockX() - loc2.getBlockX());
        depth = Math.abs(loc1.getBlockZ() - loc2.getBlockZ());
    }

    /**
     * Constructor from toString data
     *
     * @param world
     * @param x
     * @param z
     * @param width
     * @param depth
     */
    public KCuboid(String world, String x, String z, String width, String depth) {
        corner = new Location(TSKKingdom.instance.getServer().getWorld(world), Integer.parseInt(x), 0, Integer.parseInt(z));
        this.width = Integer.parseInt(width);
        this.depth = Integer.parseInt(depth);
    }

    /**
     * @return x-coordinate size of the cuboid
     */
    public int width() {
        return width;
    }

    /**
     * @return z-coordinate size of the cuboid
     */
    public int depth() {
        return depth;
    }

    /**
     * @return area of the cuboid
     */
    public int area() {
        return width * depth;
    }

    /**
     * Checks if the given point is contained in the cuboid
     *
     * @param loc point to check
     * @return    true if contained, false otherwise
     */
    public boolean contains(Location loc) {
        return loc.getX() >= corner.getX() && loc.getX() <= corner.getX() + width
                && loc.getZ() >= corner.getZ() && loc.getZ() <= corner.getZ() + depth;
    }

    /**
     * Checks if the given cuboid is entirely inside of this one
     *
     * @param cuboid cuboid to check
     * @return       true if entirely inside, false otherwise
     */
    public boolean contains(KCuboid cuboid) {
        return cuboid.corner.getX() >= corner.getX() && cuboid.corner.getX() + cuboid.width <= corner.getX() + width
                && cuboid.corner.getZ() >= corner.getZ() && cuboid.corner.getZ() + cuboid.depth <= corner.getZ() + depth;
    }

    /**
     * Checks if the cuboid overlaps at all with this one
     *
     * @param cuboid cuboid to check
     * @return       true if overlapping at all, false otherwise
     */
    public boolean overlaps(KCuboid cuboid) {
        if (cuboid.corner.getX() > corner.getX() + width) return false;
        if (cuboid.corner.getZ() > corner.getZ() + depth) return false;
        if (cuboid.corner.getX() + cuboid.width < corner.getX()) return false;
        if (cuboid.corner.getZ() + cuboid.depth < corner.getZ()) return false;
        return true;
    }

    /**
     * @return cuboid data as a string
     */
    @Override
    public String toString() {
        return corner.getWorld().getName() + "," + corner.getBlockX() + "," + corner.getBlockZ() + "," + width + "," + depth;
    }
}
