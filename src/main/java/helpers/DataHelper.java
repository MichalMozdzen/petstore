package helpers;

import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.MultiPartSpecification;
import models.Category;
import models.Pet;
import models.Tag;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.tuple.Pair;

import javax.activation.MimeType;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class DataHelper {

    public static Pet.Status generateRandomStatus() {
        Pet.Status[] values = Pet.Status.values();
        int randomIndex = new Random().nextInt(values.length);
        return values[randomIndex];
    }

    public static Category generateRandomCategory() {
        return new Category(RandomUtils.nextLong(0L, 999999999999999999L), "cat_" + RandomStringUtils.randomAlphanumeric(10));
    }

    public static String generateRandomURL() {
        return "http://www.somepagewithimages.org/photo/" + RandomStringUtils.randomAlphanumeric(15) + ".jpg";
    }

    public static List<String> generateRandomURLs(int count) {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            result.add(generateRandomURL());
        }
        return result;
    }

    public static Tag generateRandomTag() {
        return new Tag(RandomUtils.nextLong(0L, 999999999999999999L), "tag_" + RandomStringUtils.randomAlphanumeric(5));
    }

    public static List<Tag> generateRandomTags(int count) {
        List<Tag> result = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            result.add(generateRandomTag());
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
                    .setStatus(DataHelper.generateRandomStatus().label);
        }
        return body;
    }

    public static String[] getTwoRandomStatuses() {
        List<Pet.Status> list = Stream.of(Pet.Status.values())
                .filter(s -> s != generateRandomStatus())
                .collect(Collectors.toList());

        return list.stream().map(e -> e.label).toArray(String[]::new);
    }

    public static String prepareStatusWithCommas(String... statuses) {
        StringBuilder result = new StringBuilder();
        Arrays.stream(statuses).forEach(s -> result.append(s).append(","));
        return result.deleteCharAt(result.length() - 1).toString();
    }

    public static Pair<String, String> createFormParams() {
        return Pair.of("pet_" + RandomStringUtils.randomAlphanumeric(10),
                generateRandomStatus().label);
    }

    public static MultiPartSpecification createMultipartData() throws URISyntaxException {
        File file;
        URL resource = DataHelper.class.getClassLoader().getResource("samplePet.png");
        if (resource == null) {
            throw new IllegalArgumentException("File not found.");
        } else {
            file = new File(resource.toURI());
        }

        return new MultiPartSpecBuilder(file)
                .fileName("samplePet.png")
                .controlName("file")
                .mimeType("multipart/form-data")
                .build();
    }
}
