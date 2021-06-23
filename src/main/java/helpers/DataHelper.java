package helpers;

import models.Category;
import models.Pet;
import models.Tag;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class DataHelper {

    public static Pet.Status generateRandomStatus() {
        Pet.Status[] values = Pet.Status.values();
        int randomIndex = new Random().nextInt(values.length);
        return values[randomIndex];
    }

    public static String generateRandomURL() {
        return "http://www.somepagewithimages.org/photo/" + RandomStringUtils.randomAlphanumeric(15) + ".jpg";
    }

    public static Category generateRandomCategory() {
        return new Category(RandomUtils.nextLong(0L, 999999999999999999L), "cat_" + RandomStringUtils.randomAlphanumeric(10));
    }

    public static List<String> generateRandomURLs(int count) {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            result.add(generateRandomURL());
        }
        return result;
    }

    public static List<Tag> generateRandomTags(int count) {
        List<Tag> result = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            result.add(new Tag(RandomUtils.nextLong(0L, 999999999999999999L), "tag_" + RandomStringUtils.randomAlphanumeric(5)));
        }
        return result;
    }

    public static Pet createPetBody(boolean onlyMandatory) {
        Pet body = new Pet()
            .setName("pet_" + RandomStringUtils.randomAlphanumeric(10))
            .setPhotoUrls(DataHelper.generateRandomURLs(2));
        if (!onlyMandatory) {
            body.setId(RandomUtils.nextLong(0L, 999999999999999999L))
                .setCategory(DataHelper.generateRandomCategory())
                .setTags(DataHelper.generateRandomTags(2))
                .setStatus(DataHelper.generateRandomStatus());
        }
        return body;
    }
}
