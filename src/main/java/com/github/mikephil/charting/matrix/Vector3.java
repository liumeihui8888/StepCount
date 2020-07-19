
package com.github.mikephil.charting.matrix;

/**
 * Simple 3D vector class. Handles basic vector math for 3D vectors.
 */
public final class Vector3 {
    public float x;
    public float y;
    public float z;

    public static final com.github.mikephil.charting.matrix.Vector3 ZERO = new com.github.mikephil.charting.matrix.Vector3(0, 0, 0);
    public static final com.github.mikephil.charting.matrix.Vector3 UNIT_X = new com.github.mikephil.charting.matrix.Vector3(1, 0, 0);
    public static final com.github.mikephil.charting.matrix.Vector3 UNIT_Y = new com.github.mikephil.charting.matrix.Vector3(0, 1, 0);
    public static final com.github.mikephil.charting.matrix.Vector3 UNIT_Z = new com.github.mikephil.charting.matrix.Vector3(0, 0, 1);

    public Vector3() {
    }

    public Vector3(float[] array)
    {
        set(array[0], array[1], array[2]);
    }

    public Vector3(float xValue, float yValue, float zValue) {
        set(xValue, yValue, zValue);
    }

    public Vector3(com.github.mikephil.charting.matrix.Vector3 other) {
        set(other);
    }

    public final void add(com.github.mikephil.charting.matrix.Vector3 other) {
        x += other.x;
        y += other.y;
        z += other.z;
    }

    public final void add(float otherX, float otherY, float otherZ) {
        x += otherX;
        y += otherY;
        z += otherZ;
    }

    public final void subtract(com.github.mikephil.charting.matrix.Vector3 other) {
        x -= other.x;
        y -= other.y;
        z -= other.z;
    }

    public final void subtractMultiple(com.github.mikephil.charting.matrix.Vector3 other, float multiplicator)
    {
        x -= other.x * multiplicator;
        y -= other.y * multiplicator;
        z -= other.z * multiplicator;
    }

    public final void multiply(float magnitude) {
        x *= magnitude;
        y *= magnitude;
        z *= magnitude;
    }

    public final void multiply(com.github.mikephil.charting.matrix.Vector3 other) {
        x *= other.x;
        y *= other.y;
        z *= other.z;
    }

    public final void divide(float magnitude) {
        if (magnitude != 0.0f) {
            x /= magnitude;
            y /= magnitude;
            z /= magnitude;
        }
    }

    public final void set(com.github.mikephil.charting.matrix.Vector3 other) {
        x = other.x;
        y = other.y;
        z = other.z;
    }

    public final void set(float xValue, float yValue, float zValue) {
        x = xValue;
        y = yValue;
        z = zValue;
    }

    public final float dot(com.github.mikephil.charting.matrix.Vector3 other) {
        return (x * other.x) + (y * other.y) + (z * other.z);
    }

    public final com.github.mikephil.charting.matrix.Vector3 cross(com.github.mikephil.charting.matrix.Vector3 other) {
        return new com.github.mikephil.charting.matrix.Vector3(y * other.z - z * other.y,
                                                               z * other.x - x * other.z,
                                                               x * other.y - y * other.x);
    }

    public final float length() {
        return (float) Math.sqrt(length2());
    }

    public final float length2() {
        return (x * x) + (y * y) + (z * z);
    }

    public final float distance2(com.github.mikephil.charting.matrix.Vector3 other) {
        float dx = x - other.x;
        float dy = y - other.y;
        float dz = z - other.z;
        return (dx * dx) + (dy * dy) + (dz * dz);
    }

    public final float normalize() {
        final float magnitude = length();

        // TODO: I'm choosing safety over speed here.
        if (magnitude != 0.0f) {
            x /= magnitude;
            y /= magnitude;
            z /= magnitude;
        }

        return magnitude;
    }

    public final void zero() {
        set(0.0f, 0.0f, 0.0f);
    }

    public final boolean pointsInSameDirection(com.github.mikephil.charting.matrix.Vector3 other) {
        return this.dot(other) > 0;
    }

}
