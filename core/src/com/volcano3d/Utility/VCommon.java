package com.volcano3d.Utility;

public class VCommon {
	public static float lerp(float a, float b, float f) 
	{
	    return (a * (1.0f - f)) + (b * f);
	}
}
