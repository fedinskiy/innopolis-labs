import org.junit.jupiter.api.Test;
import ru.innopolis.fdudinskiy.helpers.TestBase;
import ru.innopolis.fdudinskiy.uniqcheck.MultiThreadApplication;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.innopolis.fdudinskiy.helpers.ResourceHelper.FileType.VALID;
import static ru.innopolis.fdudinskiy.helpers.ResourceHelper.FileType.VALID2;
import static ru.innopolis.fdudinskiy.helpers.ResourceHelper.ResourceType.FILE;

/**
 * Created by fedinskiy on 13.02.17.
 */
class MultiThreadApplicationTest extends TestBase {
	
	@Test
	void fullRun() {
		final String[] resources = new String[2];
		resources[0] = resources().getResource(FILE, VALID);
		resources[1] = resources().getResource(FILE, VALID2);
		assertTrue(MultiThreadApplication.main(resources));
	}
}