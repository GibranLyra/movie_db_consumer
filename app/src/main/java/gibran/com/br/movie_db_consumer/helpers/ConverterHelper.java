package gibran.com.br.movie_db_consumer.helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import timber.log.Timber;

/**
 * Created by gibranlyra on 05/02/18 for movie_db_consumer.
 */

public class ConverterHelper {

    public static String formatDate(String dateInString) {
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-mm-dd");
        SimpleDateFormat formatter = new SimpleDateFormat("dd/mm/yyyy");
        try {
            Date date = parser.parse(dateInString);
            return formatter.format(date);
        } catch (ParseException e) {
            Timber.e(e, "formatDate: %s", e.getMessage());
        }
        return null;
    }
}
