import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import pet.*;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        DeletePetByIdTest.class,
        GetPetByIdTest.class,
        GetPetFindByStatusTest.class,
        PostPetByIdTest.class,
        PostPetTest.class,
        PostPetUploadImageByPetId.class,
        PutPetTest.class
})

public class PetStoreTestSuite {
}
