package Roy_Lechtig_And_Zohar_Vardi.Comperators;
import Roy_Lechtig_And_Zohar_Vardi.Users.Seller;

import java.util.Comparator;

 public class compareSellersByNumberOfProducts implements Comparator<Seller> {
    @Override
    public int compare(Seller seller1, Seller seller2) {
        return seller2.getProductLogsSize() - seller1.getProductLogsSize();
    }
}
