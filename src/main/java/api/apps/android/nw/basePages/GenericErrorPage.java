package api.apps.android.nw.basePages;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public abstract class GenericErrorPage extends BasePage {

    public static String
        SUBTITLE_TEXT = "¡Uy, algo salió mal!",
        DISCLAIMER_TEXT = "Tuvimos un problema para completar la operación, volvé a intentar más tarde.";

    @AndroidFindBy(id = "emoji")
    protected MobileElement emoji;

    @AndroidFindBy(id = "subtitle")
    protected MobileElement subtitle;

    @AndroidFindBy(id = "disclaimerText")
    protected MobileElement disclaimerText;

    @AndroidFindBy(id = "mainButton")
    protected static MobileElement mainButton;

    public MobileElement getEmoji() {
        return emoji;
    }

    public MobileElement getSubtitle() {
        return subtitle;
    }

    public MobileElement getDisclaimerText() {
        return disclaimerText;
    }

    public static MobileElement getMainButton() {
        return mainButton;
    }

    public GenericErrorPage() {
        super();
    }

}
