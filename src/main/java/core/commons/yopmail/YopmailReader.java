package core.commons.yopmail;

import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Map;

public class YopmailReader {

    private final String YOPMAIL_BASE_URL = "http://www.yopmail.com/es/";

    private final String MOBILE_USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:75.0) Gecko/20100101 Firefox/75.0";

    private static YopmailReader instance;

    private YopmailReader() {
    }

    public static YopmailReader getInstance() {
        if (instance == null) {
            instance = new YopmailReader();
        }
        return instance;
    }

    public String getEmailsContent(final String yopmailUserName, final int page) throws IOException {
        String urlDefinitions = "&d=&ctrl=&scrl=&yf=005&yp=XZwp5ZmNjBQL5BGL3ZGV3ZQN&yj=GZwZ2ZwZmZQplAwDkAGN5BQp&v=3.1&r_c=&id=";

        Response res = Jsoup.connect(YOPMAIL_BASE_URL + "inbox.php?login=" + yopmailUserName.toLowerCase().replaceAll("@yopmail.com", "") + "&p=" + (page <= 0 ? 1 : page) + urlDefinitions)
                .userAgent(MOBILE_USER_AGENT)
                .method(Method.GET)
                .execute();

        Map<String, String> cookies = res.cookies();
        cookies.put("compte", yopmailUserName.toLowerCase().replaceAll("@yopmail.com", ""));
        Map<String, String> headers = res.headers();

        Document doc = Jsoup.parse(res.body());
        Elements mails = doc.getElementsByClass("lm");

        if (mails.size() > 0) {
            Response resMail = Jsoup.connect(YOPMAIL_BASE_URL + mails.get(0).attr("href"))
                    .headers(headers)
                    .userAgent(MOBILE_USER_AGENT)
                    .cookies(cookies)
                    .method(Method.GET)
                    .execute();

            return Jsoup.parse(resMail.body()).getElementById("mailmillieu").text();
        }

        return null;
    }
}
