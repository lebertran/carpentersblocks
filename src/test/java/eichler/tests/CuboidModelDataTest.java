package eichler.tests;

import net.minecraft.util.EnumFacing;
import org.junit.Test;
import tk.eichler.carpentersblocks.model.BaseModelData;
import tk.eichler.carpentersblocks.model.CarpentersBlockModelData;
import tk.eichler.carpentersblocks.model.helper.Polygon;
import tk.eichler.carpentersblocks.model.helper.TransformationHelper;


public class CuboidModelDataTest {
    @Test
    public void testAllPolygonFull() {
        for (final EnumFacing f : EnumFacing.values()) {
            final Polygon polygon = BaseModelData.getFullPolygon(f);
            PolygonTestHelper.testPolygon(polygon);
            PolygonTestHelper.testPolygon(polygon.createWithTransformation(TransformationHelper.ROTATE_SIDE_90));
        }
    }

    @Test
    public void testAllPolygonSlab() {
        for (final EnumFacing f : EnumFacing.values()) {
            final Polygon polygon = CarpentersBlockModelData.getDefaultSlabPolygon(f);
            PolygonTestHelper.testPolygon(polygon);
            PolygonTestHelper.testPolygon(polygon.createWithTransformation(TransformationHelper.ROTATE_SIDE_90));
            PolygonTestHelper.testPolygon(polygon.createWithTransformation(TransformationHelper.ROTATE_SIDE_180));
            PolygonTestHelper.testPolygon(polygon.createWithTransformation(TransformationHelper.ROTATE_SIDE_270));
            PolygonTestHelper.testPolygon(polygon.createWithTransformation(TransformationHelper.TRANSLATE_Y_HALF));
        }
    }
}