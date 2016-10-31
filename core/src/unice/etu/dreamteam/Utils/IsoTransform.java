package unice.etu.dreamteam.Utils;

import com.badlogic.gdx.math.Matrix4;

/**
 * Created by Guillaume on 31/10/2016.
 */
public class IsoTransform {
    private static Matrix4 isoTransform;
    private static Matrix4 invIsotransform;

    private static void init() {
        if (isoTransform == null) {
            isoTransform = new Matrix4();
            isoTransform.idt();
            isoTransform.scale((float) (Math.sqrt(2.0)), (float) (Math.sqrt(2.0) / 2), 1.0f);
            isoTransform.rotate(0.0f, 0.0f, 1.0f, -45);
            invIsotransform = new Matrix4(isoTransform);
            invIsotransform.inv();
        }
    }

    public static Matrix4 getIsoTransform() {
        if (isoTransform == null)
            init();
        return isoTransform;
    }

    public static Matrix4 getInvIsotransform() {
        if (invIsotransform == null)
            init();
        return invIsotransform;
    }


}
