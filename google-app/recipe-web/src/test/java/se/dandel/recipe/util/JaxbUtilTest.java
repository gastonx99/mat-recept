package se.dandel.recipe.util;

import static org.junit.Assert.assertEquals;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import se.dandel.recipe.xml.data.Recipes;

public class JaxbUtilTest {

    @Test
    public void unmarshal() throws Exception {
        Recipes recipes = JaxbUtil.fromXml(IOUtils.toString(getClass().getResource("/recipes.xml")), Recipes.class);
        assertEquals("BIFF_STROGANOFF", recipes.getRecipe().iterator().next().getKey());
    }
}
