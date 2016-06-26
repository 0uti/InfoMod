package de.outinetworks.InfoMod.util;

import net.minecraft.util.AxisAlignedBB;

public class Vec6
{
	double[] v = new double[6];
	
	
	public Vec6(double a, double b, double c, double d, double e, double f)
	{
		double[] vt = {a, b, c, d, e, f};
		v = vt;
	}
	
	public Vec6()
	{
		this(0, 0, 0, 0, 0, 0);
	}
	
	public void set(double a, double b, double c, double d, double e, double f){
		double[] vt = {a, b, c, d, e, f};
		v = vt;
	}
	
	public AxisAlignedBB aabb()
	{
		return AxisAlignedBB.fromBounds(v[0], v[1], v[2], v[3], v[4], v[5]);
	}
}
