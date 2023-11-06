package tdtu.report.Model;

public class CardItem {
    private String title;
    private String subtitle;

    public CardItem(String title, String subtitle) {
        this.title = title;
        this.subtitle = subtitle;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }
}