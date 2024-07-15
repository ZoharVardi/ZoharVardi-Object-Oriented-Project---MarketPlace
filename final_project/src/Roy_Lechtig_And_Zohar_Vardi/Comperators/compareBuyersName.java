package Roy_Lechtig_And_Zohar_Vardi.Comperators;

import Roy_Lechtig_And_Zohar_Vardi.Users.Buyer;

import java.util.Comparator;

public class compareBuyersName implements Comparator<Buyer> {
    @Override
    public int compare(Buyer buyer1, Buyer buyer2) {
        return buyer1.getUserName().compareTo(buyer2.getUserName());
    }
}