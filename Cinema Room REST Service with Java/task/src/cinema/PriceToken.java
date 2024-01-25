package cinema;

public class PriceToken {
    private final int price;
    private final String token;

    public PriceToken(int price, String token) {
        this.price = price;
        this.token = token;
    }

    public int getPrice() {
        return price;
    }

    public String getToken() {
        return token;
    }
}
